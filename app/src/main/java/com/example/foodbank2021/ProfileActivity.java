package com.example.foodbank2021;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

public class ProfileActivity extends AppCompatActivity {
    private TextView nameTextView;
    private TextView emailTextView, accountTextView;
    private ImageView userImageView, emailImageView, accountImageView;
    private String email, password;

    private FirebaseDatabase database;
    private DatabaseReference userRef;
    private static final String USERS="user";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        Intent intent=getIntent();
        email=intent.getStringExtra("email");

        nameTextView=findViewById(R.id.nameview);
        emailTextView=findViewById(R.id.email_textview);
        accountTextView=findViewById(R.id.account_textview);
        userImageView=findViewById(R.id.profileview);
        emailImageView=findViewById(R.id.email_imageview);
        accountImageView=findViewById(R.id.account_imageview);

        database=FirebaseDatabase.getInstance();
        userRef=database.getReference(USERS);

        userRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot ds: dataSnapshot.getChildren()){
                    if(ds.child("email").getValue().equals(email)){
                        nameTextView.setText(ds.child("firstName").getValue(String.class));
                        emailTextView.setText(email);
                        accountTextView.setText(ds.child("verified").getValue(String.class));

                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}