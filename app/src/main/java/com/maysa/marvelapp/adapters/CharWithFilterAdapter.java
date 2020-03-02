package com.maysa.marvelapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.maysa.marvelapp.R;
import com.maysa.marvelapp.datamodels.Result;
import com.maysa.marvelapp.listeners.AdapterListener;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

public class CharWithFilterAdapter extends RecyclerView.Adapter<CharWithFilterAdapter.MyViewHolder>
        implements Filterable {

    private List<Result> datumList, datumListFiltered ;
    private final Context context;
    private AdapterListener adapterListener ;



    public CharWithFilterAdapter(List<Result> datumList, Context context,AdapterListener adapterListener) {
        this.datumList = datumList;
        this.datumListFiltered = datumList;
        this.context = context;
        this.adapterListener = adapterListener ;

    }

    @NonNull
    @Override
    public CharWithFilterAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.char_item_horizontal, parent, false);
        return new CharWithFilterAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final CharWithFilterAdapter.MyViewHolder holder, final int position) {
        final Result character = datumList.get(position);
        holder.char_name.setText(character.getName());
        String pic = character.getThumbnail().getPath() + "." +
                character.getThumbnail().getExtension();
        Glide.with(context)
                .load(pic)
                .into(holder.char_pic);

    }

    @Override
    public int getItemCount() {
        return datumList.size();
    }

    @Override
    public Filter getFilter() {

        Filter filter = new Filter() {
            @SuppressWarnings("unchecked")
            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {

                datumList = (List<Result>) results.values; // has the filtered values
                notifyDataSetChanged();  // notifies the data with new filtered values
            }

            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults results = new FilterResults();        // Holds the results of a filtering operation in values
                List<Result> FilteredArrList = new ArrayList<>() ;
                if (datumListFiltered == null) {
                    datumListFiltered = datumList; // saves the original data in mOriginalValues
                }

                /********
                 *
                 *  If constraint(CharSequence that is received) is null returns the mOriginalValues(Original) values
                 *  else does the Filtering and returns FilteredArrList(Filtered)
                 *
                 ********/
                if (constraint == null || constraint.length() == 0) {

                    // set the Original result to return
                    results.count = datumListFiltered.size();
                    results.values = datumListFiltered;
                }
                else {
                    constraint = constraint.toString().toLowerCase();
                    for (int i = 0; i < datumListFiltered.size(); i++) {
                        String data = datumListFiltered.get(i).getName();
                        if (data.toLowerCase().contains(constraint.toString())) {
                            FilteredArrList.add((datumListFiltered.get(i)));
                        }
                    }
                    // set the Filtered result to return
                    results.count = FilteredArrList.size();
                    results.values = FilteredArrList;
                }
                return results;
            }
        };
        return filter;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.char_name)
        TextView char_name;

        @BindView(R.id.char_pic)
        ImageView char_pic;

        MyViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, itemView);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    adapterListener.onCharacterSelected(datumList.get(getAdapterPosition()));
                }
            });
        }

    }

}