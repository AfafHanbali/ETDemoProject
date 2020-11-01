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
import com.example.ettdemoproject.Presenters.UserInformationActivityPresenter;
import com.example.ettdemoproject.Presenters.UsersListActivityPresenter;
import com.example.ettdemoproject.networking.FavClickEvent;
import com.example.ettdemoproject.R;
import com.example.ettdemoproject.networking.RetrofitHandler;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;


/**
 * @author : Afaf Hanbali
 * Created on 2020-Oct-5
 */

public class UsersListActivity extends AppCompatActivity implements UsersAdapter.OnUserListener, UsersListActivityPresenter.View {

    public static final String APP_TITLE = "ETDemo Project";
    public static final String PROGRESS_MSG_TITLE = ("Just a Sec...");
    public static final String PROGRESS_MSG_CONTENT = ("The List of Users is loading...");
    public static final String BASE_URL = "https://jsonplaceholder.typicode.com/";

    @BindView(R.id.rv_users)
    RecyclerView listOfUsers;
    private List<User> usersList;
    @BindView(R.id.mainToolBar)
    Toolbar mainToolbar;
    private ProgressDialog progressDialog;
    private UsersAdapter usersAdapter = new UsersAdapter(this);
    private UsersListActivityPresenter presenter;
    private CompositeDisposable disposables = new CompositeDisposable();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
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
    public void onFavClickEvent(FavClickEvent event) {

        int i;
        for (i = 0; i < usersList.size(); i++) {
            if (usersList.get(i).getId() == event.user.getId()) { // TODO : you could also override Equals on User
                break;
            }
        }
        // TODO : move the above logic to inside the adapter since notifyItemChanged() can be called there as well .
        usersList.get(i).setFavorite(event.user.isFavorite());
        usersAdapter.notifyItemChanged(i);

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


    public void fetchFromApi() {

        Single<List<User>> singleObservable = RetrofitHandler.getInstance(BASE_URL).getUsers();
        singleObservable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<List<User>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        disposables.add(d);
                    }

                    @Override
                    public void onSuccess(List<User> users) {
                        progressDialog.dismiss();
                        usersList = users;
                        setupAdapter(usersList);
                    }

                    @Override
                    public void onError(Throwable e) {
                        progressDialog.dismiss();
                        showToast(e.getMessage());
                    }
                });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        disposables.clear();
    }

    public void setupAdapter(List<User> usersList) {
        usersAdapter.setUsersList(usersList);
        listOfUsers.setAdapter(usersAdapter);
        presenter = new UsersListActivityPresenter(this, usersList);
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
        progressDialog.show();
    }

    @Override
    public void onUserClick(User userItem) {
        UserInformationActivity.startScreen(this, userItem);
    }
}
