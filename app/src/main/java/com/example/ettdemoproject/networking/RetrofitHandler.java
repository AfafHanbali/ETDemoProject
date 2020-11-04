package com.example.ettdemoproject.networking;

import com.example.ettdemoproject.MainFragments.Albums.Album;
import com.example.ettdemoproject.MainFragments.Posts.Post;
import com.example.ettdemoproject.MainFragments.Users.User;

import java.util.List;

import io.reactivex.Single;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * @author : Afaf Hanbali
 * Created on 2020-Oct-5
 */

public class RetrofitHandler {

    private static RetrofitHandler retrofitHandler;
    private JsonPlaceHolder jsonPlaceHolder;

    public RetrofitHandler(String baseUrl) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        jsonPlaceHolder = retrofit.create(JsonPlaceHolder.class);
    }

    public static RetrofitHandler getInstance(String url) {
        if (retrofitHandler == null) {
            retrofitHandler = new RetrofitHandler(url);
        }
        return retrofitHandler;
    }

    public Single<List<User>> getUsers(){
        return jsonPlaceHolder.getUsers();
    }

    public Single<List<Post>> getPosts(){
        return jsonPlaceHolder.getPosts();
    }

    public Single<List<Album>> getAlbums(){
        return jsonPlaceHolder.getAlbums();
    }

}
