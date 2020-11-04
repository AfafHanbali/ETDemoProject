package com.example.ettdemoproject.MainFragments.Posts;

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
public class PostsListActivityPresenter {

    public static final String BASE_URL = "https://jsonplaceholder.typicode.com/";

    private List<Post> postsList;
    private View view;
    public CompositeDisposable disposables = new CompositeDisposable();


    public PostsListActivityPresenter(View view) {
        this.view = view;
    }

    public void loadPosts() {

        view.showProgressDialog();
        Single<List<Post>> singleObservable = RetrofitHandler.getInstance(BASE_URL).getPosts();
        singleObservable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<List<Post>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        disposables.add(d);
                    }

                    @Override
                    public void onSuccess(List<Post> posts) {
                        view.hideProgressDialog();
                        postsList = posts;
                        view.displayPosts(postsList);
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

        void displayPosts(List<Post> postsList);

        void showToast(String msg);

    }
}
