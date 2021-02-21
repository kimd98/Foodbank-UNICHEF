package com.example.foodbank2021;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    // This is a landing login page
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // replaced signIn button with login & username/password removed
        TextView register = findViewById(R.id.signup);
        Button login = findViewById(R.id.login);

        // links to onClick function
        register.setOnClickListener(this);
        login.setOnClickListener(this);
    };

    // move to sign-up (Registration) or sign-in (Login) page
    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.signup:
                startActivity(new Intent(this, RegisterUser.class));
                break;
            case R.id.login:
                startActivity(new Intent(this, LoginActivity.class));
                break;
        }
    };
}