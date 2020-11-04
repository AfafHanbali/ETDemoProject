package com.example.ettdemoproject.MainFragments.Albums;


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
public class AlbumsFragment extends Fragment implements AlbumsListActivityPresenter.View {

    public static final String PROGRESS_MSG_TITLE = ("Just a Sec...");
    public static final String PROGRESS_MSG_CONTENT = ("The List of Albums is loading...");


    @BindView(R.id.rv_albums)
    RecyclerView listOfAlbums;

    private ProgressDialog progressDialog;
    private AlbumsAdapter albumsAdapter = new AlbumsAdapter();
    private AlbumsListActivityPresenter presenter;
    private Unbinder unbinder;

    public AlbumsFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        buildProgressDialog();
        presenter = new AlbumsListActivityPresenter(this);
        presenter.loadAlbums();

        View view = inflater.inflate(R.layout.fragment_albums, container, false);
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
    public void displayAlbums(List<Album> AlbumsList) {
        setupAdapter(AlbumsList);
    }

    private void setupAdapter(List<Album> albumList) {
        albumsAdapter.setAlbumList(albumList);
        listOfAlbums.setAdapter(albumsAdapter);
        listOfAlbums.setLayoutManager(new LinearLayoutManager(getContext()));
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