package com.example.ettdemoproject.Fragments.HomeFragments.Albums;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.ettdemoproject.DataModel.Album;
import com.example.ettdemoproject.networking.RetrofitHandler;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * @author : Afaf Hanbali
 * Created on 2020-Oct-5
 */
public class AlbumsFragmentPresenter {

    public static final String BASE_URL = "https://jsonplaceholder.typicode.com/";

    private List<Album> albumList;
    private View view;
    public CompositeDisposable disposables = new CompositeDisposable();


    public AlbumsFragmentPresenter(View view) {
        this.view = view;
    }

    /*
    public void attachView(View view) {
        if (this.view == null) {
            this.view = view;
        }
    }


    public void detachView() {
        view = null;

    }*/

    public void loadAlbums(int startIndex, int limit, boolean load) {

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
                        view.hideProgressBar();
                        if(!load){
                            albumList = albums.subList(0, 10);
                        }
                        else if(limit <= albums.size()) {
                            for(int i=startIndex; i<limit; i++){
                                Album album = albums.get(i);
                                albumList.add(album);
                            }
                        }
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

        void hideProgressBar();

        void displayAlbums(List<Album> AlbumsList);

        void showToast(String msg);

    }
}
