package com.example.ettdemoproject.MainFragments.Albums;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.TransitionDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ettdemoproject.DataModel.Album;
import com.example.ettdemoproject.MainFragments.Posts.PostsFragment;
import com.example.ettdemoproject.R;
import com.example.ettdemoproject.UI.UserInformationActivity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.branch.indexing.BranchUniversalObject;
import io.branch.referral.Branch;
import io.branch.referral.BranchError;
import io.branch.referral.SharingHelper;
import io.branch.referral.util.LinkProperties;
import io.branch.referral.util.ShareSheetStyle;

/**
 * @author : Afaf Hanbali
 * Created on 2020-Oct-5
 */

public class AlbumsAdapter extends RecyclerView.Adapter<AlbumsAdapter.ViewHolder> {

    private static final CharSequence SHARE_CHOOSER_TITLE = "Share with";
    private static final String TYPE = "album";
    private static final String SUBJECT = "Album Details";

    private List<Album> albumList;
    private Album albumObj;
    private Context context;
    private Activity activity;
    private int highlightedRow = -1;

    public AlbumsAdapter(Activity activity) {
        this.activity=activity;
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
                String message = context.getString(R.string.albumShareMsg, title);
                BranchUniversalObject buo = getBranchUniversalObject(id, title);
                LinkProperties lp = getLinkProperties();
                ShareSheetStyle ss = getShareSheetStyle(message);

                buo.showShareSheet(activity, lp, ss, new Branch.BranchLinkShareListener() {
                    @Override
                    public void onShareLinkDialogLaunched() {
                    }

                    @Override
                    public void onShareLinkDialogDismissed() {
                    }

                    @Override
                    public void onLinkShareResponse(String sharedLink, String sharedChannel, BranchError error) {
                    }

                    @Override
                    public void onChannelSelected(String channelName) {
                    }
                });

            }
        });

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


    @Override
    public int getItemCount() {
        return albumList.size();
    }

    private BranchUniversalObject getBranchUniversalObject(String id, String title){
        return new BranchUniversalObject()
                .setCanonicalIdentifier(id)
                .setTitle(title)
                .setContentDescription(TYPE)
                .setContentIndexingMode(BranchUniversalObject.CONTENT_INDEX_MODE.PUBLIC)
                .setLocalIndexMode(BranchUniversalObject.CONTENT_INDEX_MODE.PUBLIC);
    }

    private LinkProperties getLinkProperties(){
        return  new LinkProperties()
                .setChannel("facebook")
                .setFeature("sharing")
                .setCampaign("content 123 launch")
                .setStage("new user");
    }

    private ShareSheetStyle getShareSheetStyle(String message) {
        return new ShareSheetStyle(context, SUBJECT, message)
                .setCopyUrlStyle(ContextCompat.getDrawable(context, android.R.drawable.ic_menu_send), "Copy", "Added to clipboard")
                .setMoreOptionStyle(ContextCompat.getDrawable(context, android.R.drawable.ic_menu_search), "Show more")
                .addPreferredSharingOption(SharingHelper.SHARE_WITH.FACEBOOK)
                .addPreferredSharingOption(SharingHelper.SHARE_WITH.GMAIL)
                .setAsFullWidthStyle(true)
                .setSharingTitle(String.valueOf(SHARE_CHOOSER_TITLE));
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
