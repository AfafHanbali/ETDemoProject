package com.example.ettdemoproject.UI;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.appcompat.widget.Toolbar;

import com.example.ettdemoproject.MainFragments.Albums.AlbumsFragment;
import com.example.ettdemoproject.MainFragments.Posts.PostsFragment;
import com.example.ettdemoproject.MainFragments.Users.UsersFragment;
import com.example.ettdemoproject.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * @author : Afaf Hanbali
 * Created on 2020-Oct-5
 */

public class ApplicationListActivity extends AppCompatActivity {

    public static final String APP_TITLE = "ETDemo Project";
    private static final String TYPE_POST = "post";
    private static final String TYPE_USER = "user";
    private static final String TYPE_ALBUM = "album";

    @BindView(R.id.mainToolBar)
    Toolbar mainToolbar;
    @BindView(R.id.bottom_navigation)
    BottomNavigationView bottomNavigationMenuView;
    UsersFragment usersFragment = new UsersFragment();
    AlbumsFragment albumsFragment = new AlbumsFragment();
    PostsFragment postsFragment = new PostsFragment();
    Uri data;
    int id;
    String type;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setToolBarOptions(mainToolbar);

        data = getIntent().getData();


        if (savedInstanceState == null && data == null) {
            setDefaultFragment(usersFragment);
            bottomNavigationMenuView.setSelectedItemId(R.id.page_users);
        } else if (data != null) {
            List<String> paramList = data.getPathSegments();
            type = paramList.get(0);
            id = Integer.parseInt(paramList.get(1));

            if (type.equals(TYPE_POST)) {
                setDefaultFragment(postsFragment);
                bottomNavigationMenuView.setSelectedItemId(R.id.page_posts);
                postsFragment.setPosition(id - 1);
            } else if (type.equals(TYPE_USER)) {
                setDefaultFragment(usersFragment);
                bottomNavigationMenuView.setSelectedItemId(R.id.page_users);
                usersFragment.setPosition(id - 1);
            } else if (type.equals(TYPE_ALBUM)) {
                setDefaultFragment(albumsFragment);
                bottomNavigationMenuView.setSelectedItemId(R.id.page_albums);
                albumsFragment.setPosition(id - 1);
            }
        }


        bottomNavigationMenuView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {

            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment selectedFragment = null;
                switch (item.getItemId()) {
                    case R.id.page_albums:
                        selectedFragment = albumsFragment;
                        break;
                    case R.id.page_users:
                        selectedFragment = usersFragment;
                        break;

                    case R.id.page_posts:
                        selectedFragment = postsFragment;
                        break;
                }

                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, selectedFragment).commit();
                return true;
            }
        });
    }

    private void setDefaultFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment).commit();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, fragment);
        transaction.commit();

    }

    private void setToolBarOptions(Toolbar toolbar) {
        toolbar.setTitle(APP_TITLE);
        toolbar.setTitleTextColor(Color.WHITE);
    }

}
