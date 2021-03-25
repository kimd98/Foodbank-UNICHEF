package com.example.foodbank2021;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Objects;

public class MemberActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private FirebaseDatabase db;
    private DatabaseReference root;
    private  ArrayList<Model> list;
    private String as;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member);
        as="user";
        recyclerView=findViewById(R.id.recyclerview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        list=new ArrayList<>();
        db=FirebaseDatabase.getInstance();
        root=db.getReference("Users");

        root.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                list.clear();
                for (DataSnapshot snapshot :dataSnapshot.getChildren()){
                    if (Objects.equals(snapshot.child("as").getValue(String.class), as)) {
                        Model user = snapshot.getValue(Model.class);
                        list.add(user);
                    }
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {//디비 가져오던 중 에러 발생 시
               // Log.e("MemberActivity",String.valueOf(databaseError.toException())databaseError.toException())
            }
        });
        adapter=new MyAdapter(this,list);
        recyclerView.setAdapter(adapter); //리사이크러뷰에 어댑터 연결

    }

//    public void onClick(View v){
//        switch(v.getId()){
//            case R.id.penalty_button:
//                gvpenalty();
//                break;
//        }
//    }
//    public void gvpenalty(){
//        Toast.makeText(MemberActivity.this, "OK", Toast.LENGTH_LONG).show();
//
//    }

}