package com.example.ettdemoproject.UI;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.example.ettdemoproject.R;
import com.example.ettdemoproject.RetrofitActivity;
/**
 * @author : Afaf Hanbali
 * Created on 2020-Oct-5
 */

public class ProgressSplashScreen extends AppCompatActivity {

    private static final long SPLASH_TIMEOUT = 3000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_progress_splash_screen);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(ProgressSplashScreen.this, RetrofitActivity.class);

                startActivity(intent);
                finish();
            }
        }, SPLASH_TIMEOUT);
    }
}
