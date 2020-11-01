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
import com.example.ettdemoproject.Presenters.UserInformationActivityPresenter;
import com.example.ettdemoproject.networking.FavClickEvent;
import com.example.ettdemoproject.R;

import org.greenrobot.eventbus.EventBus;

import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * @author : Afaf Hanbali
 * Created on 2020-Oct-5
 */

public class UserInformationActivity extends AppCompatActivity implements UserInformationActivityPresenter.UserInfoView {

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

    private UserInformationActivityPresenter presenter;
    @BindView(R.id.profileToolBar)
    Toolbar profileToolbar;
    @BindView(R.id.tv_userName)
    TextView nameTextView;
    @BindView(R.id.tv_userUsername)
    TextView userNameTextView;
    @BindView(R.id.tv_userEmail)
    TextView emailTextView;
    @BindView(R.id.tv_userPhone)
    TextView phoneTextView;
    @BindView(R.id.tv_userWebsite)
    TextView websiteTextView;
    @BindView(R.id.tv_userAddress)
    TextView addressTextView;
    @BindView(R.id.tv_userCompany)
    TextView companyTextView;
    @BindView(R.id.userFavButton)
    Button userFavButton;

    private User user;
    private String name;
    private String userName;
    private String phone;
    private String email;
    private String website;
    private String suite;
    private String street;
    private String city;
    private String zipCode;
    private String companyName;
    private String catchPhrase;
    private String bs;
    private String latitude;
    private String longitude;

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
        ButterKnife.bind(this);
        readIntent();
        setToolBarOptions(profileToolbar);
        setupListeners();
        presenter.getInfo();
        setFieldsResources();
    }

    private void readIntent() {
        Intent intent = getIntent();
        if (intent != null && intent.hasExtra(USER_KEY)) {
            user = (User) intent.getSerializableExtra(USER_KEY);
            presenter = new UserInformationActivityPresenter(this, user);
        } else {
            finish();
        }
    }

    private void setupListeners() {

        addressTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (latitude != presenter.UNSPECIDIED && longitude != presenter.UNSPECIDIED) {
                    url = String.format(Locale.ENGLISH, MAP_URL + "%f,%f", latitude, longitude);
                    startImplicitIntent(url);
                } else {
                    showToast(MAP_NOT_FOUND_ALERT);
                }
            }

        });

        websiteTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
                if (presenter.checkFavorite()) {
                    userFavButton.setBackgroundResource(R.drawable.ic_star_border_black_24dp);
                    presenter.updateFavorite(false);
                    favoriteUser(user);
                } else {
                    userFavButton.setBackgroundResource(R.drawable.ic_star_black_24dp);
                    presenter.updateFavorite(true);
                    favoriteUser(user);
                }
            }
        });

    }

    @Override
    public void showToast(String msg) {
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

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }


    @Override
    public void assignValues(String name, String userName, String phone, String email, String street, String suite, String city, String zipCode, String companyName, String catchPhrase, String bs, String website, String lat, String lng) {
        this.name = name;
        this.userName = userName;
        this.phone = phone;
        this.website = website;
        this.suite = suite;
        this.city = city;
        this.street = street;
        this.zipCode = zipCode;
        this.companyName = companyName;
        this.catchPhrase = catchPhrase;
        this.bs = bs;
        this.latitude = lat;
        this.latitude = lng;
        setFieldsText(name, userName, phone, website, street, suite, city, zipCode, companyName, catchPhrase, bs, email);


    }

    private void setFieldsText(String name, String userName, String phone, String website, String street, String suite, String city, String zipCode, String companyName, String catchPhrase, String bs, String email) {

        SpannableString direction = new SpannableString(DIRECTIONS_MAPPER);
        direction.setSpan(new UnderlineSpan(), 0, direction.length(), 0);

        nameTextView.setText(name);
        userNameTextView.setText(userName);
        phoneTextView.setText(phone);
        emailTextView.setPaintFlags(emailTextView.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        emailTextView.setText(email);
        if (suite != presenter.UNSPECIDIED && city != presenter.UNSPECIDIED && street != presenter.UNSPECIDIED && zipCode != presenter.UNSPECIDIED) {
            String fullAddress = getString(R.string.fullAddress, street, suite, city, zipCode);
            addressTextView.setText(fullAddress);
            addressTextView.append(direction);
        } else {
            addressTextView.setText(ADDRESS_NOT_FOUND);
        }
        websiteTextView.setPaintFlags(emailTextView.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        websiteTextView.setText(website);
        if (companyName != presenter.UNSPECIDIED && catchPhrase != presenter.UNSPECIDIED && bs != presenter.UNSPECIDIED) {
            String companyDetails = getString(R.string.companyDetails, companyName, catchPhrase, bs);
            companyTextView.setText(companyDetails);
        } else {
            companyTextView.setText(COMPANY_NOT_FOUND);
        }


    }

    private void setFieldsResources() {
        if (!presenter.checkFavorite()) {
            userFavButton.setBackgroundResource(R.drawable.ic_star_border_black_24dp);
        } else {
            userFavButton.setBackgroundResource(R.drawable.ic_star_black_24dp);
        }
    }

}
