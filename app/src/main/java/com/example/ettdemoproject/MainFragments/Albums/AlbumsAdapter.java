package com.example.ettdemoproject.MainFragments.Albums;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ettdemoproject.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author : Afaf Hanbali
 * Created on 2020-Oct-5
 */

public class AlbumsAdapter extends RecyclerView.Adapter<AlbumsAdapter.ViewHolder> {


    private List<Album> albumList;
    private Album albumObj;
    Context context;

    public AlbumsAdapter() {

    }

    public void updateItem(Album album) {
        int position = getPosition(album);
        if (position != -1) {
            albumList.set(position, album);
            notifyItemChanged(position);
        }
    }

    public int getPosition(Album album) {
        return albumList.indexOf(album);
    }

    public void setAlbumList(List<Album> albumList) {
        this.albumList = albumList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        context=parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.album_item_layout, parent, false);
        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        albumObj = albumList.get(position);
        String albumId = context.getString(R.string.albumId, Integer.toString(albumObj.getUserId()), Integer.toString(albumObj.getId()));
        holder.userIdTextView.setText(albumId);
        holder.albumTitleTextView.setText(albumObj.getTitle());

    }


    @Override
    public int getItemCount() {
        return albumList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @BindView(R.id.tv_albumUserId)
        TextView userIdTextView;
        @BindView(R.id.tv_albumTitle)
        TextView albumTitleTextView;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {

        }


    }

}
