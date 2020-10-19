package com.example.ettdemoproject.DataModel;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;

import com.example.ettdemoproject.MessageEvent;
import com.example.ettdemoproject.R;
import com.example.ettdemoproject.UI.UserInformationActivity;
import com.example.ettdemoproject.UI.UsersAdapter;
import com.example.ettdemoproject.networking.JsonPlaceHolder;
import com.example.ettdemoproject.networking.RetrofitHandler;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
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

    public static final String APP_TITLE = "ETDemo Project";
    public static final String PROGRESS_MSG_TITLE = ("Just a Sec...");
    public static final String PROGRESS_MSG_CONTENT = ("The List of Users is loading...");
    public static final String BASE_URL = "https://jsonplaceholder.typicode.com/";


    private RecyclerView listOfUsers;
    private List<User> usersList;
    private Toolbar mainToolbar;
    private ProgressDialog progressDialog;


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
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void onMessageEvent(MessageEvent event) {
        usersList.get(event.position).setFavorite(event.isFav);
        setupAdapter(usersList);

        MessageEvent stickyEvent = EventBus.getDefault().getStickyEvent(MessageEvent.class);
        if(stickyEvent != null) {
            EventBus.getDefault().removeStickyEvent(stickyEvent);
        }
    }

    @Override
    public void onStop() {
        EventBus.getDefault().unregister(this);
        super.onStop();
    }


    private void setToolBarOptions(Toolbar toolbar) {
        toolbar.setTitle(APP_TITLE);
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
            if (response.errorBody() != null) {
                JSONObject jObjError = new JSONObject(response.errorBody().string());
                showToast(jObjError.getJSONObject("error").getString("isFav"));
            } else {
                finish();
            }

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
        UserInformationActivity.startScreen(this, userItem, position);
    }
}
