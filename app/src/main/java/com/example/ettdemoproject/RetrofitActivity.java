package com.example.ettdemoproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import com.example.ettdemoproject.UI.RvAdapter;
import com.example.ettdemoproject.networking.JsonPlaceHolder;
import com.example.ettdemoproject.networking.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


/**
 * @author : Afaf Hanbali
 * Created on 2020-Oct-5
 */

public class RetrofitActivity extends AppCompatActivity {

    private RecyclerView mListOfUsers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mListOfUsers = (RecyclerView) findViewById(R.id.rv_listOfUsers);
        fetchFromApi();

    }

    public void fetchFromApi() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://jsonplaceholder.typicode.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        JsonPlaceHolder jsonPlaceHolder = retrofit.create(JsonPlaceHolder.class);
        Call<List<User>> call = jsonPlaceHolder.getUsers();
        call.enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                if (!response.isSuccessful()) {
                    Toast toast = Toast.makeText(getApplicationContext(), response.code(), Toast.LENGTH_LONG);
                    toast.show();
                    return;
                }
                List<User> usersList = response.body();
                setupAdapter(usersList);
            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable tt) {
                Toast toast = Toast.makeText(getApplicationContext(), tt.getMessage(), Toast.LENGTH_LONG);
                toast.show();
            }
        });

    }

    public void setupAdapter(List<User> usersList) {
        RvAdapter rvAdapter = new RvAdapter(usersList);
        mListOfUsers.setAdapter(rvAdapter);
        mListOfUsers.setLayoutManager(new LinearLayoutManager(this));
    }
}
