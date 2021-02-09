package com.example.foodbank2021;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.foodbank2021.MainActivity;
import com.example.foodbank2021.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterUser extends AppCompatActivity implements View.OnClickListener {
    private FirebaseAuth mAuth;
    private TextView banner, registerUser;
    private EditText editTextFirstName, editTextLastName, editTextEmail, editTextPassword;
    //private PrograssBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_user);
        mAuth = FirebaseAuth.getInstance();
        
        banner=(TextView) findViewById(R.id.banner);
        banner.setOnClickListener(this);
        
        registerUser=(Button)findViewById(R.id.registerUser);
        registerUser.setOnClickListener(this);
        
        editTextFirstName=(EditText)findViewById(R.id.Firstname);
        editTextLastName=(EditText)findViewById(R.id.Lastname);
        editTextEmail=(EditText)findViewById(R.id.Email);
        editTextPassword=(EditText)findViewById(R.id.Password);
        
        //progressBar=(progressBar)findViewById(R.id.progressBar)
        
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.banner:
                startActivity(new Intent(this, MainActivity.class));
                break;
            case R.id.registerUser:
                registerUser();
                break;
        }
    }

    private void registerUser() {
        String email=editTextEmail.getText().toString().trim();
        String password=editTextPassword.getText().toString().trim();
        String firstName=editTextFirstName.getText().toString().trim();
        String lastName=editTextLastName.getText().toString().trim();

        if(firstName.isEmpty()){
            editTextFirstName.setError("Firstname is required!");
            editTextFirstName.requestFocus();
            return;
        }
        if(lastName.isEmpty()){
            editTextLastName.setError("Lastname is required!");
            editTextLastName.requestFocus();
            return;
        }
        if(email.isEmpty()){
            editTextEmail.setError("Email is required!");
            editTextEmail.requestFocus();
            return;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            editTextEmail.setError("Please provide valid email!");
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

        //progressBar.setVisibility(View.VISIBLE);
        mAuth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if(task.isSuccessful()){
                            User user=new User(firstName,lastName,email);

                            FirebaseDatabase.getInstance().getReference("Users")
                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                    .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {

                                    if(task.isSuccessful()){
                                        Toast.makeText(RegisterUser.this,"User has been registered succesfully!",Toast.LENGTH_LONG).show();
                                        //progressBar.setVisibility(View.VISIBLE);

                                    }else{
                                        Toast.makeText(RegisterUser.this,"Failed to register! Try again!",Toast.LENGTH_LONG).show();
                                        //progressBar.setVisibility(View.VISIBLE);
                                    }
                                }
                            });
                        }else{
                            Toast.makeText(RegisterUser.this,"Failed to register! Try again!",Toast.LENGTH_LONG).show();
                            //progressBar.setVisibility(View.VISIBLE);
                        }
                    }
                });

    }
}