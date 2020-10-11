package com.example.ettdemoproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;

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
    private Toolbar toolbar;
    private ProgressDialog progressDialog;
    public static final String progressMsgTitle = ("Just a Sec...");
    public static final String progressMsgContent = ("The List of Users is loading...");
    private UserInformationActivity userInformationActivity;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = findViewById(R.id.mainToolBar);
        listOfUsers = findViewById(R.id.rv_users);
        setToolBarOptions(toolbar);
        buildProgressBar();
        fetchFromApi();

    }

    private void setToolBarOptions(Toolbar toolbar) {
        toolbar.setTitle("ETDemo Project");
        toolbar.setTitleTextColor(Color.WHITE);
    }


    public void fetchFromApi() {

        Retrofit retrofit = RetrofitHandler.getInstance();
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
            showToast(jObjError.getJSONObject("error").getString("message"));

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
        progressDialog.setMessage(progressMsgContent);
        progressDialog.setTitle(progressMsgTitle);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
    }

    @Override
    public void onUserClick(User userList) {
        //TODO : pls also dont call actvities directly , instead build a public static Intent startActivity inside UserInformationActivity activity .
        // and let it write/read its own keys .

        userInformationActivity.startUserInfoActivity = new Intent(this, UserInformationActivity.class);
        userInformationActivity.startUserInfoActivity.putExtra("userListObject", userList);
        startActivity(userInformationActivity.startUserInfoActivity);

    }
}
