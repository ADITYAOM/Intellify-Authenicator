package com.aditya.intellify;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {


    private EditText email;

    private EditText password;

    private Button loginButton;


    private  Button signButton;

    private ProgressBar progressBar;

    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        email = (EditText) findViewById(R.id.login_email);
        password = (EditText) findViewById(R.id.login_password);
        loginButton = (Button) findViewById(R.id.login_button);
        signButton = (Button) findViewById(R.id.login_signup);
        progressBar = (ProgressBar) findViewById(R.id.login_progressBar);

        firebaseAuth = FirebaseAuth.getInstance();

        //auto login process
        //move to main activity if user already sign in
        if (firebaseAuth.getCurrentUser() != null) {
            // User is logged in
            startActivity(new Intent(LoginActivity.this, Details.class));
            finish();
        }



        firebaseAuth = FirebaseAuth.getInstance();
//        if(btnSignup!=null) {
            signButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(LoginActivity.this, SignupActivity.class));
                }
            });
//        }

//        if(loginButton!=null) {
            loginButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String useremail = email.getText().toString();
                    String userpassword = password.getText().toString();

                    if (TextUtils.isEmpty(useremail)) {
                        Toast.makeText(LoginActivity.this.getApplicationContext(), "Enter email address!", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    if (TextUtils.isEmpty(userpassword)) {
                        Toast.makeText(LoginActivity.this.getApplicationContext(), "Enter password!", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    progressBar.setVisibility(View.VISIBLE);

                    //login user
                    firebaseAuth.signInWithEmailAndPassword(useremail, userpassword)
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    progressBar.setVisibility(View.GONE);

                                    if (!task.isSuccessful()) {

                                        if (userpassword.length() < 6) {
                                            password.setError(LoginActivity.this.getString(R.string.minimum_password));
                                        } else {
                                            Toast.makeText(LoginActivity.this, LoginActivity.this.getString(R.string.auth_failed), Toast.LENGTH_SHORT).show();
                                        }

                                    } else {
                                        LoginActivity.this.startActivity(new Intent(LoginActivity.this, Details.class));
                                        LoginActivity.this.finish();
                                    }
                                }
                            });

                }
            });
//        }


    }
}