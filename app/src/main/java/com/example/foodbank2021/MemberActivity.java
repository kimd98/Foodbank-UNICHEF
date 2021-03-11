package com.example.foodbank2021;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MemberActivity extends AppCompatActivity {

    ArrayList<String> userList = new ArrayList<>();
    ListView listview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member);

        DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference("Users");
        dbRef.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    String userInfo = dataSnapshot.child("firstName").getValue().toString();
                    userInfo = userInfo.concat(" ");
                    userInfo = userInfo.concat(dataSnapshot.child("lastName").getValue().toString());
                    userInfo = userInfo.concat(" | ");
                    userInfo = userInfo.concat(dataSnapshot.child("email").getValue().toString());
                    userList.add(userInfo);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                //do nothing
            }
        });

        listview = this.findViewById(R.id.userlist);
        showList();
    }

    public void showList() {
        ArrayAdapter adapter = new ArrayAdapter<>(this,
                R.layout.activity_listview, R.id.name_textView, userList);
        listview.setAdapter(adapter);
    }
}