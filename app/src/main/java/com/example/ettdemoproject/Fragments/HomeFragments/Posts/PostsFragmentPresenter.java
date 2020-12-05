package com.example.ettdemoproject.Fragments.HomeFragments.Posts;

import com.example.ettdemoproject.DataModel.Post;
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
public class PostsFragmentPresenter {

    public static final String BASE_URL = "https://jsonplaceholder.typicode.com/";

    private List<Post> postsList;
    private View view;
    public CompositeDisposable disposables = new CompositeDisposable();


    public PostsFragmentPresenter(View view) {
        this.view = view;
    }

    public void attachView(View view) {
        if (this.view == null) {
            this.view = view;
        }
    }

    public void detachView() {
        view = null;

    }

    public void loadPosts(int startIndex, int limit, boolean load) {

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
                        view.hideProgressBar();
                        if(!load){
                            postsList = posts.subList(0, 10);
                        }
                        else if(limit <= posts.size()) {
                            for(int i=startIndex; i<limit; i++){
                                Post post = posts.get(i);
                                postsList.add(post);
                            }
                        }
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

        void hideProgressBar();

        void displayPosts(List<Post> postsList);

        void showToast(String msg);

    }
}
