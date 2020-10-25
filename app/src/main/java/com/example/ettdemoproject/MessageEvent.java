package com.example.ettdemoproject;

/**
 * @author : Afaf Hanbali
 * Created on 2020-Oct-5
 */

//TODO : its a favorite click event
public class MessageEvent {

    public final boolean isFav;
    //TODO : pls dont pass position and query later .because position might change
    // try to pass the whole object/model .
    public final int position;


    public MessageEvent(boolean message, int position) {
        this.isFav = message;
        this.position = position;
    }
}
