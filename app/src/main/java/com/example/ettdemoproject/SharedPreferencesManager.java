package com.example.ettdemoproject;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * @author : Afaf Hanbali
 * Created on 2020-Oct-5
 */
public class SharedPreferencesManager {

    private static final String SHARED_PREF_NAME = "FCM Shared Pref.";
    private static final String TOKEN_KEY = "token";

    private static Context context;
    private static SharedPreferencesManager instance;
    SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREF_NAME, context.MODE_PRIVATE);


    public SharedPreferencesManager(Context context) {
        this.context = context;
    }

    public static synchronized SharedPreferencesManager getInstance(Context context){
        if(instance == null){
            instance = new SharedPreferencesManager(context);
        }
        return instance;
    }

    public void storeToken(String token){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(TOKEN_KEY, token);
        editor.apply();
    }

    public String getToken(){
        return sharedPreferences.getString(TOKEN_KEY, null);
    }

}
