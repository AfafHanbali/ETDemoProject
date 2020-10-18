package com.example.ettdemoproject.DataModel;

import androidx.annotation.Nullable;
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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;

import com.example.ettdemoproject.R;

import java.util.Locale;

/**
 * @author : Afaf Hanbali
 * Created on 2020-Oct-5
 */

//TODO : doesnt belong to data model package , move to UI package
public class UserInformationActivity extends AppCompatActivity {

    private TextView nameTextView;
    private TextView userNameTextView;
    private TextView emailTextView;
    private TextView phoneTextView;
    private TextView websiteTextView;
    private TextView addressTextView;
    private TextView companyTextView;
    private User user;
    private int position;
    private Toolbar profileToolbar;
    private ImageView userStar;

    //TODO : pls follow convention of having static members at top then class members below grouped by their category ( views , data ..etc )
    public static final String EMAIL_CHOOSER_TITLE = "Choose your Sending Application:";
    public static final String USER_KEY = "userItem";
    public static final String POSITION_KEY = "position";
    public static final int REQUEST_CODE = 11; // TODO : request code is for the sending activity to store/hold not the receiving one .
    private static final String BOOLEAN_KEY = "isFavorite";
    private String url;
    private Intent implicitIntent; // TODO : no need to have it stored here .

    public static void startScreen(Activity srcActivity, User userItem, int position) {
        Intent intent = new Intent(srcActivity, UserInformationActivity.class);
        intent.putExtra(USER_KEY, userItem);
        intent.putExtra(POSITION_KEY, Integer.toString(position));
        srcActivity.startActivityForResult(intent, REQUEST_CODE);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_information);
        // TODO : pls split into methods ( bindViews() , setupListeners() ..etc )
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
        userStar = findViewById(R.id.iv_itemStar);

        addressTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO : convention const string .
                //TODO : what if you have null latt/long ?
                url = String.format(Locale.ENGLISH, "http://maps.google.com/maps?q=loc:%f,%f", user.getLatt(), user.getLng());
                startImplicitIntent(url);
            }
        });

        websiteTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO : convention const string .
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

        userStar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (user.isFavorite()) {
                    userStar.setImageResource(R.drawable.ic_star_border_24dp);
                    user.setFavorite(false);
                } else {
                    userStar.setImageResource(R.drawable.ic_star_24dp);
                    user.setFavorite(true);
                }
            }
        });

        setFieldsText();
        setFieldsResources();
    }


    private void readIntent() {
        Intent intent = getIntent();
        if (intent != null && intent.hasExtra(USER_KEY)) {
            user = (User) intent.getSerializableExtra(USER_KEY);
            //TODO : this might produce null pointer since u dont check for position key .
            position = Integer.parseInt(intent.getStringExtra(POSITION_KEY));
        } else {
            finish();
            //TODO : exit will terminate the whole app process . just use finish
            System.exit(0);
        }
    }

    private void setToolBarOptions(Toolbar toolbar) {

        toolbar.setTitle("UserData");//TODO :  const convention
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

        SpannableString direction = new SpannableString("Directions Here"); //TODO : const string , convention
        direction.setSpan(new UnderlineSpan(), 0, direction.length(), 0);

        nameTextView.setText(user.getName());
        userNameTextView.setText(user.getUsername());
        phoneTextView.setText(user.getPhone());
        emailTextView.setPaintFlags(emailTextView.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        emailTextView.setText(user.getEmail());
        addressTextView.setText(user.getStreet() + "'" + user.getSuite() + "\n" + user.getCity() + "'" + user.getZipCode() + "\n");//TODO : cant concat on set
        addressTextView.append(direction);
        websiteTextView.setPaintFlags(emailTextView.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        websiteTextView.setText(user.getWebsite());
        companyTextView.setText(user.getCompanyName() + "\n" + user.getCatchPhrase() + "\n" + user.getBs());//TODO : cant concat on set .

    }

    private void setFieldsResources() {
        if (!user.isFavorite()) {
            userStar.setImageResource(R.drawable.ic_star_border_24dp);
        } else {
            userStar.setImageResource(R.drawable.ic_star_24dp);
        }
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent();
        intent.putExtra(BOOLEAN_KEY, Boolean.toString(user.isFavorite()));
        intent.putExtra(POSITION_KEY, Integer.toString(position));
        setResult(RESULT_OK, intent);
        finish();
    }
}
