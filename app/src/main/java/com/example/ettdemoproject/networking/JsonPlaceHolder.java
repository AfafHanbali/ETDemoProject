package com.example.ettdemoproject.networking;

import com.example.ettdemoproject.DataModel.Album;
import com.example.ettdemoproject.DataModel.Post;
import com.example.ettdemoproject.DataModel.User;

import java.util.List;

import io.reactivex.Single;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * @author : Afaf Hanbali
 * Created on 2020-Oct-5
 */

public interface JsonPlaceHolder {

    public String API_USERS = "users";
    public String API_POSTS = "posts";
    public String API_ALBUMS = "albums";
    public String QUERY_PAGE = "page";
    public String QUERY_LIMIT = "limit";

    @GET(API_USERS)
    Single<List<User>> getUsers();

    @GET(API_ALBUMS)
    Single<List<Album>> getAlbums(
           // @Query(QUERY_PAGE) int page,
            //@Query(QUERY_LIMIT) int limit
    );

    @GET(API_POSTS)
    Single<List<Post>> getPosts();


}
