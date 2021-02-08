package com.example.foodbank2021;

import android.app.Activity;
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
    private EditText username,
            password;
    private Button login;
    private TextView register;
    private EditText editTextEmail, editTextPassword;
    private Button signIn;
    private FirebaseAuth mAuth;
    private ProgressBar progressBar;

    Switch active;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        register=(TextView) findViewById(R.id.signup);
        register.setOnClickListener(this);
        signIn=(Button)findViewById(R.id.login);
        signIn.setOnClickListener(this);
        editTextEmail=(EditText)findViewById(R.id.email);
        editTextPassword=(EditText)findViewById(R.id.password);

        username = findViewById(R.id.email);
        password = findViewById(R.id.password);
        login = findViewById(R.id.login);
        active = findViewById(R.id.active);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
                databaseReference.child("login").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        String input1 = username.getText().toString();
                        String input2 = password.getText().toString();

                        if (dataSnapshot.child(input1).exists()) {
                            if (dataSnapshot.child(input1).child("password").getValue(String.class).equals(input2)) {
                                if (active.isChecked()) { // remember me
                                    if (dataSnapshot.child(input1).child("as").getValue(String.class).equals("admin")) { // remember admin
                                        preferences.setDataLogin(MainActivity.this, true);
                                        preferences.setDataAs(MainActivity.this, "admin");
                                        startActivity(new Intent(MainActivity.this, AdminActivity.class));
                                    } else if (dataSnapshot.child(input1).child("as").getValue(String.class).equals("user")){ // remember user
                                        preferences.setDataLogin(MainActivity.this, true);
                                        preferences.setDataAs(MainActivity.this, "user");
                                        startActivity(new Intent(MainActivity.this, UserActivity.class));
                                    }
                                } else {
                                    if (dataSnapshot.child(input1).child("as").getValue(String.class).equals("admin")) {
                                        preferences.setDataLogin(MainActivity.this, false);
                                        startActivity(new Intent(MainActivity.this, AdminActivity.class));
                                    } else if (dataSnapshot.child(input1).child("as").getValue(String.class).equals("user")){
                                        preferences.setDataLogin(MainActivity.this, false);
                                        startActivity(new Intent(MainActivity.this, UserActivity.class));
                                    }
                                }
                            } else {
                                Toast.makeText(MainActivity.this, "password", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(MainActivity.this, "username", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            };
        });
    };

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

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.register:
                startActivity(new Intent(this, RegisterUser.class));
                break;

            case R.id.signIn:
                userLogin();
                break;

        }
    }

    private void userLogin() {
        String email=editTextEmail.getText().toString().trim();
        String password=editTextPassword.getText().toString().trim();

        if(email.isEmpty()){
            editTextEmail.setError("Email is required!");
            editTextEmail.requestFocus();
            return;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            editTextEmail.setError("Please enter valid email!");
            editTextEmail.requestFocus();
            return;
        }
        if(password.isEmpty()){
            editTextPassword.setError("Password is required!");
            editTextPassword.requestFocus();
            return;
        }
        if(password.length()<6){
            editTextPassword.setError("Min password length should be 6 characters!");
            editTextPassword.requestFocus();
            return;
        }

        progressBar.setVisibility(View.VISIBLE);

        mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if(task.isSuccessful()){
                    FirebaseUser user=FirebaseAuth.getInstance().getCurrentUser();

                    if(user.isEmailVerified()){
                        startActivity(new Intent(MainActivity.this,UserActivity.class));
                    }else{
                        user.sendEmailVerification();
                        Toast.makeText(MainActivity.this,"Check your email to verifiy your account!",Toast.LENGTH_LONG).show();
                    }
                    startActivity(new Intent(MainActivity.this,UserActivity.class));

                }else{
                    Toast.makeText(MainActivity.this,"Failed to login! Please check your credentials",Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}