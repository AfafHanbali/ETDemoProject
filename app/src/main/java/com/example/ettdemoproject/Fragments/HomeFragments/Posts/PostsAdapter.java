package com.example.ettdemoproject.Fragments.HomeFragments.Posts;

import android.app.Activity;
import android.content.Context;
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


/**
 * @author : Afaf Hanbali
 * Created on 2020-Oct-5
 */

public class PostsAdapter extends RecyclerView.Adapter<PostsAdapter.ViewHolder> {

    private List<Post> postsList;
    private Post postObj;
    private int highlightedRow = -1;
    private Context context;

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

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @BindView(R.id.tv_postTitle)
        TextView postTitleTextView;
        @BindView(R.id.tv_body)
        TextView postBodyTextView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }

        public void onClick(View v) {

        }

    }

}
