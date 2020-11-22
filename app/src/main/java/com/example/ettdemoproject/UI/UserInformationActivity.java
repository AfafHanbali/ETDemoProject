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
import androidx.core.content.ContextCompat;

import com.example.ettdemoproject.DataModel.User;
import com.example.ettdemoproject.Events.FavClickEvent;
import com.example.ettdemoproject.R;

import org.greenrobot.eventbus.EventBus;

import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.branch.indexing.BranchUniversalObject;
import io.branch.referral.Branch;
import io.branch.referral.BranchError;
import io.branch.referral.SharingHelper;
import io.branch.referral.util.LinkProperties;
import io.branch.referral.util.ShareSheetStyle;


/**
 * @author : Afaf Hanbali
 * Created on 2020-Oct-5
 */

public class UserInformationActivity extends AppCompatActivity {

    public static final String APP_TITLE = "UserData";
    public static final String EMAIL_CHOOSER_TITLE = "Open with";
    public static final String SHARE_CHOOSER_TITLE = "Share with";
    public static final String USER_KEY = "userItem";
    public static final String DIRECTIONS_MAPPER = "Directions Here";
    public static final String WEBSITE_PROTOCOL = "http://";
    public static final String MAP_URL = "http://maps.google.com/maps?q=loc:";
    public static final String MAP_NOT_FOUND_ALERT = "Can't find location";
    public static final String WEBSITE_NOT_FOUND = "Can't open the website";
    public static final String EMAIL_NOT_FOUND = "Can't reach the email";
    public static final String ADDRESS_NOT_FOUND = "User's address isn't specified.";
    public static final String COMPANY_NOT_FOUND = "User's company isn't specified.";
    public static final String TYPE = "user";
    public static final String SUBJECT = "User Details";

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
    @BindView(R.id.userShareButton)
    Button shareButton;

    private User user;
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

    private void setupListeners() {

        addressTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                User.Address address = user.getAddress();
                if (address != null) {
                    User.Address.Geo geo = address.getGeo();

                    if (geo != null) {
                        double lat = geo.getLat();
                        double lng = geo.getLng();

                        if (lat != -1 && lng != -1) {
                            url = String.format(Locale.ENGLISH, MAP_URL + "%f,%f", lat, lng);
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
                if (website != user.UNSPECIFIED) {
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
                if (email != user.UNSPECIFIED) {
                    Intent implicitIntent = new Intent(Intent.ACTION_SEND);
                    implicitIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{email});
                    implicitIntent.setType("message/rfc822");
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

        shareButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id = Integer.toString(user.getId());
                String message = getString(R.string.userShareMsg, user.getName());
                BranchUniversalObject buo = getBranchUniversalObject(id);
                LinkProperties lp = getLinkProperties();
                ShareSheetStyle ss = getShareSheetStyle(message);

                buo.showShareSheet(UserInformationActivity.this, lp, ss, new Branch.BranchLinkShareListener() {
                    @Override
                    public void onShareLinkDialogLaunched() {
                    }

                    @Override
                    public void onShareLinkDialogDismissed() {
                    }

                    @Override
                    public void onLinkShareResponse(String sharedLink, String sharedChannel, BranchError error) {
                    }

                    @Override
                    public void onChannelSelected(String channelName) {
                    }
                });

            }
        });

    }

    private BranchUniversalObject getBranchUniversalObject(String id){
        return new BranchUniversalObject()
                .setCanonicalIdentifier(id)
                .setTitle(user.getName())
                .setContentDescription(TYPE)
                .setContentIndexingMode(BranchUniversalObject.CONTENT_INDEX_MODE.PUBLIC)
                .setLocalIndexMode(BranchUniversalObject.CONTENT_INDEX_MODE.PUBLIC);
    }

    private LinkProperties getLinkProperties(){
        return  new LinkProperties()
                .setChannel("facebook")
                .setFeature("sharing")
                .setCampaign("content 123 launch")
                .setStage("new user");
    }

    private ShareSheetStyle getShareSheetStyle(String message) {
        return new ShareSheetStyle(UserInformationActivity.this, SUBJECT, message)
                .setCopyUrlStyle(ContextCompat.getDrawable(UserInformationActivity.this, android.R.drawable.ic_menu_send), "Copy", "Added to clipboard")
                .setMoreOptionStyle(ContextCompat.getDrawable(UserInformationActivity.this, android.R.drawable.ic_menu_search), "Show more")
                .addPreferredSharingOption(SharingHelper.SHARE_WITH.FACEBOOK)
                .addPreferredSharingOption(SharingHelper.SHARE_WITH.GMAIL)
                .setAsFullWidthStyle(true)
                .setSharingTitle(SHARE_CHOOSER_TITLE);
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

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }


    private void setFieldsText() {

        String suite;
        String street;
        String city;
        String zipCode;
        String companyName;
        String catchPhrase;
        String bs;
        User.Address address = user.getAddress();
        User.Company company = user.getCompany();

        SpannableString direction = new SpannableString(DIRECTIONS_MAPPER);
        direction.setSpan(new UnderlineSpan(), 0, direction.length(), 0);

        nameTextView.setText(user.getName());
        userNameTextView.setText(user.getUsername());
        phoneTextView.setText(user.getPhone());
        emailTextView.setPaintFlags(emailTextView.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        emailTextView.setText(user.getEmail());
        websiteTextView.setPaintFlags(emailTextView.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        websiteTextView.setText(user.getWebsite());

        if (address != null) {
            street = address.getStreet();
            suite = address.getSuite();
            city = address.getCity();
            zipCode = address.getZipcode();
            if (suite != user.UNSPECIFIED && city != user.UNSPECIFIED && street != user.UNSPECIFIED && zipCode != user.UNSPECIFIED) {
                String fullAddress = getString(R.string.fullAddress, street, suite, city, zipCode);
                addressTextView.setText(fullAddress);
                addressTextView.append(direction);
            } else {
                addressTextView.setText(ADDRESS_NOT_FOUND);
            }
        } else {
            addressTextView.setText(ADDRESS_NOT_FOUND);
        }
        if (company != null) {
            companyName = company.getCompanyName();
            catchPhrase = company.getCatchPhrase();
            bs = company.getBs();
            if (companyName != user.UNSPECIFIED && catchPhrase != user.UNSPECIFIED && bs != user.UNSPECIFIED) {
                String companyDetails = getString(R.string.companyDetails, companyName, catchPhrase, bs);
                companyTextView.setText(companyDetails);
            } else {
                companyTextView.setText(COMPANY_NOT_FOUND);
            }
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
