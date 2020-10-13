package com.example.ettdemoproject.DataModel;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;

import com.example.ettdemoproject.R;

import java.util.Locale;

/**
 * @author : Afaf Hanbali
 * Created on 2020-Oct-5
 */

public class UserInformationActivity extends AppCompatActivity {

    private TextView nameTextView;
    private TextView userNameTextView;
    private TextView emailTextView;
    private TextView phoneTextView;
    private TextView websiteTextView;
    private TextView addressTextView;
    private TextView companyTextView;
    private User user;
    private Toolbar profileToolbar;
    public static final String EMAIL_CHOOSER_TITLE = "Choose your Sending Application:";
    public static final String USER_KEY = "userItem";
    private String url;
    private Intent implicitIntent;

    public static void startScreen(Activity srcActivity, User userItem) {
        Intent intent = new Intent(srcActivity, UserInformationActivity.class);
        intent.putExtra(USER_KEY, userItem);
        srcActivity.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_information);
        profileToolbar = findViewById(R.id.profileToolBar);
        setToolBarOptions(profileToolbar);
        readIntent();
        nameTextView = findViewById(R.id.tv_name);
        userNameTextView = findViewById(R.id.tv_username2);
        emailTextView = findViewById(R.id.tv_email2);
        addressTextView = findViewById(R.id.tv_address);
        phoneTextView = findViewById(R.id.tv_phone);
        websiteTextView = findViewById(R.id.tv_website);
        companyTextView = findViewById(R.id.tv_company);

        addressTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                url = String.format(Locale.ENGLISH, "http://maps.google.com/maps?q=loc:%f,%f", user.getLatt(), user.getLng());
                startImplicitIntent(url);
            }
        });

        websiteTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                url = "http://" + user.getWebsite();
                startImplicitIntent(url);
            }
        });

        emailTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                implicitIntent = new Intent(Intent.ACTION_SEND);
                implicitIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{user.getEmail()});
                implicitIntent.setType("message/rfc822");
                startActivity(Intent.createChooser(implicitIntent, EMAIL_CHOOSER_TITLE));
            }
        });

        setFieldsText();

    }

    private void readIntent() {
        Intent intent = getIntent();
        if (intent != null && intent.hasExtra(USER_KEY)) {
            user = (User) intent.getSerializableExtra(USER_KEY);
        } else {
            finish();
            System.exit(0);
        }
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

    private void startImplicitIntent(String Url) {
        implicitIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(Url));
        startActivity(implicitIntent);
    }

    private void setFieldsText() {

        SpannableString direction = new SpannableString("Directions Here");
        direction.setSpan(new UnderlineSpan(), 0, direction.length(), 0);

        nameTextView.setText(user.getName());
        userNameTextView.setText(user.getUsername());
        phoneTextView.setText(user.getPhone());
        emailTextView.setPaintFlags(emailTextView.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        emailTextView.setText(user.getEmail());
        addressTextView.setText(user.getStreet() + "'" + user.getSuite() + "\n" + user.getCity() + "'" + user.getZipCode() + "\n");
        addressTextView.append(direction);
        websiteTextView.setPaintFlags(emailTextView.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        websiteTextView.setText(user.getWebsite());
        companyTextView.setText(user.getCompanyName() + "\n" + user.getCatchPhrase() + "\n" + user.getBs());

    }


}
