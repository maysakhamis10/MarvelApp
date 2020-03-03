package com.maysa.marvelapp.views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.kaopiz.kprogresshud.KProgressHUD;
import com.maysa.marvelapp.R;
import com.maysa.marvelapp.adapters.ComicsAdapter;
import com.maysa.marvelapp.adapters.EventsAdapter;
import com.maysa.marvelapp.adapters.SeriesAdapter;
import com.maysa.marvelapp.adapters.StoriesAdapter;
import com.maysa.marvelapp.datamodels.Item;
import com.maysa.marvelapp.datamodels.Result;
import com.maysa.marvelapp.datamodels.Url;
import com.maysa.marvelapp.viewmodels.DetailViewModel;
import java.util.ArrayList;
import java.util.List;

public class DetailsActivity extends AppCompatActivity implements View.OnClickListener {


    @BindView(R.id.listComics)
    RecyclerView listComics;
    @BindView(R.id.listSeries)
    RecyclerView listSeries;
    @BindView(R.id.listStories)
    RecyclerView listStories;
    @BindView(R.id.listEvents)
    RecyclerView listEvents;
    @BindView(R.id.characterName)
    TextView characterName;
    @BindView(R.id.characterDescription)
    TextView characterDescription;
    @BindView(R.id.wiki)
    TextView wiki;
    @BindView(R.id.ComicLink)
    TextView ComicLink;
    @BindView(R.id.characterDetail)
    TextView characterDetail;
    @BindView(R.id.header)
    ImageView header;
    private KProgressHUD loading ;
    DetailViewModel detailViewModel;
    private String wikiUrl;
    private String comicLinkUrl;
    private String detailUrl;
    Result selectedChar;
    private List<Item> stories, events,series,comics ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        ButterKnife.bind(this);
        initLoading();
        init();
        populateComicsList();
        populateEventsList();
        populateSeriesList();
        populateStoriesList();

    }

    private void init() {
        detailViewModel = ViewModelProviders.of
                (this).get(DetailViewModel.class);
        selectedChar = getIntent().getParcelableExtra("SelectedChar");
        stories = getIntent().getParcelableArrayListExtra("STORIES");
        events = getIntent().getParcelableArrayListExtra("EVENTS");
        series = getIntent().getParcelableArrayListExtra("SERIES");
        comics = getIntent().getParcelableArrayListExtra("COMICS");
        String descStr = getIntent().getStringExtra("DES");
        List<Url> urlList = getIntent().getParcelableArrayListExtra("URLS");
        characterName.setText(selectedChar.getName());
        characterDescription.setText(descStr);
        for (Url url : urlList) {
            if (url.getType().equals("detail")) {
                detailUrl = url.getUrl();
            } else if (url.getType().equals("wiki")) {
                wikiUrl = url.getUrl();
            } else {
                comicLinkUrl = url.getUrl();
            }
            Glide.with(DetailsActivity.this)
                    .load(selectedChar.getThumbnail().getPath() + "." +
                            selectedChar.getThumbnail().getExtension())
                    .into(header);
            characterDetail.setOnClickListener(this);
            wiki.setOnClickListener(this);
            ComicLink.setOnClickListener(this);
        }
    }

    private void populateComicsList() {
        loading.show();
        List<Item> newComics= new ArrayList<>();
        if (comics!=null) {
            if (comics.size() != 0) {
                    detailViewModel.getPhotosOfEachComic(comics)
                            .observe(this, dataModel -> {
                                loading.dismiss();
                                newComics.add(dataModel);
                                ComicsAdapter comicsAdapter = new ComicsAdapter(newComics,
                                        DetailsActivity.this);
                                listComics.setLayoutManager(new LinearLayoutManager(getApplicationContext(),
                                        LinearLayoutManager.HORIZONTAL, false));
                                listComics.setAdapter(comicsAdapter);
                            });


            }
            else {
                loading.dismiss();
            }
        }
        else {
            loading.dismiss();
        }

    }

    private void populateStoriesList() {
        loading.show();
        List<Item> newStories= new ArrayList<>();
        if (stories!=null) {
            if (stories.size() != 0) {
                    detailViewModel.getPhotosOfEachStory(stories)
                            .observe(this, dataModel -> {
                                loading.dismiss();
                                newStories.add(dataModel);
                                StoriesAdapter storiesAdapter = new StoriesAdapter(newStories,
                                        DetailsActivity.this);
                                listStories.setLayoutManager(new LinearLayoutManager(getApplicationContext(),
                                        LinearLayoutManager.HORIZONTAL, false));
                                listStories.setAdapter(storiesAdapter);
                            });

                }
            else {
                loading.dismiss();
            }
            }
        else {
            loading.dismiss();
        }
        }


    private void populateSeriesList() {
        loading.show();
        List<Item> newSeries = new ArrayList<>();
        if (series!=null){
        if (series.size() != 0) {
                detailViewModel.getPhotosOfEachSeries(series)
                        .observe(this, dataModel -> {
                            loading.dismiss();
                            newSeries.add(dataModel);
                            SeriesAdapter seriesAdapter = new SeriesAdapter(newSeries,
                                    DetailsActivity.this);
                            listSeries.setLayoutManager(new LinearLayoutManager(getApplicationContext(),
                                    LinearLayoutManager.HORIZONTAL, false));
                            listSeries.setAdapter(seriesAdapter);
                        });
        }
        else {
            loading.dismiss();
        }
        }
        else {
            loading.dismiss();
        }
    }

    private void populateEventsList() {

        loading.show();
        List<Item> newEvents = new ArrayList<>();
        if (events!=null) {
            if (events.size() != 0) {
                    detailViewModel.getPhotosOfEachEvent(events)
                            .observe(this, dataModel -> {
                                loading.dismiss();
                                newEvents.add(dataModel);
                                EventsAdapter eventsAdapter = new EventsAdapter(newEvents,
                                        DetailsActivity.this);
                                listEvents.setLayoutManager(new LinearLayoutManager(getApplicationContext(),
                                        LinearLayoutManager.HORIZONTAL, false));
                                listEvents.setAdapter(eventsAdapter);
                            });

            }
            else {
                loading.dismiss();
            }
        }
        else {
            loading.dismiss();
        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.wiki:
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(wikiUrl));
                startActivity(browserIntent);
                break;
            case R.id.characterDetail:
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(detailUrl));
                startActivity(intent);
                break;

            case R.id.ComicLink:
                Intent intent1 = new Intent(Intent.ACTION_VIEW, Uri.parse(comicLinkUrl));
                startActivity(intent1);
                break;
        }


    }

    private void initLoading( ){
        loading = KProgressHUD.create(DetailsActivity.this)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setAnimationSpeed(2)
                .setBackgroundColor(Color.TRANSPARENT)
                .setDimAmount(0.8f);
    }
}
