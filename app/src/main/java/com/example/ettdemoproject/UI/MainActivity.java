package com.example.ettdemoproject.UI;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import androidx.appcompat.widget.Toolbar;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;
import com.example.ettdemoproject.MainFragments.Albums.AlbumsFragment;
import com.example.ettdemoproject.MainFragments.Posts.PostsFragment;
import com.example.ettdemoproject.MainFragments.Users.UsersFragment;
import com.example.ettdemoproject.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;


import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.branch.referral.Branch;
import io.branch.referral.BranchError;


/**
 * @author : Afaf Hanbali
 * Created on 2020-Oct-5
 */

public class MainActivity extends FragmentActivity {

    private static final String TAG = "BRANCH SDK";
    public static final String APP_TITLE = "ETDemo Project";
    private static final String TYPE_POST = "post";
    private static final String TYPE_USER = "user";
    private static final String TYPE_ALBUM = "album";
    private static final int NUM_PAGES = 3;


    @BindView(R.id.mainToolBar)
    Toolbar mainToolbar;
    @BindView(R.id.bottom_navigation)
    BottomNavigationView bottomNavigationMenuView;
    @BindView(R.id.viewPager)
    ViewPager2 viewPager;
    private FragmentStateAdapter pagerAdapter;
    UsersFragment usersFragment = new UsersFragment();
    AlbumsFragment albumsFragment = new AlbumsFragment();
    PostsFragment postsFragment = new PostsFragment();

    private Branch.BranchReferralInitListener branchReferralInitListener;
    private int id = -1;
    private String type = null;
    private int rowPosition;
    private boolean isLinkClicked = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setToolBarOptions(mainToolbar);

        pagerAdapter = new ScreenSlidePagerAdapter(this);
        viewPager.setAdapter(pagerAdapter);
        bottomNavigationMenuView.setSelectedItemId(R.id.page_users);
        viewPager.setCurrentItem(1);

        branchReferralInitListener = new Branch.BranchReferralInitListener() {
            @Override
            public void onInitFinished(JSONObject linkProperties, BranchError error) {
                if (error == null) {
                    try {
                        id = linkProperties.getInt("$canonical_identifier");
                        type = linkProperties.getString("$og_description");
                        if (type.equals(TYPE_ALBUM)) {
                            viewPager.setCurrentItem(0);
                            bottomNavigationMenuView.setSelectedItemId(R.id.page_albums);
                            isLinkClicked = true;
                            rowPosition = id - 1;
                            pagerAdapter.createFragment(0);
                            //albumsFragment.setPosition(id - 1);
                        } else if (type.equals(TYPE_USER)) {
                            viewPager.setCurrentItem(1);
                            bottomNavigationMenuView.setSelectedItemId(R.id.page_users);
                            isLinkClicked = true;
                            rowPosition = id - 1;
                            //  usersFragment.setPosition(id - 1);
                        } else if (type.equals(TYPE_POST)) {
                            viewPager.setCurrentItem(2);
                            bottomNavigationMenuView.setSelectedItemId(R.id.page_posts);
                            isLinkClicked = true;
                            rowPosition = id - 1;
                            //postsFragment.setPosition(id - 1);
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else {
                    Log.i(TAG, error.getMessage());
                }
            }
        };

        viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                switch (position) {
                    case 0:
                        bottomNavigationMenuView.setSelectedItemId(R.id.page_albums);
                        break;
                    case 1:
                        bottomNavigationMenuView.setSelectedItemId(R.id.page_users);
                        break;
                    case 2:
                        bottomNavigationMenuView.setSelectedItemId(R.id.page_posts);
                        break;
                }
            }
        });

        bottomNavigationMenuView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.page_albums:
                        viewPager.setCurrentItem(0);
                        break;
                    case R.id.page_users:
                        viewPager.setCurrentItem(1);
                        break;

                    case R.id.page_posts:
                        viewPager.setCurrentItem(2);
                        break;
                }
                return true;
            }
        });
    }

    @Override
    public void onBackPressed() {
        if (viewPager.getCurrentItem() == 1) {
            super.onBackPressed();
        } else if (viewPager.getCurrentItem() == 0) {
            viewPager.setCurrentItem(viewPager.getCurrentItem() + 1);
        } else {
            viewPager.setCurrentItem(viewPager.getCurrentItem() - 1);
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        Branch.sessionBuilder(this).withCallback(branchReferralInitListener).withData(getIntent() != null ? getIntent().getData() : null).init();

    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        // if activity is in foreground (or in backstack but partially visible) launching the same
        // activity will skip onStart, handle this case with reInitSession
        Branch.sessionBuilder(this).withCallback(branchReferralInitListener).reInit();
    }

    private void setToolBarOptions(Toolbar toolbar) {
        toolbar.setTitle(APP_TITLE);
        toolbar.setTitleTextColor(Color.WHITE);
    }

    private class ScreenSlidePagerAdapter extends FragmentStateAdapter {
        public ScreenSlidePagerAdapter(FragmentActivity fa) {
            super(fa);
        }

        @Override
        public Fragment createFragment(int position) {
            switch (position) {
                case 0:
                   /* if (isLinkClicked) {
                        albumsFragment.setPosition(rowPosition);
                    }*/
                    return albumsFragment;
                case 1:
                    //usersFragment.setPosition(rowPosition);
                    return usersFragment;
                case 2:
                    //postsFragment.setPosition(rowPosition);
                    return postsFragment;
            }
            return null;
        }

        @Override
        public int getItemCount() {
            return NUM_PAGES;
        }

    }

}

