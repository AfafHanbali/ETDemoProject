package com.example.ettdemoproject;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

//TODO : save constants in stand alone or const
//TODO : convention
//TODO : packaging
//TODO : velocity template

public interface JsonPlaceHolder {

    @GET("users")
    Call<List<Users>> getUsers();
}
