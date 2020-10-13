package com.example.ettdemoproject.networking;

import androidx.annotation.NonNull;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * @author : Afaf Hanbali
 * Created on 2020-Oct-5
 */

public class RetrofitHandler {

    private static Retrofit retrofit;

    public static Retrofit buildRetrofit(String baseUrl) {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}
