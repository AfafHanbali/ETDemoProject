package com.example.ettdemoproject.DataModel;

/**
 * @author : Afaf Hanbali
 * Created on 2020-Oct-5
 */

public class Album {
    private int albumId;
    private int id;
    private String title;
    private String url;
    private String thumbnailUrl;

    public int getUserId() {
        return albumId;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getUrl() {
        return url;
    }

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }
}
