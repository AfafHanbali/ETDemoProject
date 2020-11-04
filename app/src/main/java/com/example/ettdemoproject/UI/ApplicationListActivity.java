package com.example.ettdemoproject.UI;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.graphics.Color;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.appcompat.widget.Toolbar;

import com.example.ettdemoproject.MainFragments.Albums.AlbumsFragment;
import com.example.ettdemoproject.MainFragments.Posts.PostsFragment;
import com.example.ettdemoproject.MainFragments.Users.UsersFragment;
import com.example.ettdemoproject.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * @author : Afaf Hanbali
 * Created on 2020-Oct-5
 */

public class ApplicationListActivity extends AppCompatActivity {

    public static final String APP_TITLE = "ETDemo Project";
    @BindView(R.id.mainToolBar)
    Toolbar mainToolbar;
    @BindView(R.id.bottom_navigation)
    BottomNavigationView bottomNavigationMenuView;
    UsersFragment usersFragment = new UsersFragment();
    AlbumsFragment albumsFragment = new AlbumsFragment();
    PostsFragment postsFragment = new PostsFragment();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setToolBarOptions(mainToolbar);
        if(savedInstanceState == null){
            setDefaultFragment(usersFragment);
        }

        bottomNavigationMenuView.setSelectedItemId(R.id.page_users);
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
