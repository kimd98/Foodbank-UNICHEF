package com.example.foodbank2021;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.zxing.client.result.BookmarkDoCoMoResultParser;

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

    PopupWindow popUp;
    boolean click = true;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_home, container, false);
        listview = root.findViewById(R.id.foodlist);
        popUp = new PopupWindow(getContext());

        LinearLayout layout = new LinearLayout(getContext());
        TextView tv = new TextView(getContext());

        showList();

        FloatingActionButton donate = root.findViewById(R.id.donate);
        donate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (click) {
                    popUp.showAtLocation(layout, Gravity.BOTTOM, 10, 10);
                    popUp.update(50, 50, 300, 80);
                    click = false;
                } else {
                    popUp.dismiss();
                    click = true;
                }
            }
        });

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        layout.setOrientation(LinearLayout.VERTICAL);

        tv.setText("Please Donate Your Food.");

        layout.addView(tv, params);
        popUp.setContentView(layout);
        //popUp.showAtLocation(layout, Gravity.CENTER, 0, 0);

        return root;
    }

    public void showList() {
        ArrayAdapter adapter = new ArrayAdapter<>(getContext(),
                R.layout.activity_listview, R.id.textView, foodArrayList);
        listview.setAdapter(adapter);
    }
}