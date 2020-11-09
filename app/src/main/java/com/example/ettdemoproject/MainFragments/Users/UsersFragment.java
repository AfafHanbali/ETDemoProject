package com.example.ettdemoproject.MainFragments.Users;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ettdemoproject.Events.FavClickEvent;
import com.example.ettdemoproject.Events.UserClickEvent;
import com.example.ettdemoproject.R;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * @author : Afaf Hanbali
 * Created on 2020-Oct-5
 */
public class UsersFragment extends Fragment implements UsersListActivityPresenter.View {

    public static final String PROGRESS_MSG_TITLE = ("Just a Sec...");
    public static final String PROGRESS_MSG_CONTENT = ("The List of Users is loading...");
    private int position = -1;


    @BindView(R.id.rv_users)
    RecyclerView listOfUsers;

    private ProgressDialog progressDialog;
    private UsersAdapter usersAdapter = new UsersAdapter();
    private UsersListActivityPresenter presenter;
    private Unbinder unbinder;

    public UsersFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        buildProgressDialog();
        presenter = new UsersListActivityPresenter(this);
        presenter.loadUsers();

        View view = inflater.inflate(R.layout.fragment_users, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
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


    @Override
    public void onDestroy() {
        super.onDestroy();
        presenter.disposables.clear();
    }

    @Override
    public void displayUsers(List<User> usersList) {
        setupAdapter(usersList);
    }

    public void setPosition(int position) {
        this.position = position;
    }

    private void setupAdapter(List<User> usersList) {
        usersAdapter.setUsersList(usersList);
        listOfUsers.setAdapter(usersAdapter);
        listOfUsers.setLayoutManager(new LinearLayoutManager(getContext()));

        if (position != -1) {
            usersAdapter.setHighlightedRow(position);
            listOfUsers.scrollToPosition(position);
        }
    }


    @Override
    public void showToast(String toastMsg) {
        Toast toast = Toast.makeText(getContext(), toastMsg, Toast.LENGTH_LONG);
        toast.show();
    }


    private void buildProgressDialog() {
        progressDialog = new ProgressDialog(getContext(), R.style.progressDialog);
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

        UserInformationActivity.startScreen(getActivity(), event.user);

    }

}
