package com.example.ettdemoproject.UI;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ettdemoproject.R;
import com.example.ettdemoproject.networking.User;

import java.util.List;

/**
 * @author : Afaf Hanbali
 * Created on 2020-Oct-5
 */

//TODO : all adapter that extend RecyclerView.Adapter are rvAdapters . pls rename into sth like 'UsersAdapter'
public class RvAdapter extends RecyclerView.Adapter<RvAdapter.ViewHolder> {

    List<User> usersList;

    public RvAdapter(List<User> usersList) {
        this.usersList = usersList;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.usernameTextView.setText(usersList.get(position).getUsername());
        holder.emailTextView.setText(usersList.get(position).getEmail());

    }

    @Override
    public int getItemCount() {
        return usersList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView usernameTextView;
        TextView emailTextView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            usernameTextView = itemView.findViewById(R.id.tv_username);
            emailTextView = itemView.findViewById(R.id.tv_email);
        }
    }
}
