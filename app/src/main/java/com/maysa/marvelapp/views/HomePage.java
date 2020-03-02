package com.maysa.marvelapp.views;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.os.Handler;
import android.os.Parcelable;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.github.ybq.android.spinkit.SpinKitView;
import com.google.gson.Gson;
import com.maysa.marvelapp.R;
import com.maysa.marvelapp.adapters.CharWithFilterAdapter;
import com.maysa.marvelapp.adapters.CharactersAdapter;
import com.maysa.marvelapp.datamodels.CharactersDataModel;
import com.maysa.marvelapp.datamodels.Result;
import com.maysa.marvelapp.listeners.AdapterListener;
import com.maysa.marvelapp.utilis.InfiniteScrollListener;
import com.maysa.marvelapp.viewmodels.MainPageViewModel;
import com.miguelcatalan.materialsearchview.MaterialSearchView;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class HomePage extends AppCompatActivity implements InfiniteScrollListener.OnLoadMoreListener
        , AdapterListener {


    @BindView(R.id.list_of_characters)
    RecyclerView list_of_chars;
    @BindView(R.id.list_of_characters_filter)
    RecyclerView list_of_characters_filter;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.search_view)
    MaterialSearchView searchView;

    @BindView(R.id.progressBar)
    ProgressBar progressBar;

    CharWithFilterAdapter charWithFilterAdapter;
    CharactersAdapter charactersAdapter;
    private int pageNum = 0 ;
    private int total_count  = 0 ;
    InfiniteScrollListener infiniteScrollListener ;
    private List<Result> allCharList ;
    MainPageViewModel mainPageViewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        ButterKnife.bind(this);
        initViews();
        getAllData();
    }

    private void initViews(){
        //init some objects
        allCharList = new ArrayList<>();
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }
        LinearLayoutManager layoutManager = new LinearLayoutManager(HomePage.this);
        list_of_chars.setLayoutManager(layoutManager);
        LinearLayoutManager layoutManager2 = new LinearLayoutManager(HomePage.this);
        list_of_characters_filter.addItemDecoration(new DividerItemDecoration(HomePage.this, LinearLayout.VERTICAL));
        list_of_characters_filter.setLayoutManager(layoutManager2);


        infiniteScrollListener = new InfiniteScrollListener(layoutManager, this);
        infiniteScrollListener.setLoaded();
        list_of_chars.addOnScrollListener(infiniteScrollListener);


        searchView.setOnClickListener(v -> {
            list_of_chars.setVisibility(View.GONE);
            //init parent list
            list_of_characters_filter.setVisibility(View.VISIBLE);
        });
        searchView.setOnQueryTextListener(new MaterialSearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                if (query.equals("")) {
                    list_of_chars.setVisibility(View.VISIBLE);
                    list_of_characters_filter.setVisibility(View.GONE);
                } else {

                    list_of_chars.setVisibility(View.GONE);
                    list_of_characters_filter.setVisibility(View.VISIBLE);
                    // filter recycler view when query submitted
                    charWithFilterAdapter.getFilter().filter(query);
                    charWithFilterAdapter.notifyDataSetChanged();
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String query) {
                if (query.equals("")) {
                    list_of_chars.setVisibility(View.VISIBLE);
                    list_of_characters_filter.setVisibility(View.GONE);
                } else {
                    list_of_chars.setVisibility(View.GONE);
                    list_of_characters_filter.setVisibility(View.VISIBLE);
                    charWithFilterAdapter.getFilter().filter(query);
                    charWithFilterAdapter.notifyDataSetChanged();
                }
                return false;
            }
        });
        searchView.setOnSearchViewListener(new MaterialSearchView.SearchViewListener() {
            @Override
            public void onSearchViewShown() {
                //Do some magic
            }

            @Override
            public void onSearchViewClosed() {
                list_of_chars.setVisibility(View.VISIBLE);
                list_of_characters_filter.setVisibility(View.GONE);
            }
        });

    }

    private void getAllData() {

        pageNum = 0 ;
        allCharList.clear();
        mainPageViewModel = ViewModelProviders.of
                (this).get(MainPageViewModel.class);
        mainPageViewModel.getAllCharacters(pageNum).observe(this,
                jsonObject -> {

                    if (jsonObject != null) {
                        pageNum = jsonObject.getData().getOffset();
                        total_count = jsonObject.getData().getTotal();
                        allCharList = jsonObject.getData().getResults();
                        Log.d("AAA", new Gson().toJson(jsonObject.getData().getResults()));
                        charactersAdapter =
                                new CharactersAdapter(allCharList, HomePage.this,this);
                        charWithFilterAdapter = new CharWithFilterAdapter(allCharList,
                                HomePage.this,this);
                        list_of_chars.setAdapter(charactersAdapter);
                        list_of_characters_filter.setAdapter(charWithFilterAdapter);
                    }
                });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);

        MenuItem item = menu.findItem(R.id.action_search);
        searchView.setMenuItem(item);

        return true;
    }



    @Override
    public void onLoadMore() {
        if (allCharList.size() != total_count) {
            showProgressView();
        }
        new Handler().postDelayed(() -> {
            if (allCharList.size() != total_count) {
                pageNum = pageNum +1 ;
                loadMore(pageNum);
                infiniteScrollListener.setLoaded();
            }
        }, 1000);
    }

    void showProgressView() {
        progressBar.setVisibility(View.VISIBLE);
    }

    void hideProgressView() {
        progressBar.setVisibility(View.INVISIBLE);
    }

    private void loadMore(int offset){
        Log.d("MORE", String.valueOf(offset));
        mainPageViewModel.getAllCharacters(offset ).observe(this, charactersDataModel -> {
            hideProgressView();
            pageNum = charactersDataModel.getData().getOffset();
            allCharList.addAll(charactersDataModel.getData().getResults());
            charactersAdapter.notifyDataSetChanged();
            Log.d("SIZE_OF_LIST", String.valueOf(allCharList.size()));
        });

    }


    @Override
    public void onBackPressed() {
        if (searchView.isSearchOpen()) {
            searchView.closeSearch();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public void onCharacterSelected(Result result) {
        Log.d("SELECTED_ONE", result.getName());
        Intent  intent = new Intent(HomePage.this,DetailsActivity.class);
        intent.putExtra("SelectedChar", result);
        intent.putExtra("OFFSET", String.valueOf(pageNum));
        intent.putParcelableArrayListExtra("STORIES",(ArrayList<? extends Parcelable>)result.getStories().getItems());
        intent.putParcelableArrayListExtra("SERIES",(ArrayList<? extends Parcelable>)result.getSeries().getItems());
        intent.putParcelableArrayListExtra("EVENTS",(ArrayList<? extends Parcelable>)result.getEvents().getItems());
        intent.putParcelableArrayListExtra("URLS",(ArrayList<? extends Parcelable>)result.getUrls());
        intent.putParcelableArrayListExtra("COMICS",(ArrayList<? extends Parcelable>)result.getComics().getItems());
        startActivity(intent);
    }
}
