package com.example.ettdemoproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.example.ettdemoproject.UI.UsersAdapter;
import com.example.ettdemoproject.networking.JsonPlaceHolder;

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

//TODO : what if i have 2 screens that calls retrofit ? pls rename into UsersListActivty
public class UsersListActivity extends AppCompatActivity implements UsersAdapter.OnUserListener {

    private RecyclerView mListOfUsers;
    private List<User> usersList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mListOfUsers = (RecyclerView) findViewById(R.id.rv_users);
        fetchFromApi();

    }

    public void fetchFromApi() {

        // TODO : i expect a sigelton instance here . what if i need to call retrofit in another screen.
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
                    showToast(Integer.toString(response.code()));
                    // TODO : use a method 'showToast'mith msg parameter and avoiding duplicate code as in "onFailure"
                    
                    return;
                }
                usersList = response.body();
                setupAdapter(usersList);
            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable tt) {
                showToast(tt.getMessage());
            }
        });

    }

    public void setupAdapter(List<User> usersList) {
        UsersAdapter usersAdapter = new UsersAdapter(usersList, this);
        mListOfUsers.setAdapter(usersAdapter);
        mListOfUsers.setLayoutManager(new LinearLayoutManager(this));
    }

    public void showToast(String toastMsg) {
        Toast toast = Toast.makeText(getApplicationContext(), toastMsg, Toast.LENGTH_LONG);
        toast.show();
    }

    @Override
    public void onUserClick(int position) {

        Intent intent = new Intent(this, UserInformation.class);
        intent.putExtra("id", Integer.toString(usersList.get(position).getId()));
        intent.putExtra("name", usersList.get(position).getName());
        intent.putExtra("phone", usersList.get(position).getPhone());
        intent.putExtra("website", usersList.get(position).getWebsite());

        startActivity(intent);

    }
}
