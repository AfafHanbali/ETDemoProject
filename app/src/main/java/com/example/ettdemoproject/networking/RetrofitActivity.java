package com.example.ettdemoproject.networking;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * @author : Afaf Hanbali
 * Created on 2020-Oct-5
 */
public class RetrofitActivity {

    private static Retrofit retrofit;

    private RetrofitActivity() {

    }

    public static Retrofit getInstance() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl("https://jsonplaceholder.typicode.com/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}
