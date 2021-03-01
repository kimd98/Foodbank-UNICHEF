package com.example.foodbank2021;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

public class HomeFragment extends Fragment {
    ListView foodlist;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        foodlist = findViewById(R.id.foodlist);
        MainArrayAdapter adapter = new MainArrayAdapter(this, food);
        foodlist.setAdapter(adapter);
        return inflater.inflate(R.layout.admin_fragment_home, container, false);
    }
}