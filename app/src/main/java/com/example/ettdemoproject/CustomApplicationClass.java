package com.example.ettdemoproject;

import android.app.Application;

import io.branch.referral.Branch;

/**
 * @author : Afaf Hanbali
 * Created on 2020-Oct-5
 */
public class CustomApplicationClass extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        // Branch logging for debugging
        Branch.enableLogging();

        // Branch object initialization
        Branch.getAutoInstance(this);
    }
}
