package com.example.ettdemoproject.UI;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ettdemoproject.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LoginActivity extends AppCompatActivity {

    private static final String PROGRESS_MSG_CONTENT = "Authenticating...";
    private static final String TITLE = "Login Page";
    private static final String TAG = "Login Status";
    private static final String SUCCESS_MSG = "signInWithEmail:success";
    private static final String EMAIL_FAILURE_MSG = "signInWithEmail:failure";
    private static final String FAILURE_MSG = "Authentication failed";
    private static final String USERNAME_ERROR = "Enter Your Email/Username";
    private static final String EMAIL_ERROR = "Enter Your Password";



    private FirebaseAuth firebaseAuth;

    @BindView(R.id.loginToolBar)
    Toolbar loginToolbar;
    @BindView(R.id.et_login_username)
    EditText usernameEditText;
    @BindView(R.id.et_login_password)
    EditText passwordEditText;
    @BindView(R.id.loginButton)
    Button loginButton;
    @BindView(R.id.tv_signUp)
    TextView signUpLink;

    private ProgressDialog progressDialog;
    private String username;
    private String password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        firebaseAuth = FirebaseAuth.getInstance();
        setSupportActionBar(loginToolbar);
        getSupportActionBar().setTitle(TITLE);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                username = usernameEditText.getText().toString();
                password = passwordEditText.getText().toString();

                if (TextUtils.isEmpty(username)) {
                    usernameEditText.setError(USERNAME_ERROR);
                    return;
                }
                if (TextUtils.isEmpty(password)) {
                    passwordEditText.setError(EMAIL_ERROR);
                    return;
                }
                login(username, password);
            }
        });

        signUpLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // to be continued..
            }
        });


    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = firebaseAuth.getCurrentUser();
        //updateUI(currentUser);
    }

    private void showProgressDialog() {
        progressDialog = new ProgressDialog(LoginActivity.this, R.style.progressDialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage(PROGRESS_MSG_CONTENT);
        progressDialog.show();
    }

    private void login(String username, String password) {
        showProgressDialog();

        firebaseAuth.signInWithEmailAndPassword(username, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, SUCCESS_MSG);
                            FirebaseUser user = firebaseAuth.getCurrentUser();
                            progressDialog.dismiss();
                            startActivity(new Intent(LoginActivity.this, MainActivity.class));
                        } else {
                            Log.w(TAG, EMAIL_FAILURE_MSG, task.getException());
                            Toast.makeText(LoginActivity.this, FAILURE_MSG,
                                    Toast.LENGTH_SHORT).show();
                        }

                        // ...
                    }
                });
    }
}
