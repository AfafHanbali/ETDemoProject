package com.example.ettdemoproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;

/**
 * @author : Afaf Hanbali
 * Created on 2020-Oct-5
 */

public class UserInformationActivity extends AppCompatActivity {

    private TextView userId;
    private TextView name;
    private TextView userName;
    private TextView email;
    private TextView phone;
    private TextView website;
    private TextView address;
    private TextView company;
    private User user;
    private Toolbar toolbar;
    public static Intent startUserInfoActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_information);
        toolbar = findViewById(R.id.profileToolBar);
        setToolBarOptions(toolbar);

        name = findViewById(R.id.tv_name);
        userName = findViewById(R.id.tv_username2);
        email = findViewById(R.id.tv_email2);
        address = findViewById(R.id.tv_address);
        phone = findViewById(R.id.tv_phone);
        website = findViewById(R.id.tv_website);
        company = findViewById(R.id.tv_company);


        setFieldsText();

    }

    private void setToolBarOptions(Toolbar toolbar) {
        toolbar.setTitle("UserData");
        toolbar.setTitleTextColor(Color.WHITE);
        toolbar.setNavigationIcon(R.drawable.back_arrow);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    public void setFieldsText() {

        user = (User) startUserInfoActivity.getSerializableExtra("userListObject");
        name.setText(user.getName());
        userName.setText(user.getUsername());
        phone.setText(user.getPhone());
        email.setText(user.getEmail());
        address.setText(user.getStreet() + "'" + user.getSuite() + "\n" + user.getCity() + "'" + user.getZipCode());
        website.setText(user.getWebsite());
        company.setText(user.getCompanyName() + "\n" + user.getCatchPhrase() + "\n" + user.getBs());

    }


}
