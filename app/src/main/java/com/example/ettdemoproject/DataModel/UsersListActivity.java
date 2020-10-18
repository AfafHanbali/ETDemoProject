package com.example.ettdemoproject.DataModel;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;

import com.example.ettdemoproject.R;
import com.example.ettdemoproject.UI.UsersAdapter;
import com.example.ettdemoproject.networking.JsonPlaceHolder;
import com.example.ettdemoproject.networking.RetrofitHandler;

import org.json.JSONObject;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;


/**
 * @author : Afaf Hanbali
 * Created on 2020-Oct-5
 */

public class UsersListActivity extends AppCompatActivity implements UsersAdapter.OnUserListener {


    private RecyclerView listOfUsers;
    private List<User> usersList;
    private Toolbar mainToolbar;
    private ProgressDialog progressDialog;
    public static final String PROGRESS_MSG_TITLE = ("Just a Sec...");
    public static final String PROGRESS_MSG_CONTENT = ("The List of Users is loading...");
    public static final String BASE_URL = "https://jsonplaceholder.typicode.com/";
    public static final String POSITION_KEY = "position";
    public static final int REQUEST_CODE = 11;
    private static final String BOOLEAN_KEY = "isFavorite";
    private UserInformationActivity userInformationActivity; //TODO : remove .


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mainToolbar = findViewById(R.id.mainToolBar);
        listOfUsers = findViewById(R.id.rv_users);
        setToolBarOptions(mainToolbar);
        buildProgressBar();
        fetchFromApi();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK) {
            Boolean isFavorite = Boolean.parseBoolean(data.getStringExtra(BOOLEAN_KEY));
            int position = Integer.parseInt(data.getStringExtra(POSITION_KEY));
            usersList.get(position).setFavorite(isFavorite);
            setupAdapter(usersList);
        }
    }

    private void setToolBarOptions(Toolbar toolbar) {
        toolbar.setTitle("ETDemo Project");// TODO : convention const string .
        toolbar.setTitleTextColor(Color.WHITE);
    }


    public void fetchFromApi() {

        Retrofit retrofit = RetrofitHandler.buildRetrofit(BASE_URL);
        JsonPlaceHolder jsonPlaceHolder = retrofit.create(JsonPlaceHolder.class);
        Call<List<User>> call = jsonPlaceHolder.getUsers();
        progressDialog.show();
        call.enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                progressDialog.dismiss();
                if (!response.isSuccessful()) {
                    showResponseMsg(response);
                }
                usersList = response.body();
                setupAdapter(usersList);
            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable tt) {
                progressDialog.dismiss();
                showToast(tt.getMessage());
            }
        });

    }

    private void showResponseMsg(Response<List<User>> response) {
        try {
            JSONObject jObjError = new JSONObject(response.errorBody().string());
            showToast(jObjError.getJSONObject("error").getString("message"));//TODO : might produce NPE .

        } catch (Exception e) {
            showToast(e.getMessage());
        }
    }

    public void setupAdapter(List<User> usersList) {
        UsersAdapter usersAdapter = new UsersAdapter(usersList, this);
        listOfUsers.setAdapter(usersAdapter);
        listOfUsers.setLayoutManager(new LinearLayoutManager(this));
    }


    public void showToast(String toastMsg) {
        Toast toast = Toast.makeText(getApplicationContext(), toastMsg, Toast.LENGTH_LONG);
        toast.show();
    }

    public void buildProgressBar() {
        progressDialog = new ProgressDialog(this, R.style.progressDialog);
        progressDialog.setMax(100);
        progressDialog.setMessage(PROGRESS_MSG_CONTENT);
        progressDialog.setTitle(PROGRESS_MSG_TITLE);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
    }

    @Override
    public void onUserClick(User userItem, int position) {
        //TODO : use class name to refer to static method .
        userInformationActivity.startScreen(this, userItem, position);
    }
}
