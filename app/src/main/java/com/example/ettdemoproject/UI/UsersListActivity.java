package com.example.ettdemoproject.UI;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;

import com.example.ettdemoproject.DataModel.User;
import com.example.ettdemoproject.Events.UserClickEvent;
import com.example.ettdemoproject.Presenters.UsersListActivityPresenter;
import com.example.ettdemoproject.Events.FavClickEvent;
import com.example.ettdemoproject.R;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * @author : Afaf Hanbali
 * Created on 2020-Oct-5
 */

public class UsersListActivity extends AppCompatActivity implements UsersListActivityPresenter.View {

    public static final String APP_TITLE = "ETDemo Project";
    public static final String PROGRESS_MSG_TITLE = ("Just a Sec...");
    public static final String PROGRESS_MSG_CONTENT = ("The List of Users is loading...");

    @BindView(R.id.rv_users)
    RecyclerView listOfUsers;
    @BindView(R.id.mainToolBar)
    Toolbar mainToolbar;
    private ProgressDialog progressDialog;
    private UsersAdapter usersAdapter = new UsersAdapter();
    private UsersListActivityPresenter presenter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setToolBarOptions(mainToolbar);
        buildProgressDialog();
        presenter = new UsersListActivityPresenter(this);
        presenter.loadUsers();

    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void onFavClickEvent(FavClickEvent event) {

        usersAdapter.updateItem(event.user);
        FavClickEvent stickyEvent = EventBus.getDefault().getStickyEvent(FavClickEvent.class);
        if (stickyEvent != null) {
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


    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.disposables.clear();
    }

    @Override
    public void displayUsers(List<User> usersList) {
        setupAdapter(usersList);
    }

    private void setupAdapter(List<User> usersList) {
        usersAdapter.setUsersList(usersList);
        listOfUsers.setAdapter(usersAdapter);
        listOfUsers.setLayoutManager(new LinearLayoutManager(this));
    }


    @Override
    public void showToast(String toastMsg) {
        Toast toast = Toast.makeText(getApplicationContext(), toastMsg, Toast.LENGTH_LONG);
        toast.show();
    }


    private void buildProgressDialog() {
        progressDialog = new ProgressDialog(this, R.style.progressDialog);
        progressDialog.setMax(100);
        progressDialog.setMessage(PROGRESS_MSG_CONTENT);
        progressDialog.setTitle(PROGRESS_MSG_TITLE);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
    }

    @Override
    public void showProgressDialog() {
        progressDialog.show();
    }

    @Override
    public void hideProgressDialog() {
        progressDialog.dismiss();
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onUserClickEvent(UserClickEvent event) {

        UserInformationActivity.startScreen(this, event.user);

    }

}
