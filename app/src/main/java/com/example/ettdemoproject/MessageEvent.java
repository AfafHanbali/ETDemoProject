package com.example.ettdemoproject;

/**
 * @author : Afaf Hanbali
 * Created on 2020-Oct-5
 */
public class MessageEvent {

    public final boolean isFav;
    public final int position;


    public MessageEvent(boolean message, int position) {
        this.isFav = message;
        this.position = position;
    }
}
