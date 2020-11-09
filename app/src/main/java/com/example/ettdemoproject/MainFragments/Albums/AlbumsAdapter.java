package com.example.ettdemoproject.MainFragments.Albums;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.TransitionDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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

    private static final CharSequence SHARE_CHOOSER_TITLE = "Share with";
    private static final String HOST = "https://www.etdemoproject.com/";
    private static final String TYPE = "album/";
    private static final String SUBJECT = "Album Details";

    private List<Album> albumList;
    private Album albumObj;
    private Context context;
    private int highlightedRow = -1;

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
        holder.userIdTextView.setText(albumId);
        holder.albumTitleTextView.setText(title);

        holder.shareButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String host = HOST + TYPE + id;
                String message = context.getString(R.string.albumShareMsg, title, host);
                Intent implicitIntent = new Intent(Intent.ACTION_SEND);
                implicitIntent.putExtra(Intent.EXTRA_SUBJECT, SUBJECT);
                implicitIntent.putExtra(Intent.EXTRA_TEXT, message);
                implicitIntent.setType("message/rfc822");
                context.startActivity(Intent.createChooser(implicitIntent, SHARE_CHOOSER_TITLE));

            }
        });

        if (highlightedRow == position) {
            TransitionDrawable transitionDrawable = (TransitionDrawable) context.getResources().getDrawable(R.drawable.transition_drawable);
            holder.itemView.setBackgroundDrawable(transitionDrawable);
            transitionDrawable.startTransition(1000);
            clearHighlightedRow();
        }

    }

    private void clearHighlightedRow() {
        highlightedRow = -1;
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
        @BindView(R.id.albumShareButton)
        Button shareButton;


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
