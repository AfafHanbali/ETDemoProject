package com.example.ettdemoproject.MainFragments.Posts;


import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.ettdemoproject.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;


/**
 * @author : Afaf Hanbali
 * Created on 2020-Oct-5
 */
public class PostsFragment extends Fragment implements PostsListActivityPresenter.View {
    public static final String PROGRESS_MSG_TITLE = ("Just a Sec...");
    public static final String PROGRESS_MSG_CONTENT = ("The List of Posts is loading...");


    @BindView(R.id.rv_posts)
    RecyclerView listOfPosts;

    private ProgressDialog progressDialog;
    private PostsAdapter postsAdapter = new PostsAdapter();
    private PostsListActivityPresenter presenter;
    private Unbinder unbinder;
    private int position = -1;

    public PostsFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        buildProgressDialog();
        presenter = new PostsListActivityPresenter(this);
        presenter.loadPosts();

        View view = inflater.inflate(R.layout.fragment_posts, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        presenter.disposables.clear();
    }


    @Override
    public void displayPosts(List<Post> postsList) {
        setupAdapter(postsList);
    }

    public void setPosition(int position) {
        this.position = position;
    }

    private void setupAdapter(List<Post> postList) {
        postsAdapter.setPostsList(postList);
        listOfPosts.setAdapter(postsAdapter);
        listOfPosts.setLayoutManager(new LinearLayoutManager(getContext()));

        if (position != -1) {
            postsAdapter.setHighlightedRow(position);
            listOfPosts.scrollToPosition(position);
        }
    }


    @Override
    public void showToast(String toastMsg) {
        Toast toast = Toast.makeText(getContext(), toastMsg, Toast.LENGTH_LONG);
        toast.show();
    }


    private void buildProgressDialog() {
        progressDialog = new ProgressDialog(getContext(), R.style.progressDialog);
        progressDialog.setMax(100);
        progressDialog.setMessage(PROGRESS_MSG_CONTENT);
        progressDialog.setTitle(PROGRESS_MSG_TITLE);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
    }

    @Override
    public void showProgressDialog() {
        progressDialog.show();
    }

    @Override
    public void hideProgressDialog() {
        progressDialog.dismiss();
    }
}