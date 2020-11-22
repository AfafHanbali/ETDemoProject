package com.example.ettdemoproject.MainFragments.Posts;

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

import com.example.ettdemoproject.DataModel.Post;
import com.example.ettdemoproject.R;

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

public class PostsAdapter extends RecyclerView.Adapter<PostsAdapter.ViewHolder> {


    private static final CharSequence SHARE_CHOOSER_TITLE = "Share with";
    private static final String TYPE = "post";
    private static final String SUBJECT = "Post Details";
    private List<Post> postsList;
    private Post postObj;
    private int highlightedRow = -1;
    private Context context;
    private Activity activity;

    public PostsAdapter(Activity activity) {
        this.activity=activity;
    }

    public void updateItem(Post post) {
        int position = getPosition(post);
        if (position != -1) {
            postsList.set(position, post);
            notifyItemChanged(position);
        }
    }

    public int getPosition(Post post) {
        return postsList.indexOf(post);
    }

    public void setPostsList(List<Post> postsList) {
        this.postsList = postsList;
    }

    public void setHighlightedRow(int position) {
        highlightedRow = position;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.post_item_layout, parent, false);
        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        postObj = postsList.get(position);
        String title = postObj.getTitle();
        String body = postObj.getBody();
        String id = Integer.toString((postObj.getId()));
        holder.postTitleTextView.setText(title);
        holder.postBodyTextView.setText(body);

        holder.shareButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String message = context.getString(R.string.postShareMsg, title);
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
        return postsList.size();
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
        @BindView(R.id.tv_postTitle)
        TextView postTitleTextView;
        @BindView(R.id.tv_body)
        TextView postBodyTextView;
        @BindView(R.id.postShareButton)
        Button shareButton;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }

        public void onClick(View v) {

        }

    }

}
