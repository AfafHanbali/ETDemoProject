package com.example.ettdemoproject.networking;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * @author : Afaf Hanbali
 * Created on 2020-Oct-5
 */

//TODO : naming . this is not an activity , it has no registry in manifest and its not a screen. maybe call it RetrofitHandler
public class RetrofitActivity {

    private static Retrofit retrofit;

    private RetrofitActivity() {

    }

    public static Retrofit getInstance() {
        //TODO :  if the check+initialization is done here ? why do you need a def no-arg constructor. i suggest you move the init to constructor .
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl("https://jsonplaceholder.typicode.com/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}
