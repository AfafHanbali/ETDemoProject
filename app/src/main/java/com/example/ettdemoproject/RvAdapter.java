package com.example.ettdemoproject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import retrofit2.Callback;

public class RvAdapter extends RecyclerView.Adapter<RvAdapter.ViewHolder> {

    Context context;
    String name[];
    String email[];

    public RvAdapter(Context cx, String s1[], String s2[]) { //TODO : naming
        context = cx;
        name = s1;
        email = s2;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //TODO : no need to pass context , you can get from parent
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.item_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        //TODO : good but i always tend to let holder bind
        holder.username.setText(name[position]);
        holder.email.setText(email[position]);
    }

    @Override
    public int getItemCount() {
        return email.length;
    }

    //TODO : convention
    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView username;
        TextView email;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            username = itemView.findViewById(R.id.username);
            email = itemView.findViewById(R.id.email);
        }
    }
}
