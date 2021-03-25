package com.example.foodbank2021;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

public class GvPenalty extends AppCompatActivity {
    private EditText useremail;
    private Button gv_pen;

    private FirebaseDatabase db;
    private DatabaseReference root;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gv_penalty);
        useremail=(EditText)findViewById(R.id.penalty_email);
        gv_pen=(Button)findViewById(R.id.gv_penalty);

        db=FirebaseDatabase.getInstance();
        root=db.getReference("Users");


        gv_pen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                give_penalty();
            }
        });
    }
    public void give_penalty(){
        String email=useremail.getText().toString().trim();

        root.addListenerForSingleValueEvent(new ValueEventListener() {
            String p;
            int pen;
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot keyId: dataSnapshot.getChildren()) {
                    if (Objects.equals(keyId.child("email").getValue(String.class), email)) {
                        p = keyId.child("penalty").getValue().toString();
                        pen = Integer.parseInt(p);
                        pen+=1;
                        p=String.valueOf(pen);
                        root.child(keyId.getKey()).child("penalty").setValue(pen);

                    }
                    else{
                        Toast.makeText(GvPenalty.this, "Please write correct User's email!", Toast.LENGTH_LONG).show();

                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}