package com.example.foodbank2021;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Map;
import java.util.Objects;

public class ProfileActivity extends AppCompatActivity {
    private TextView nameTxtView, emailTxtView, accountTxtView;
    private ImageView userImageView, emailImageView, accountImageView;
    private final String TAG = this.getClass().getName().toUpperCase();
    private FirebaseDatabase database;
    private DatabaseReference mDatabase;
    private Map<String, String> userMap;
    private String email;
    private String userid;
    private static final String USERS = "Users";
    private Button verify_now;

    private final String user_uid= FirebaseAuth.getInstance().getCurrentUser().getUid();
    private final String user_email= FirebaseAuth.getInstance().getCurrentUser().getEmail();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        verify_now=(Button) findViewById(R.id.verify);
        verify_now.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference databaseRef = database.getReference("Users");

        nameTxtView=findViewById(R.id.name_textview);
        emailTxtView=findViewById(R.id.email_textview);
        accountTxtView=findViewById(R.id.account_textview);
        userImageView=findViewById(R.id.user_imageview);
        emailImageView=findViewById(R.id.email_imageview);
        accountImageView=findViewById(R.id.account_imageview);

        databaseRef.addValueEventListener(new ValueEventListener() {
            String fname, email,verify;
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot keyId: dataSnapshot.getChildren()) {
                    if (Objects.equals(keyId.child("email").getValue(String.class), user_email)) {
                        fname = keyId.child("firstName").getValue().toString();
                        email=keyId.child("email").getValue().toString();
                        verify = keyId.child("verified").getValue().toString();
                        break;
                    }
                }
                nameTxtView.setText(fname);
                emailTxtView.setText(email);
                accountTxtView.setText(verify);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });
    }
}