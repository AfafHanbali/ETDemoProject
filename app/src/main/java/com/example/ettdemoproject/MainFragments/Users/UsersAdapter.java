package com.example.ettdemoproject.MainFragments.Users;

import android.content.Context;
import android.graphics.drawable.TransitionDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ettdemoproject.Events.UserClickEvent;
import com.example.ettdemoproject.R;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author : Afaf Hanbali
 * Created on 2020-Oct-5
 */

public class UsersAdapter extends RecyclerView.Adapter<UsersAdapter.ViewHolder> {


    public List<User> usersList;
    private User userObj;
    Context context;
    private int highlightedRow = -1;

    public UsersAdapter() {

    }

    public void updateItem(User user) {
        int position = getPosition(user);
        if (position != -1) {
            usersList.set(position, user);
            notifyItemChanged(position);
        }
    }

    public int getPosition(User user) {
        return usersList.indexOf(user);
    }

    public void setUsersList(List<User> usersList) {
        this.usersList = usersList;
    }

    public void setHighlightedRow(int position) {
        highlightedRow = position;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.user_item_layout, parent, false);
        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        userObj = usersList.get(position);
        holder.usernameTextView.setText(userObj.getUsername());
        holder.emailTextView.setText(userObj.getEmail());
        if (!userObj.isFavorite()) {
            holder.favButton.setBackgroundResource(R.drawable.ic_star_border_black_24dp);
        } else {
            holder.favButton.setBackgroundResource(R.drawable.ic_star_black_24dp);
        }

        holder.favButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (usersList.get(position).isFavorite()) {
                    holder.favButton.setBackgroundResource(R.drawable.ic_star_border_black_24dp);
                    usersList.get(position).setFavorite(false);
                } else {
                    holder.favButton.setBackgroundResource(R.drawable.ic_star_black_24dp);
                    usersList.get(position).setFavorite(true);

                }
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
        return usersList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @BindView(R.id.tv_username)
        TextView usernameTextView;
        @BindView(R.id.tv_email)
        TextView emailTextView;
        @BindView(R.id.favButton)
        Button favButton;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onUserClickEvent(usersList.get(getAdapterPosition()));
        }

        private void onUserClickEvent(User user) {
            EventBus.getDefault().post(new UserClickEvent(user));

        }
    }
}
