package com.example.foodbank2021;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.util.Map;

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
//        Intent intent = getIntent();
//        email = intent.getStringExtra("email");
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference databaseRef = database.getReference("Users");
        DatabaseReference userRef = databaseRef.child(user_uid);//
//        DatabaseReference userRef = rootRef.child(USERS);
        //Log.v("USERID", userRef.getKey());

        nameTxtView=findViewById(R.id.name_textview);
        emailTxtView=findViewById(R.id.email_textview);
        accountTxtView=findViewById(R.id.account_textview);

        userImageView=findViewById(R.id.user_imageview);
        emailImageView=findViewById(R.id.email_imageview);
        accountImageView=findViewById(R.id.account_imageview);


        userRef.addValueEventListener(new ValueEventListener() {
            String fname, mail,verify;
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot keyId: dataSnapshot.getChildren()) {
                    if (keyId.child("email").getValue().equals(email)) {
                        fname = keyId.child("firstName").getValue(String.class);
                        email=keyId.child("email").getValue(String.class);
                        verify = keyId.child("verified").getValue(String.class);
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