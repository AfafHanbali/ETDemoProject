package com.example.ettdemoproject.UI;

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
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;

import com.example.ettdemoproject.DataModel.User;
import com.example.ettdemoproject.MessageEvent;
import com.example.ettdemoproject.R;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.Locale;


/**
 * @author : Afaf Hanbali
 * Created on 2020-Oct-5
 */

public class UserInformationActivity extends AppCompatActivity {

    public static final String APP_TITLE = "UserData";
    public static final String EMAIL_CHOOSER_TITLE = "Choose your Sending Application:";
    public static final String USER_KEY = "userItem";
    public static final String POSITION_KEY = "position";
    public static final String DIRECTIONS_MAPPER = "Directions Here";
    public static final String WEBSITE_PROTOCOL = "http://";
    public static final String MAP_URL = "http://maps.google.com/maps?q=loc:";
    public static final String MAP_NOT_FOUND_ALERT = "Can't find location";
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
    private Button userFavButton;

    private String url;

    public static void startScreen(Activity srcActivity, User userItem, int position) {
        Intent intent = new Intent(srcActivity, UserInformationActivity.class);
        intent.putExtra(USER_KEY, userItem);
        intent.putExtra(POSITION_KEY, Integer.toString(position));
        srcActivity.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_information);
        readIntent();
        bindViews();
        setupListeners();
        setFieldsText();
        setFieldsResources();
    }

    private void readIntent() {
        Intent intent = getIntent();
        if (intent != null && intent.hasExtra(USER_KEY) && intent.hasExtra(POSITION_KEY)) {
            user = (User) intent.getSerializableExtra(USER_KEY);
            position = Integer.parseInt(intent.getStringExtra(POSITION_KEY));//TODO  : do we need position here ?
        } else {
            finish();
        }
    }

    private void bindViews() {
        profileToolbar = findViewById(R.id.profileToolBar);
        setToolBarOptions(profileToolbar);
        nameTextView = findViewById(R.id.tv_userName);
        userNameTextView = findViewById(R.id.tv_userUsername);
        emailTextView = findViewById(R.id.tv_userEmail);
        addressTextView = findViewById(R.id.tv_userAddress);
        phoneTextView = findViewById(R.id.tv_userPhone);
        websiteTextView = findViewById(R.id.tv_userWebsite);
        companyTextView = findViewById(R.id.tv_userCompany);
        userFavButton = findViewById(R.id.userFavButton);
    }

    private void setupListeners() {

        addressTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO : this nested dot access will cause NPE .
                if (Double.toString(user.getAddress().getGeo().getLat()) != null && Double.toString(user.getAddress().getGeo().getLng()) != null) {
                    url = String.format(Locale.ENGLISH, MAP_URL + "%f,%f", user.getAddress().getGeo().getLat(), user.getAddress().getGeo().getLng());
                    startImplicitIntent(url);
                } else {
                    Toast toast = Toast.makeText(getApplicationContext(), MAP_NOT_FOUND_ALERT, Toast.LENGTH_LONG);
                    toast.show();
                }
            }
        });

        websiteTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                url = WEBSITE_PROTOCOL + user.getWebsite();
                startImplicitIntent(url);
            }
        });

        emailTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent implicitIntent = new Intent(Intent.ACTION_SEND);
                implicitIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{user.getEmail()});
                implicitIntent.setType("isFav/rfc822");
                startActivity(Intent.createChooser(implicitIntent, EMAIL_CHOOSER_TITLE));
            }
        });

        userFavButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (user.isFavorite()) {
                    userFavButton.setBackgroundResource(R.drawable.ic_star_border_black_24dp);
                    //TODO : simply negate the current user isFavorite and post an event with the whole object .
                    sendMsg(false, position);
                } else {
                    userFavButton.setBackgroundResource(R.drawable.ic_star_black_24dp);
                    sendMsg(true, position);
                }
            }
        });

    }

    private void sendMsg(boolean isFav, int position) {
        EventBus.getDefault().postSticky(new MessageEvent(isFav, position));
    }

    private void setToolBarOptions(Toolbar toolbar) {

        toolbar.setTitle(APP_TITLE);
        toolbar.setTitleTextColor(Color.WHITE);
        toolbar.setNavigationIcon(R.drawable.back_arrow);

        // TODO : dont override this behaviour , instead assign using setSupportActionBar(); && call getSupportActionBar().setHomeButtonEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    private void startImplicitIntent(String Url) {
        Intent implicitIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(Url));
        startActivity(implicitIntent);
    }

    private void setFieldsText() {

        SpannableString direction = new SpannableString(DIRECTIONS_MAPPER);
        direction.setSpan(new UnderlineSpan(), 0, direction.length(), 0);

        nameTextView.setText(user.getName());
        userNameTextView.setText(user.getUsername());
        phoneTextView.setText(user.getPhone());
        emailTextView.setPaintFlags(emailTextView.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        emailTextView.setText(user.getEmail());
        String fullAddress = getString(R.string.fullAddress, user.getAddress().getStreet(), user.getAddress().getSuite(), user.getAddress().getCity(), user.getAddress().getZipcode());//TODO : NPE ALERT
        addressTextView.setText(fullAddress);
        addressTextView.append(direction);
        websiteTextView.setPaintFlags(emailTextView.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        websiteTextView.setText(user.getWebsite());
        String companyDetails = getString(R.string.companyDetails, user.getCompany().getCompanyName(), user.getCompany().getCatchPhrase(), user.getCompany().getBs());//TODO : NPE ALERT
        companyTextView.setText(companyDetails);

    }

    private void setFieldsResources() {
        if (!user.isFavorite()) {
            userFavButton.setBackgroundResource(R.drawable.ic_star_border_black_24dp);
        } else {
            userFavButton.setBackgroundResource(R.drawable.ic_star_black_24dp);
        }
    }

}
