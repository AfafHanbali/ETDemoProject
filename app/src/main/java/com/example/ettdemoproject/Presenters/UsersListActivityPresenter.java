package com.example.ettdemoproject.Presenters;

import com.example.ettdemoproject.DataModel.User;

import java.util.List;

/**
 * @author : Afaf Hanbali
 * Created on 2020-Oct-5
 */
public class UsersListActivityPresenter {

    private List<User> usersList;
    private View view;

    public UsersListActivityPresenter(View view, List<User> usersList) {
        this.usersList = usersList;
        this.view = view;
    }


    public interface View {

    }
}
