package com.example.ettdemoproject.Fragments.HomeFragments;


import android.animation.Animator;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.ettdemoproject.Fragments.HomeFragments.Albums.AlbumsFragment;
import com.example.ettdemoproject.Fragments.HomeFragments.Posts.PostsFragment;
import com.example.ettdemoproject.Fragments.HomeFragments.Users.UsersFragment;
import com.example.ettdemoproject.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.ArrayList;

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

    @BindView(R.id.tabLayout)
    TabLayout tabLayout;
    @BindView(R.id.viewPager)
    ViewPager2 viewPager;


    private FragmentStateAdapter pagerAdapter;
    private UsersFragment usersFragment = new UsersFragment(getActivity());
    private AlbumsFragment albumsFragment = new AlbumsFragment(getActivity());
    private PostsFragment postsFragment = new PostsFragment(getActivity());

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
        viewPager.setCurrentItem(1);


        viewPager.setUserInputEnabled(false);
        new TabLayoutMediator(tabLayout, viewPager,
                (tab, position) -> tab.setText("OBJECT " + (position + 1))
        ).attach();
        tabLayout.getTabAt(0).setIcon(R.drawable.ic_album_icon)
                .setText(R.string.tab_album);
        tabLayout.getTabAt(1).setIcon(R.drawable.ic_users_icon)
                .setText(R.string.tab_user);
        tabLayout.getTabAt(2).setIcon(R.drawable.ic_posts_icon)
                .setText(R.string.tab_post);

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
