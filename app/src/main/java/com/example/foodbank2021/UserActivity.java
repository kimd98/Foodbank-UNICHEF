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
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class UserActivity extends AppCompatActivity {

    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private DatabaseReference mDatabase1 = FirebaseDatabase.getInstance().getReference("Food");
    private DatabaseReference mDatabase2 = FirebaseDatabase.getInstance().getReference("Fridge");
    private final String user_uid = FirebaseAuth.getInstance().getCurrentUser().getUid();

    Toolbar toolbar;
    FloatingActionButton donate;
    ArrayList<Food> foodList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_activity_user);

        // fragment title tool bar
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle("Welcome to UNICHEF");

        // side bar
        drawerLayout=findViewById(R.id.drawer_layout);
        navigationView=findViewById(R.id.nav_view);

        // display user email on drawer
        String user_email= FirebaseAuth.getInstance().getCurrentUser().getEmail();
        //Uri user_profile=FirebaseAuth.getInstance().getCurrentUser().getPhotoUrl();

        View headerView = navigationView.getHeaderView(0);
        TextView textViewToChange = headerView.findViewById(R.id.username);
        textViewToChange.setText(user_email);

        //ImageView imageViewToChange=headerView.findViewById(R.id.imageView);
        //imageViewToChange.setImageURI(user_profile);

        navigationView.bringToFront();
        ActionBarDrawerToggle toggle=new ActionBarDrawerToggle(this,drawerLayout,toolbar,
                R.string.navigation_drawer_open,R.string.navigation_drawer_close);
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
        FoodAdapter adapter = new FoodAdapter(UserActivity.this, foodList);
        listView.setAdapter(adapter);

        // convert firebase data -> Food class and then store in the global arraylist
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Food");
        databaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String s) {
                Food value = snapshot.getValue(Food.class);
                // only if not taken, add to the list
                if (value.getRecipient().equals("none")) {
                    foodList.add(value);
                    adapter.notifyDataSetChanged();
                }
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


        // donation floating button
        donate = findViewById(R.id.donate);
        donate.setOnClickListener(new View.OnClickListener() {
            boolean click = true;

            @Override
            public void onClick(View v) {
                if (click) {
                    Toast.makeText(UserActivity.this, "Hello?", Toast.LENGTH_SHORT).show();
                    // put a donation function
                    click = false;
                } else {
                    click = true;
                }
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
                            startActivity(new Intent(UserActivity.this, UserActivity.class));
                            break;
                        case R.id.nav_profile:
                            startActivity(new Intent(UserActivity.this, ProfileActivity.class));
                            break;
                        case R.id.nav_qrcode:
                            startActivity(new Intent(UserActivity.this, createQRcode.class));
                            break;
                        case R.id.nav_map:
                            startActivity(new Intent(UserActivity.this, MapActivity.class));
                            break;
                        case R.id.nav_logout:
                            startActivity(new Intent(UserActivity.this, MainActivity.class));
                            break;
                    }
                    drawerLayout.closeDrawer(GravityCompat.START);
                    return true;
                }
            };

    public void logout(MenuItem item) {
        startActivity(new Intent(UserActivity.this, MainActivity.class));
        preferences.clearData(this);
        finish();
    }
}