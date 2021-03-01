package com.example.foodbank2021;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {
    ListView foodlist;
    FloatingActionButton donate;

    DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
    DatabaseReference foodRef = rootRef.child("Food");

    List<Food> foodArrayList = new ArrayList<>();
    MainArrayAdapter adapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_home, container, false);

        foodlist = getView().findViewById(R.id.foodlist);
        donate = getView().findViewById(R.id.donate);

        adapter = new MainArrayAdapter(getContext(), foodArrayList);
        foodlist.setAdapter(adapter);
        
        return root;
    }

    @Override
    public void onStart() {
        super.onStart();

        foodRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                String name = snapshot.child("name").getValue(String.class);
                String amount = snapshot.child("amount").getValue(String.class);
                String donator = snapshot.child("donator").getValue(String.class);
                String receiver = snapshot.child("receiver").getValue(String.class);
                String expireDate = snapshot.child("expireDate").getValue(String.class);
                String fridge = snapshot.child("fridge").getValue(String.class);

                Food foodNew = new Food (name, amount, donator, receiver, expireDate, fridge);
                foodArrayList.add(foodNew);

                adapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });


    }
}