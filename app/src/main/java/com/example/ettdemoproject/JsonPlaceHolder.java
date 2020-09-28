package com.example.ettdemoproject;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface JsonPlaceHolder {

    @GET("users")
    Call<List<Users>> getUsers();
}
