package com.example.alexandre.bar;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;

public class LoginActivity extends AppCompatActivity {

    private android.widget.EditText edtLogin;
    private android.widget.EditText edtPassword;

    private android.widget.ProgressBar progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        setContentView(R.layout.activity_login);
        this.progressBar = (ProgressBar) findViewById(R.id.progressBar);
        this.edtPassword = (EditText) findViewById(R.id.edtPassword);
        this.edtLogin = (EditText) findViewById(R.id.edtLogin);


    }

    public void loginOnParse(View view) {

        progressBar.setVisibility(View.VISIBLE);
        ParseUser.logInInBackground(edtLogin.getText().toString(),
                edtPassword.getText().toString(),
                new LogInCallback() {
                    @Override
                    public void done(ParseUser parseUser, ParseException e) {
                        if (e == null) {
                            Intent it = new Intent(LoginActivity.this, MainActivity.class);
                            startActivity(it);
                            finish();
                        }
                    }
                });

    }

    public void signUpOnParse(View view) {
        Intent it = new Intent(this, SignUpActivity.class);
        startActivity(it);

    }

}
