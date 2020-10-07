package com.example.ettdemoproject.networking;

/**
 * @author : Afaf Hanbali
 * Created on 2020-Oct-5
 */


//TODO : this is a data model , it doent belong to the networking package.

public class User {


    private String username;
    private String email;


    //TODO : add the otehr data you have in response in prepare for second stage (opening details screen)
    public User() {
    }

    public String getUsername() {

        return username;
    }

    public String getEmail() {

        return email;
    }

}
