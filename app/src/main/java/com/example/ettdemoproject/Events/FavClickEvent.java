package com.example.ettdemoproject.Events;

import com.example.ettdemoproject.MainFragments.Users.User;

/**
 * @author : Afaf Hanbali
 * Created on 2020-Oct-5
 */

public class FavClickEvent {

    public final User user;

    public FavClickEvent(User user) {
        this.user = user;
    }

}
