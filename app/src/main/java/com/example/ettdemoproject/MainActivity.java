package com.example.ettdemoproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.TextView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


//TODO : velocity template
//TODO : convention

public class MainActivity extends AppCompatActivity {

    RecyclerView Rc;
    // TODO : remove
    String names[] = new String[10];
    String emails[] = new String[10];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //TODO : split into separate methods
        Rc = (RecyclerView) findViewById(R.id.recyclerview);
        //TODO : separate class
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://jsonplaceholder.typicode.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        JsonPlaceHolder jsonPlaceHolder = retrofit.create(JsonPlaceHolder.class);
        Call<List<Users>> call = jsonPlaceHolder.getUsers();
        //TODO : loading progress
        call.enqueue(new Callback<List<Users>>() {
            @Override
            public void onResponse(Call<List<Users>> call, Response<List<Users>> response) {
                if (!response.isSuccessful()) {
                    //TODO : feed back i.e toast .

                    // t.setText("Code: " + response.code());

                    return;
                }

                List<Users> usersList = response.body();
                int i = 0;
                for (Users user : usersList) {
                    names[i] = user.getUsername();
                    emails[i] = user.getEmail();
                    i++;
                }

            }

            @Override
            public void onFailure(Call<List<Users>> call, Throwable tt) {
                //TODO : feed back i.e toast
                //t.setText(tt.getMessage());
            }
        });

        // TODO : split into setup+ populate
        RvAdapter rvAdapter = new RvAdapter(this, names, emails);
        Rc.setAdapter(rvAdapter);
        Rc.setLayoutManager(new LinearLayoutManager(this));
    }
}
