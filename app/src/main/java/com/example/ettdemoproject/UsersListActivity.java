package com.example.ettdemoproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

import com.example.ettdemoproject.UI.UsersAdapter;
import com.example.ettdemoproject.networking.JsonPlaceHolder;
import com.example.ettdemoproject.networking.RetrofitActivity;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

import static android.os.SystemClock.sleep;


/**
 * @author : Afaf Hanbali
 * Created on 2020-Oct-5
 */

public class UsersListActivity extends AppCompatActivity implements UsersAdapter.OnUserListener {

    //TODO : pls pick a style
    private RecyclerView mListOfUsers;
    private List<User> usersList;

    //TODO : do you need this address object ?
    private User.Address address;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mListOfUsers = (RecyclerView) findViewById(R.id.rv_users);
        fetchFromApi();

    }

    //TODO : pls make it more modular by  splitting  the API related code from UI progress code .
    public void fetchFromApi() {

        Retrofit retrofit = RetrofitActivity.getInstance();
        JsonPlaceHolder jsonPlaceHolder = retrofit.create(JsonPlaceHolder.class);
        Call<List<User>> call = jsonPlaceHolder.getUsers();


        final ProgressDialog progressDialog;
        progressDialog = new ProgressDialog(this, R.style.progressDialog);
        progressDialog.setMax(100);
        // TODO : const strings
        progressDialog.setMessage("The List of Users is loading...");
        // TODO : const strings
        progressDialog.setTitle("Just a Sec...");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.show();
        final Handler handler = new Handler();
        // TODO : bad practice , what if the response took more time to respond ( it will appear blank after 3sec mark untill its received )
        //TODO : lets show progress upon calling retrofit and hide on success/failure callbacks
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                progressDialog.dismiss();
            }
        }, 3000);
        call.enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                if (!response.isSuccessful()) {
                    //TODO : showing a user a response code is not very informative , you could build a method to map code to a proper msg .
                    showToast(Integer.toString(response.code()));
                    return;
                }
                usersList = response.body();
                setupAdapter(usersList);
            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable tt) {
                showToast(tt.getMessage());
            }
        });

    }

    public void setupAdapter(List<User> usersList) {
        UsersAdapter usersAdapter = new UsersAdapter(usersList, this);
        mListOfUsers.setAdapter(usersAdapter);
        mListOfUsers.setLayoutManager(new LinearLayoutManager(this));
    }

    public void showToast(String toastMsg) {
        Toast toast = Toast.makeText(getApplicationContext(), toastMsg, Toast.LENGTH_LONG);
        toast.show();
    }

    @Override
    public void onUserClick(int position) {
        //TODO : bad practice . pls pass/receive the whole object .
        //TODO : pls also dont call actvities directly , instead build a public static Intent startActivity inside UserInformation activity .
        // and let it write/read its own keys .
        // what if (i.e) 'id' was later changed to 'userID' , you will lose a parameter !
        Intent intent = new Intent(this, UserInformation.class);
        intent.putExtra("id", Integer.toString(usersList.get(position).getId()));
        intent.putExtra("name", usersList.get(position).getName());
        intent.putExtra("street", usersList.get(position).getStreet());
        intent.putExtra("suite", usersList.get(position).getSuite());
        intent.putExtra("city", usersList.get(position).getCity());
        intent.putExtra("zipCode", usersList.get(position).getZipCode());
        intent.putExtra("lat", Double.toString(usersList.get(position).getLatt()));
        intent.putExtra("lng", Double.toString(usersList.get(position).getLng()));
        intent.putExtra("phone", usersList.get(position).getPhone());
        intent.putExtra("website", usersList.get(position).getWebsite());
        intent.putExtra("companyName", usersList.get(position).getCompanyName());
        intent.putExtra("catchPhrase", usersList.get(position).getCatchPhrase());
        intent.putExtra("bs", usersList.get(position).getBs());
        startActivity(intent);

    }
}
