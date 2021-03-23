package com.example.foodbank2021;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class FoodActivity extends AppCompatActivity implements View.OnClickListener{

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.food_activity);

        Button confirm = findViewById(R.id.confirm_button);
        Button cancel = findViewById(R.id.cancel_button);
        confirm.setOnClickListener(this);
        cancel.setOnClickListener(this);

        String foodname = getIntent().getStringExtra("FOODNAME");
        String amount = getIntent().getStringExtra("AMOUNT");
        String location = getIntent().getStringExtra("LOCATION");
        String expirydate = getIntent().getStringExtra("EXPIRYDATE");

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
                // check QR code
                break;
            case R.id.cancel_button:
                startActivity(new Intent(this, UserActivity.class));
                break;
        }
    }
}
