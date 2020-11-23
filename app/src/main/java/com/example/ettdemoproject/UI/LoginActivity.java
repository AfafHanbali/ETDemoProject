package com.example.ettdemoproject.UI;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
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
import com.google.firebase.auth.SignInMethodQueryResult;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LoginActivity extends AppCompatActivity {

    private static final String PROGRESS_MSG_CONTENT = "Authenticating...";
    private static final String TITLE = "Login Page";
    private static final String TAG = "Login Status";
    private static final String SUCCESS_MSG = "signInWithEmail:success";
    private static final String EMAIL_FAILURE_MSG = "signInWithEmail:failure";
    private static final String USERNAME_ERROR = "Enter Your Email/Username";
    private static final String PASSWORD_ERROR = "Enter Your Password";
    private static final String PATH = "Users";
    private static final String NO_USERNAME_ERROR = "User Doesn't exist";
    private static final String NO_EMAIL_ERROR = "Email Doesn't exist";
    private static final String WRONG_PASSWORD_ERROR = "Password isn't Correct";


    private FirebaseAuth firebaseAuth;
    private FirebaseDatabase database = FirebaseDatabase.getInstance();

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

                if (username.isEmpty()) {
                    usernameEditText.setError(USERNAME_ERROR);
                    return;
                }
                if (password.isEmpty()) {
                    passwordEditText.setError(PASSWORD_ERROR);
                    return;
                }
                if (username.contains("@")) {
                    loginAuth(username, password);
                } else {
                    login();
                }
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

    private void loginAuth(String username, String password) {
        showProgressDialog();

        String email = username;
        firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, SUCCESS_MSG);
                            FirebaseUser user = firebaseAuth.getCurrentUser();
                            progressDialog.dismiss();
                            startActivity(new Intent(LoginActivity.this, MainActivity.class));
                        } else {
                            progressDialog.dismiss();
                            firebaseAuth.fetchSignInMethodsForEmail(email).addOnCompleteListener(new OnCompleteListener<SignInMethodQueryResult>() {
                                @Override
                                public void onComplete(@NonNull Task<SignInMethodQueryResult> task) {
                                    boolean checkEmail = !task.getResult().getSignInMethods().isEmpty();
                                    if (!checkEmail) {
                                        passwordEditText.setError(null);
                                        usernameEditText.setError(NO_EMAIL_ERROR);
                                        usernameEditText.requestFocus();
                                    } else {
                                        usernameEditText.setError(null);
                                        passwordEditText.setError(WRONG_PASSWORD_ERROR);
                                        passwordEditText.requestFocus();
                                    }
                                }
                            });

                        }
                    }
                });
    }


    private void login() {
        showProgressDialog();

        DatabaseReference reference = database.getReference(PATH);
        Query checkUser = reference.orderByChild("username").equalTo(username);

        checkUser.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    //usernameEditText.setError(null);
                    String passwordDB = dataSnapshot.child(username).child("password").getValue().toString();

                    if (passwordDB.equals(password)) {
                        progressDialog.dismiss();
                        startActivity(new Intent(LoginActivity.this, MainActivity.class));
                    } else {
                        progressDialog.dismiss();
                        passwordEditText.setError(WRONG_PASSWORD_ERROR);
                        passwordEditText.requestFocus();
                    }
                } else {
                    progressDialog.dismiss();
                    usernameEditText.setError(NO_USERNAME_ERROR);
                    usernameEditText.requestFocus();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }
}
