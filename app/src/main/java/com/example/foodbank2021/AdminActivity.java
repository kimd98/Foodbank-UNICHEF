package com.example.foodbank2021;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class AdminActivity extends AppCompatActivity {

    Toolbar toolbar;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    ArrayList<Food> foodList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_activity_admin);

        // fragment title tool bar
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle("Welcome to UNICHEF");

        drawerLayout=findViewById(R.id.drawer_layout);
        navigationView=findViewById(R.id.nav_view);
        View headerView = navigationView.getHeaderView(0);
        TextView textViewToChange = headerView.findViewById(R.id.username);
        textViewToChange.setText("Administrator");

        navigationView.bringToFront();
        ActionBarDrawerToggle toggle=new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.navigation_drawer_open,R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(sidenavListener);
        showList();
    }

    // custom adapter for a food list
    public class FoodAdapter extends ArrayAdapter<Food> {

        public FoodAdapter(@NonNull Context context, ArrayList<Food> androidFood) {
            super(context, 0, androidFood);
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

            View listItemView = convertView;
            if (listItemView == null) {
                listItemView = LayoutInflater.from(getContext()).inflate(R.layout.row, parent, false);
            }

            // get food information
            Food currentFood = getItem(position);
            TextView nameView = (TextView) listItemView.findViewById(R.id.foodname);
            nameView.setText(currentFood.getName());
            TextView amountView = (TextView) listItemView.findViewById(R.id.amount);
            amountView.setText("Amount: " + currentFood.getAmount());
            TextView locationView = (TextView) listItemView.findViewById(R.id.location);
            locationView.setText("Location: " + currentFood.getFridge());
            return listItemView;
        }
    }

    public void showList() {
        ListView listView;
        listView = findViewById(R.id.list_view);
        AdminActivity.FoodAdapter adapter = new AdminActivity.FoodAdapter(AdminActivity.this, foodList);
        listView.setAdapter(adapter);

        // convert firebase data -> Food class and then store in the global arraylist
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Food");
        databaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String s) {
                Food value = snapshot.getValue(Food.class);
                foodList.add(value);
                adapter.notifyDataSetChanged();
            }
            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String s) {
                adapter.notifyDataSetChanged();
            }
            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {
                adapter.notifyDataSetChanged();
            }
            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String s) {
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }

    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    private final NavigationView.OnNavigationItemSelectedListener sidenavListener=
            new NavigationView.OnNavigationItemSelectedListener() {

                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    switch (item.getItemId()) {
                        case R.id.nav_home:
//                            startActivity(new Intent(UserActivity.this, UserActivity.class));
                            break;
                        case R.id.nav_ams:
                            startActivity(new Intent(AdminActivity.this, MemberActivity.class));
                            break;
                        case R.id.nav_penalty:
                            startActivity(new Intent(AdminActivity.this, GvPenalty.class));
                            break;
                        case R.id.nav_map:
                            startActivity(new Intent(AdminActivity.this, MapActivity.class));
                            break;
                        case R.id.nav_logout:
                            startActivity(new Intent(AdminActivity.this, MainActivity.class));
                            break;
                    }
                    drawerLayout.closeDrawer(GravityCompat.START);
                    return true;
                }
            };

    // left for the side menu
    public void logout(MenuItem item) {
        startActivity(new Intent(AdminActivity.this, MainActivity.class));
        preferences.clearData(this);
        finish();
    }
}