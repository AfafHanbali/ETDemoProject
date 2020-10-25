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
import com.example.ettdemoproject.networking.FavClickEvent;
import com.example.ettdemoproject.R;

import org.greenrobot.eventbus.EventBus;

import java.util.Locale;


/**
 * @author : Afaf Hanbali
 * Created on 2020-Oct-5
 */

public class UserInformationActivity extends AppCompatActivity {

    public static final String APP_TITLE = "UserData";
    public static final String EMAIL_CHOOSER_TITLE = "Choose your Sending Application:";
    public static final String USER_KEY = "userItem";
    public static final String DIRECTIONS_MAPPER = "Directions Here";
    public static final String WEBSITE_PROTOCOL = "http://";
    public static final String MAP_URL = "http://maps.google.com/maps?q=loc:";
    public static final String MAP_NOT_FOUND_ALERT = "Can't find location";
    private static final String WEBSITE_NOT_FOUND = "Can't open the website";
    private static final String EMAIL_NOT_FOUND = "Can't reach the email";
    private static final String ADDRESS_NOT_FOUND = "User's address isn't specified.";
    private static final String COMPANY_NOT_FOUND = "User's company isn't specified.";
    private Toolbar profileToolbar;
    private TextView nameTextView;
    private TextView userNameTextView;
    private TextView emailTextView;
    private TextView phoneTextView;
    private TextView websiteTextView;
    private TextView addressTextView;
    private TextView companyTextView;
    private User user;
    private Button userFavButton;

    private String url;

    public static void startScreen(Activity srcActivity, User userItem) {
        Intent intent = new Intent(srcActivity, UserInformationActivity.class);
        intent.putExtra(USER_KEY, userItem);
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
        if (intent != null && intent.hasExtra(USER_KEY)) {
            user = (User) intent.getSerializableExtra(USER_KEY);
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

                if (user.getAddress() != null) {
                    User.Address.Geo geo = user.getAddress().getGeo();
                    if (geo != null) {
                        String latitude = Double.toString(user.getAddress().getGeo().getLat());
                        String longitude = Double.toString(user.getAddress().getGeo().getLng());
                        if (latitude != null && longitude != null) {
                            url = String.format(Locale.ENGLISH, MAP_URL + "%f,%f", user.getAddress().getGeo().getLat(), user.getAddress().getGeo().getLng());
                            startImplicitIntent(url);
                        } else {
                            showToast(MAP_NOT_FOUND_ALERT);
                        }
                    } else {
                        showToast(MAP_NOT_FOUND_ALERT);

                    }
                } else {
                    showToast(MAP_NOT_FOUND_ALERT);
                }
            }
        });

        websiteTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String website = user.getWebsite();
                if (website != null) {
                    url = WEBSITE_PROTOCOL + website;
                    startImplicitIntent(url);
                } else {
                    showToast(WEBSITE_NOT_FOUND);
                }
            }
        });

        emailTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = user.getEmail();
                if (email != null) {
                    Intent implicitIntent = new Intent(Intent.ACTION_SEND);
                    implicitIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{email});
                    implicitIntent.setType("isFav/rfc822");
                    startActivity(Intent.createChooser(implicitIntent, EMAIL_CHOOSER_TITLE));
                } else {
                    showToast(EMAIL_NOT_FOUND);
                }
            }
        });

        userFavButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (user.isFavorite()) {
                    userFavButton.setBackgroundResource(R.drawable.ic_star_border_black_24dp);
                    user.setFavorite(false);
                    favoriteUser(user);
                } else {
                    userFavButton.setBackgroundResource(R.drawable.ic_star_black_24dp);
                    user.setFavorite(true);
                    favoriteUser(user);
                }
            }
        });

    }

    private void showToast(String msg) {
        Toast toast = Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG);
        toast.show();
    }

    private void favoriteUser(User user) {
        EventBus.getDefault().postSticky(new FavClickEvent(user));

    }


    private void startImplicitIntent(String Url) {
        Intent implicitIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(Url));
        startActivity(implicitIntent);
    }

    private void setToolBarOptions(Toolbar toolbar) {

        toolbar.setTitle(APP_TITLE);
        toolbar.setTitleTextColor(Color.WHITE);
        toolbar.setNavigationIcon(R.drawable.back_arrow);

        // TODO : dont override this behaviour , instead assign using setSupportActionBar(); && call getSupportActionBar().setHomeButtonEnabled(true);
        // I tried it, it keeps refreshing the main activity as if no data was stored before

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    private void setFieldsText() {

        SpannableString direction = new SpannableString(DIRECTIONS_MAPPER);
        direction.setSpan(new UnderlineSpan(), 0, direction.length(), 0);

        nameTextView.setText(user.getName());
        userNameTextView.setText(user.getUsername());
        phoneTextView.setText(user.getPhone());
        emailTextView.setPaintFlags(emailTextView.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        emailTextView.setText(user.getEmail());
        if (user.getAddress() != null) {
            String street = user.getAddress().getStreet();
            String suite = user.getAddress().getSuite();
            String city = user.getAddress().getCity();
            String zipCode = user.getAddress().getZipcode();
            String fullAddress = getString(R.string.fullAddress, street, suite, city, zipCode);
            addressTextView.setText(fullAddress);
            addressTextView.append(direction);
        } else {
            addressTextView.setText(ADDRESS_NOT_FOUND);
        }
        websiteTextView.setPaintFlags(emailTextView.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        websiteTextView.setText(user.getWebsite());
        if (user.getCompany() != null) {
            String companyName = user.getCompany().getCompanyName();
            String catchPhrase = user.getCompany().getCatchPhrase();
            String bs = user.getCompany().getBs();
            String companyDetails = getString(R.string.companyDetails, companyName, catchPhrase, bs);
            companyTextView.setText(companyDetails);
        } else {
            companyTextView.setText(COMPANY_NOT_FOUND);

        }

    }

    private void setFieldsResources() {
        if (!user.isFavorite()) {
            userFavButton.setBackgroundResource(R.drawable.ic_star_border_black_24dp);
        } else {
            userFavButton.setBackgroundResource(R.drawable.ic_star_black_24dp);
        }
    }

}
