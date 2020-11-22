package com.example.ettdemoproject.networking;

import com.example.ettdemoproject.DataModel.Album;
import com.example.ettdemoproject.DataModel.Post;
import com.example.ettdemoproject.DataModel.User;

import java.util.List;

import io.reactivex.Single;
import retrofit2.http.GET;

/**
 * @author : Afaf Hanbali
 * Created on 2020-Oct-5
 */

public interface JsonPlaceHolder {

    public String API_USERS = "users";
    public String API_POSTS = "posts";
    public String API_ALBUMS = "albums";

    @GET(API_USERS)
    Single<List<User>> getUsers();
    @GET(API_ALBUMS)
    Single<List<Album>> getAlbums();
    @GET(API_POSTS)
    Single<List<Post>> getPosts();


}
