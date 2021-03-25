package com.example.foodbank2021;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import static androidx.constraintlayout.motion.utils.Oscillator.TAG;
import static com.google.firebase.auth.FirebaseAuth.getInstance;

public class FoodActivity extends AppCompatActivity implements View.OnClickListener{

    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    String foodID;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.food_activity);

        String foodname = getIntent().getStringExtra("FOODNAME");
        String amount = getIntent().getStringExtra("AMOUNT");
        String location = getIntent().getStringExtra("LOCATION");
        String expirydate = getIntent().getStringExtra("EXPIRYDATE");
        String ID = getIntent().getStringExtra("FOODID");
        foodID = ID;

        Button confirm = findViewById(R.id.confirm_button);
        Button cancel = findViewById(R.id.cancel_button);
        confirm.setOnClickListener(this);
        cancel.setOnClickListener(this);

        TextView nameText = findViewById(R.id.confirm_name);
        nameText.setText(foodname);
        TextView amountText = findViewById(R.id.confirm_amount);
        amountText.setText(amount);
        TextView locationText = findViewById(R.id.confirm_location);
        locationText.setText(location);
        TextView expirydateText = findViewById(R.id.confirm_expirydate);
        expirydateText.setText(expirydate);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.confirm_button:
                //DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("Food");
                //mDatabase.child(foodID).child("recipient").setValue(user.getUid());
                startActivity(new Intent(this, ConfirmActivity.class));
                break;
            case R.id.cancel_button:
                startActivity(new Intent(this, UserActivity.class));
                break;
        }
    }
}
