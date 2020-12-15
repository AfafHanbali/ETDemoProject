package com.example.ettdemoproject.Fragments.HomeFragments.Albums;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.TransitionDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ettdemoproject.DataModel.Album;
import com.example.ettdemoproject.R;
import com.squareup.picasso.Picasso;

import java.util.List;
import java.util.UUID;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author : Afaf Hanbali
 * Created on 2020-Oct-5
 */

public class AlbumsAdapter extends RecyclerView.Adapter<AlbumsAdapter.ViewHolder> {

    private List<Album> albumList;
    private Album albumObj;
    private Context context;
    private Activity activity;
    private int highlightedRow = -1;

    public AlbumsAdapter(Activity activity, List<Album> albumList) {
        this.activity = activity;
        this.albumList = albumList;
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

    public void setUsersList(List<Album> albumList) {
        this.albumList = albumList;
        notifyDataSetChanged();
    }


    public void setHighlightedRow(int position) {
        highlightedRow = position;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.album_item_layout, parent, false);
        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        albumObj = albumList.get(position);
        String albumId = context.getString(R.string.albumId, Integer.toString(albumObj.getUserId()), Integer.toString(albumObj.getId()));
        String id = Integer.toString((albumObj.getId()));
        String title = albumObj.getTitle();
        String url = albumObj.getUrl();
        Picasso.get().load(url).into(holder.albumImage);
        holder.albumTitleTextView.setText(title);

        if (highlightedRow == position) {
            TransitionDrawable transitionDrawable = (TransitionDrawable) context.getResources().getDrawable(R.drawable.transition_drawable);
            holder.itemView.setBackground(transitionDrawable);
            transitionDrawable.startTransition(1500);
            clearHighlightedRow();
        }

    }

    private void clearHighlightedRow() {
        highlightedRow = -1;
    }

    private String getRandomString(){
        String uuid = UUID.randomUUID().toString();
        return uuid;
    }


    @Override
    public int getItemCount() {
        return albumList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @BindView(R.id.tv_albumUrl)
        ImageView albumImage;
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
