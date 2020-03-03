package com.maysa.marvelapp.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.maysa.marvelapp.R;
import com.maysa.marvelapp.datamodels.Item;
import com.maysa.marvelapp.utilis.GlideApp;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

public class SeriesAdapter extends RecyclerView.Adapter<SeriesAdapter.MyViewHolder> {

    private List<Item> datumList ;
    private final Context context;

    public SeriesAdapter(List<Item> datumList,
                         Context context  )
    {
        this.datumList = datumList;
        this.context = context;
        /// this.itemListener = itemListener ;
    }

    @NonNull
    @Override
    public SeriesAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recylec_item, parent, false);
        return new SeriesAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final SeriesAdapter.MyViewHolder holder, final int position) {
        final Item character = datumList.get(position);
        holder.char_name.setText(character.getName());
        if (character.getPhotoUrl()!=null) {
            if (!character.getPhotoUrl().equals("no_photo")) {
                String photoStr = character.getPhotoUrl();
                GlideApp.with(context)
                        .load(photoStr)
                        .into(holder.char_pic);
            }
        }else {
            holder.char_pic.setImageResource(R.drawable.download);
        }

    }

    @Override
    public int getItemCount() {
        return datumList.size();
    }


    class MyViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.name)
        TextView char_name;

        @BindView(R.id.icon)
        ImageView char_pic;

        MyViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, itemView);
        }

    }

}


