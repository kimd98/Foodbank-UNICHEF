package com.example.foodbank2021;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private Button login;
    private TextView register;
    private EditText user_email, user_password;
    private ProgressBar progressBar;
    Switch active; // for remember me

    // This is a landing login page
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // replaced signIn button with login & username/password removed
        register=(TextView)findViewById(R.id.signup);
        login = findViewById(R.id.login);
        user_email=(EditText)findViewById(R.id.email);
        user_password=(EditText)findViewById(R.id.password);
        active = findViewById(R.id.active);

        // links to onClick function
        register.setOnClickListener(this);
        login.setOnClickListener(this);
    };

    // move to sign-up (Registration) or sign-in (Login) page
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.register:
                startActivity(new Intent(this, RegisterUser.class));
                break;
            case R.id.login:
                userLogin();
                break;
        }
    };

    // a helper function for login info validity check
    private boolean validityCheck(String input_email, String input_password) {
        if (input_email.isEmpty()) {
            user_email.setError("Email is required.");
            user_email.requestFocus();
            return true;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(input_email).matches()) {
            user_email.setError("Please enter valid email.");
            user_email.requestFocus();
            return true;
        }
        if (input_password.isEmpty()) {
            user_password.setError("Password is required.");
            user_password.requestFocus();
            return true;
        } else if (input_password.length() < 6) {
            user_password.setError("Minimum password length should be 6 characters.");
            user_password.requestFocus();
            return true;
        }
        return false;
    };

    // login activity (validity check, account match, google authentication)
    private void userLogin() {
        // casting global user data to string
        String email = user_email.getText().toString().trim();
        String password = user_password.getText().toString().trim();

        // I don't know what this is :) ?
        progressBar.setVisibility(View.VISIBLE);

        // if input data is invalid, do nothing
        if (validityCheck(email, password))
            return;

        // Firebase Authentication Sign-in Method (reference to "Users")
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Users");

        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {

            // a helper function to login as admin/user
            private void login(String uid) {
                DatabaseReference userRef = databaseReference.child(uid);
                userRef.addValueEventListener(new ValueEventListener() {    // attach a listener to get "as" value
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (active.isChecked()) {   // remember me switch ON ("as" will be saved)
                            if (snapshot.child("as").getValue(String.class).equals("admin")) { // admin
                                preferences.setDataLogin(MainActivity.this, true);
                                preferences.setDataAs(MainActivity.this, "admin");
                                startActivity(new Intent(MainActivity.this, AdminActivity.class));
                            } else if (snapshot.child("as").getValue(String.class).equals("user")) { // user
                                preferences.setDataLogin(MainActivity.this, true);
                                preferences.setDataAs(MainActivity.this, "user");
                                startActivity(new Intent(MainActivity.this, UserActivity.class));
                            }
                        } else {    // remember me switch OFF ("as" history will be ignored)
                            if (snapshot.child("as").getValue(String.class).equals("admin")) { // admin
                                preferences.setDataLogin(MainActivity.this, false);
                                startActivity(new Intent(MainActivity.this, AdminActivity.class));
                            } else if (snapshot.child("as").getValue(String.class).equals("user")) { // user
                                preferences.setDataLogin(MainActivity.this, false);
                                startActivity(new Intent(MainActivity.this, UserActivity.class));
                            }
                        }
                    };
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        // do nothing
                    };
                });
            };

            // core authentication process using the previous helper function
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    FirebaseUser user=FirebaseAuth.getInstance().getCurrentUser();
                    if (user.isEmailVerified()) {
                        login(user.getUid());
                    } else {    // email not verified
                        user.sendEmailVerification();
                        Toast.makeText(MainActivity.this,"Check your email to verify your account.",Toast.LENGTH_LONG).show();
                    }
                } else {    // unsuccessful task
                    Toast.makeText(MainActivity.this,"Failed to login! Please check your credentials.",Toast.LENGTH_LONG).show();
                }
            };
        });
    };

    // choose either admin page or user page after successfully log into the app
    @Override
    protected void onStart() {
        super.onStart();
        if (preferences.getDataLogin(this)) {
            if (preferences.getDataAs(this).equals("admin")) {
                startActivity(new Intent(MainActivity.this, AdminActivity.class));
                finish();
            } else if (preferences.getDataAs(this).equals("user")) {
                startActivity(new Intent(MainActivity.this, UserActivity.class));
                finish();
            }
        }
    };
}