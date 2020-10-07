package com.example.ettdemoproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class UserInformation extends AppCompatActivity {

    private TextView id;
    private TextView name;
    private TextView phone;
    private TextView website;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_information);

        id=findViewById(R.id.tv_id);
        name=findViewById(R.id.tv_name);
        phone=findViewById(R.id.tv_phone);
        website=findViewById(R.id.tv_website);

        Intent intent = getIntent();
        id.setText(intent.getStringExtra("id"));
        name.setText(intent.getStringExtra("name"));
        phone.setText(intent.getStringExtra("phone"));
        website.setText(intent.getStringExtra("website"));

    }
}
