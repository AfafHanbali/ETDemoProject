package com.example.ettdemoproject.UI;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ettdemoproject.R;
import com.example.ettdemoproject.DataModel.User;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author : Afaf Hanbali
 * Created on 2020-Oct-5
 */

public class UsersAdapter extends RecyclerView.Adapter<UsersAdapter.ViewHolder> {


    private List<User> usersList;
    private OnUserListener onUserListener;
    private User userObj;

    public UsersAdapter(OnUserListener onUserListener) {
        this.onUserListener = onUserListener;

    }

    public void setUsersList(List<User> usersList) {
        this.usersList = usersList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_layout, parent, false);
        return new ViewHolder(view, onUserListener);
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
        OnUserListener onUserListener;

        public ViewHolder(@NonNull View itemView, OnUserListener onUserListener) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            this.onUserListener = onUserListener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onUserListener.onUserClick(usersList.get(getAdapterPosition()));
        }

    }

    public interface OnUserListener {
        void onUserClick(User userItem);
    }
}
