package com.example.ettdemoproject;

import androidx.appcompat.app.AppCompatActivity;

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

import java.util.Locale;

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
    public static final String emailChooserTitle = "Choose the Application you want to send through:";
    private Intent imlicitIntent;

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

        address.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String uri = String.format(Locale.ENGLISH, "http://maps.google.com/maps?q=loc:%f,%f", user.getLatt(), user.getLng());
                imlicitIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
                startActivity(imlicitIntent);
            }
        });

        website.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "http://" + user.getWebsite();
                imlicitIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                startActivity(imlicitIntent);
            }
        });

        email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imlicitIntent = new Intent(Intent.ACTION_SEND);
                imlicitIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{user.getEmail()});
                imlicitIntent.setType("message/rfc822");
                startActivity(Intent.createChooser(imlicitIntent, emailChooserTitle));
            }
        });

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

        SpannableString direction = new SpannableString("Directions Here");
        direction.setSpan(new UnderlineSpan(), 0, direction.length(), 0);

        user = (User) startUserInfoActivity.getSerializableExtra("userListObject");
        name.setText(user.getName());
        userName.setText(user.getUsername());
        phone.setText(user.getPhone());
        email.setPaintFlags(email.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        email.setText(user.getEmail());
        address.setText(user.getStreet() + "'" + user.getSuite() + "\n" + user.getCity() + "'" + user.getZipCode()+"\n");
        address.append(direction);
        website.setPaintFlags(email.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        website.setText(user.getWebsite());
        company.setText(user.getCompanyName() + "\n" + user.getCatchPhrase() + "\n" + user.getBs());

    }


}
