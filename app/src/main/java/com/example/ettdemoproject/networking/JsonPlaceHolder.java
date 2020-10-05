package com.example.ettdemoproject.networking;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * @author : Afaf Hanbali
 * Created on 2020-Oct-5
 */

public interface JsonPlaceHolder {

    public static final String api = "users";

    @GET(api)
    Call<List<User>> getUsers();
}
