package com.example.ettdemoproject.MainFragments.Posts;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.TransitionDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

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

public class PostsAdapter extends RecyclerView.Adapter<PostsAdapter.ViewHolder> {


    private static final CharSequence SHARE_CHOOSER_TITLE = "Share with";
    private static final String HOST = "https://www.etdemoproject.com/";
    private static final String TYPE = "post/";
    private static final String SUBJECT = "Post Details";
    private List<Post> postsList;
    private Post postObj;
    private int highlightedRow = -1;
    Context context;

    public PostsAdapter() {

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
        String id = Integer.toString((postObj.getId() + postObj.getUserId()) - 1);
        holder.postTitleTextView.setText(title);
        holder.postBodyTextView.setText(body);

        holder.shareButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String host = HOST + TYPE + id;
                String message = context.getResources().getString(R.string.postShareMsg, title, host);
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
        }

    }


    @Override
    public int getItemCount() {
        return postsList.size();
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
