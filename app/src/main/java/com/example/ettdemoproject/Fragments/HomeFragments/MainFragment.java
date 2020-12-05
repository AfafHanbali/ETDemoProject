package com.example.ettdemoproject.Fragments.HomeFragments;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.example.ettdemoproject.Fragments.HomeFragments.Albums.AlbumsFragment;
import com.example.ettdemoproject.Fragments.HomeFragments.Posts.PostsFragment;
import com.example.ettdemoproject.Fragments.HomeFragments.Users.UsersFragment;
import com.example.ettdemoproject.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;


/**
 * @author : Afaf Hanbali
 * Created on 2020-Oct-5
 */
public class MainFragment extends Fragment {

    private static final String TYPE_POST = "post";
    private static final String TYPE_USER = "user";
    private static final String TYPE_ALBUM = "album";
    private static final int NUM_PAGES = 3;

    @BindView(R.id.bottom_navigation)
    BottomNavigationView bottomNavigationMenuView;
    @BindView(R.id.viewPager)
    ViewPager2 viewPager;

    private FragmentStateAdapter pagerAdapter;
    private UsersFragment usersFragment = new UsersFragment();
    private AlbumsFragment albumsFragment = new AlbumsFragment();
    private PostsFragment postsFragment = new PostsFragment();

    private Unbinder unbinder;


    public MainFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        unbinder = ButterKnife.bind(this, view);

        pagerAdapter = new ScreenSlidePagerAdapter(getActivity());
        viewPager.setAdapter(pagerAdapter);
        bottomNavigationMenuView.setSelectedItemId(R.id.page_users);
        viewPager.setCurrentItem(1);


        viewPager.setUserInputEnabled(false);

        /*
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
        });*/


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


        return view;

    }



    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
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
