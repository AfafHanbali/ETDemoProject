package com.example.ettdemoproject.UI;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ettdemoproject.R;
import com.example.ettdemoproject.DataModel.User;

import java.util.List;

/**
 * @author : Afaf Hanbali
 * Created on 2020-Oct-5
 */

public class UsersAdapter extends RecyclerView.Adapter<UsersAdapter.ViewHolder> {


    private List<User> usersList;
    private OnUserListener onUserListener;

    public UsersAdapter(List<User> usersList, OnUserListener onUserListener) {
        this.usersList = usersList;
        this.onUserListener = onUserListener;

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

        holder.usernameTextView.setText(usersList.get(position).getUsername());
        holder.emailTextView.setText(usersList.get(position).getEmail());
        if(!usersList.get(position).isFavorite()) {
            holder.userStar.setImageResource(R.drawable.ic_star_border_24dp);
        }
        else{
            holder.userStar.setImageResource(R.drawable.ic_star_24dp);
        }
    }

    @Override
    public int getItemCount() {
        return usersList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView usernameTextView;
        TextView emailTextView;
        ImageView userStar;
        OnUserListener onUserListener;

        public ViewHolder(@NonNull View itemView, OnUserListener onUserListener) {
            super(itemView);
            usernameTextView = itemView.findViewById(R.id.tv_username);
            emailTextView = itemView.findViewById(R.id.tv_email);
            userStar=itemView.findViewById(R.id.iv_listStar);
            this.onUserListener = onUserListener;
            itemView.setOnClickListener(this);

            userStar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (usersList.get(getAdapterPosition()).isFavorite()) {
                        userStar.setImageResource(R.drawable.ic_star_border_24dp);
                        usersList.get(getAdapterPosition()).setFavorite(false);
                    } else {
                        userStar.setImageResource(R.drawable.ic_star_24dp);
                        usersList.get(getAdapterPosition()).setFavorite(true);
                    }
                }
            });
        }

        @Override
        public void onClick(View v) {
            onUserListener.onUserClick(usersList.get(getAdapterPosition()), getAdapterPosition());
        }

    }

    public interface OnUserListener {
        void onUserClick(User userList, int position);
    }
}
