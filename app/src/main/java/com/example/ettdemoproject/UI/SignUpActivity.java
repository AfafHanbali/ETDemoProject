package com.example.ettdemoproject.UI;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ettdemoproject.R;
import com.example.ettdemoproject.SignUpHelperClass;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SignUpActivity extends AppCompatActivity {

    private static final String PROGRESS_MSG_CONTENT = "Signing Up..";
    private static final String TITLE = "Sign Up Page";
    private static final String PATH = "Users";
    private static final String NAME_ERROR = "Enter Your Full Name";
    private static final String USERNAME_ERROR = "Enter Your Username";
    private static final String PASSWORD_ERROR = "Enter Your Password";
    private static final String RE_PASSWORD_ERROR = "Re-enter Your Password";
    private static final String PASSWORD_LENGTH_ERROR = "Password Can't be Less Than 6 Characters";
    private static final String PASSWORDS_MISMATCH_ERROR = "It Doesn't Match your Password";
    private static final String EMAIL_ERROR = "Enter Your Email";
    private static final String NOT_VALID_EMAIL_ERROR = "Please Enter a Valid Email";
    private static final String FAIL_ERROR = "Failed";

    @BindView(R.id.signUpToolBar)
    Toolbar signUpToolbar;
    @BindView(R.id.et_signUp_name)
    EditText nameEditText;
    @BindView(R.id.et_signUp_username)
    EditText usernameEditText;
    @BindView(R.id.et_signUp_password)
    EditText passwordEditText;
    @BindView(R.id.et_signUp_repassword)
    EditText rePasswordEditText;
    @BindView(R.id.et_signUp_email)
    EditText emailEditText;
    @BindView(R.id.signUpButton)
    Button signUpButton;
    @BindView(R.id.tv_login)
    TextView loginLink;

    private FirebaseAuth firebaseAuth;
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference reference = database.getReference(PATH);

    private ProgressDialog progressDialog;
    private String name;
    private String username;
    private String password;
    private String rePassword;
    private String email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        ButterKnife.bind(this);
        setSupportActionBar(signUpToolbar);
        getSupportActionBar().setTitle(TITLE);

        firebaseAuth = FirebaseAuth.getInstance();

        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name = nameEditText.getText().toString().trim();
                username = usernameEditText.getText().toString().trim();
                password = passwordEditText.getText().toString().trim();
                rePassword = rePasswordEditText.getText().toString().trim();
                email = emailEditText.getText().toString().trim();

                if (isValidInfo()) {
                    signUp();
                }
            }
        });

        loginLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    private void showProgressDialog() {
        progressDialog = new ProgressDialog(SignUpActivity.this, R.style.progressDialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage(PROGRESS_MSG_CONTENT);
        progressDialog.show();
    }

    private boolean isValidInfo() {
        if (name.isEmpty()) {
            nameEditText.setError(NAME_ERROR);
            nameEditText.requestFocus();
            return false;
        }
        if (username.isEmpty()) {
            usernameEditText.setError(USERNAME_ERROR);
            usernameEditText.requestFocus();
            return false;
        }
        if (password.isEmpty()) {
            passwordEditText.setError(PASSWORD_ERROR);
            passwordEditText.requestFocus();
            return false;
        }

        if (rePassword.isEmpty()) {
            rePasswordEditText.setError(RE_PASSWORD_ERROR);
            rePasswordEditText.requestFocus();
            return false;
        }
        if (email.isEmpty()) {
            emailEditText.setError(EMAIL_ERROR);
            emailEditText.requestFocus();
            return false;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            emailEditText.setError(NOT_VALID_EMAIL_ERROR);
            emailEditText.requestFocus();
            return false;
        }
        if (password.length() < 6) {
            passwordEditText.setError(PASSWORD_LENGTH_ERROR);
            passwordEditText.requestFocus();
            return false;
        }
        if (!password.equals(rePassword)) {
            rePasswordEditText.setError(PASSWORDS_MISMATCH_ERROR);
            rePasswordEditText.requestFocus();
            return false;
        }

        return true;
    }

    private void signUp() {
        showProgressDialog();

        firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    SignUpHelperClass helperClass = new SignUpHelperClass(name, username, password, email);
                    reference.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(helperClass).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                progressDialog.dismiss();
                                startActivity(new Intent(SignUpActivity.this, MainActivity.class));
                            } else {
                                progressDialog.dismiss();
                                Toast.makeText(SignUpActivity.this, FAIL_ERROR, Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                } else {
                    progressDialog.dismiss();
                    Toast.makeText(SignUpActivity.this, FAIL_ERROR, Toast.LENGTH_LONG).show();
                }
            }
        });


    }
}
