package com.example.ettdemoproject.Presenters;

import com.example.ettdemoproject.DataModel.User;
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
public class UsersListActivityPresenter {

    public static final String BASE_URL = "https://jsonplaceholder.typicode.com/";

    private List<User> usersList;
    private View view;
    public CompositeDisposable disposables = new CompositeDisposable();


    public UsersListActivityPresenter(View view) {
        this.view = view;
    }

    public void loadUsers() {

        view.showProgressDialog();
        Single<List<User>> singleObservable = RetrofitHandler.getInstance(BASE_URL).getUsers();
        singleObservable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<List<User>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        disposables.add(d);
                    }

                    @Override
                    public void onSuccess(List<User> users) {
                        view.hideProgressDialog();
                        usersList = users;
                        view.displayUsers(usersList);
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
        void displayUsers(List<User> usersList);
        void showToast(String msg);

    }
}
