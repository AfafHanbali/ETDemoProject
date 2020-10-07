package com.example.ettdemoproject.networking;

import com.example.ettdemoproject.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * @author : Afaf Hanbali
 * Created on 2020-Oct-5
 */

public interface JsonPlaceHolder {

    //TODO : just define as String , since its a member of interface its static final by def .
    public String api = "users";
    

    @GET(api)
    Call<List<User>> getUsers();
}
