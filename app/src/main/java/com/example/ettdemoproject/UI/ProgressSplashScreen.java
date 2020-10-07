package com.example.ettdemoproject.UI;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.example.ettdemoproject.R;
import com.example.ettdemoproject.UsersListActivity;

/**
 * @author : Afaf Hanbali
 * Created on 2020-Oct-5
 */

public class ProgressSplashScreen extends AppCompatActivity {

    private static final long SPLASH_TIMEOUT = 3000;

    //TODO : this is not what i meant by using progress !
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_progress_splash_screen);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(ProgressSplashScreen.this, UsersListActivity.class);

                startActivity(intent);
                finish();
            }
        }, SPLASH_TIMEOUT);
    }
}
