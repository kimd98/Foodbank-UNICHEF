package com.example.foodbank2021;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
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

    // need change to firebase real time database
    Food chocolate = new Food("Chocolate", "2" , "Anonymous", "None",
            "03/05/2021", "0");
    Food flower = new Food("Flower Cake", "4" , "Lena", "None",
            "05/05/2021", "2");
    Food kimchi = new Food("Kimchi Fried Rice", "1" , "Anonymous", "None",
            "03/05/2021", "1");

    private String[] foodArrayList = {chocolate.getName(), flower.getName(), kimchi.getName()};
    private ListView listview;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_home, container, false);
        listview = root.findViewById(R.id.foodlist);

        showList();
        return root;
    }

    public void showList() {

        ArrayAdapter adapter = new ArrayAdapter<>(getContext(),
                R.layout.activity_listview, R.id.textView, foodArrayList);
        listview.setAdapter(adapter);
    }
}