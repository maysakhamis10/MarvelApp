package com.maysa.marvelapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

public class CharactersAdapter extends RecyclerView.Adapter<CharactersAdapter.MyViewHolder> {

    private List<Result> datumList ;
    private final Context context;
    private AdapterListener adapterListener ;

    public CharactersAdapter(List<Result> datumList, Context context  ,AdapterListener adapterListener) {
        this.datumList = datumList;
        this.context = context;
       this.adapterListener = adapterListener ;
    }

    @NonNull
    @Override
    public CharactersAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.charcater_item, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final CharactersAdapter.MyViewHolder holder, final int position) {
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

