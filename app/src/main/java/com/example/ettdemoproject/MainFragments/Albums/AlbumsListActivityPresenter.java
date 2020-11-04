package com.example.ettdemoproject.MainFragments.Albums;

import com.example.ettdemoproject.networking.RetrofitHandler;

import java.util.List;

import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * @author : Afaf Hanbali
 * Created on 2020-Oct-5
 */
public class AlbumsListActivityPresenter {

    public static final String BASE_URL = "https://jsonplaceholder.typicode.com/";

    private List<Album> albumList;
    private View view;
    public CompositeDisposable disposables = new CompositeDisposable();


    public AlbumsListActivityPresenter(View view) {
        this.view = view;
    }

    public void loadAlbums() {

        view.showProgressDialog();
        Single<List<Album>> singleObservable = RetrofitHandler.getInstance(BASE_URL).getAlbums();
        singleObservable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<List<Album>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        disposables.add(d);
                    }

                    @Override
                    public void onSuccess(List<Album> albums) {
                        view.hideProgressDialog();
                        albumList = albums;
                        view.displayAlbums(albumList);
                    }

                    @Override
                    public void onError(Throwable e) {
                        view.hideProgressDialog();
                        view.showToast(e.getMessage());
                    }
                });

    }


    public interface View {

        void showProgressDialog();

        void hideProgressDialog();

        void displayAlbums(List<Album> AlbumsList);

        void showToast(String msg);

    }
}
