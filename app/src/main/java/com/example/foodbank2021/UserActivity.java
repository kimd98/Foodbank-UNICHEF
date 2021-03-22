package com.example.foodbank2021;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
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

import com.google.android.gms.maps.UiSettings;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Arrays;

import static java.lang.Boolean.parseBoolean;

public class UserActivity extends AppCompatActivity {

    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private DatabaseReference mDatabase1 = FirebaseDatabase.getInstance().getReference().child("Food");
    private DatabaseReference mDatabase2 = FirebaseDatabase.getInstance().getReference().child("Fridge");
    private final String user_uid = FirebaseAuth.getInstance().getCurrentUser().getUid();

    boolean verified = false;
    Toolbar toolbar;
    FloatingActionButton donate;

    // data string arrays
    String[] nameArr;
    String[] amountArr;
    String[] fridgeArr;
    String[] locationArr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_activity_user);

        // find verified
        DatabaseReference nameRef = FirebaseDatabase.getInstance().getReference("Users");
        nameRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    if (dataSnapshot.getValue() == user_uid) {
                        verified = parseBoolean(dataSnapshot.child("verified").getValue().toString());
                        break;
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // do nothing
            }
        });


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

        // getData();
        showList();
    }

    public void updateFood (Food item) {
        nameArr = Arrays.copyOf(nameArr, nameArr.length + 1);
        amountArr = Arrays.copyOf(amountArr, amountArr.length + 1);
        fridgeArr = Arrays.copyOf(fridgeArr, fridgeArr.length + 1);
        nameArr[nameArr.length-1] = item.getName();
        amountArr[amountArr.length-1] = item.getAmount();
        fridgeArr[fridgeArr.length-1] = item.getFridgeID();
    }

    public void updateLocation (String item) {
        locationArr = Arrays.copyOf(locationArr, locationArr.length + 1);
        locationArr[locationArr.length-1] = item;
    }

    private void getData() {

        mDatabase1.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Food food = dataSnapshot.getValue(Food.class);
                    updateFood(food);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(UserActivity.this, "Fail to get food data.", Toast.LENGTH_SHORT).show();
            }
        });

        mDatabase2.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (int i =0; i < fridgeArr.length; i++) {
                    String fridgeID = fridgeArr[i];
                    String location_str = snapshot.child(fridgeID).child("location").getValue(String.class);
                    updateLocation(location_str);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(UserActivity.this, "Fail to get location.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    // custom adapter subclass
    public class MyAdapter extends ArrayAdapter<String> {
        Context context;
        String rName[];
        String rLocation[];
        String rAmount[];

        MyAdapter (Context c, String s1[], String s2[], String s3[]) {
            super(c, R.layout.row, R.id.foodname, s1);
            this.context = c;
            this.rName = s1;
            this.rLocation = s2;
            this.rAmount = s3;
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            LayoutInflater layoutInflater = (LayoutInflater) getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View row = layoutInflater.inflate(R.layout.row, parent, false);
            TextView name = row.findViewById(R.id.foodname);
            TextView location = row.findViewById(R.id.location);
            TextView amount = row.findViewById(R.id.amount);

            name.setText(rName[position]);
            amount.setText(rAmount[position]);
            location.setText(rLocation[position]);

            return row;
        }
    }

    public void showList() {
        ListView listView;

        // temporary data
        nameArr = new String[]{"Oranges", "Chocolate", "Milk", "Tacos", "Cherry"};
        amountArr = new String[]{"3", "1", "1L", "30", "40"};
        locationArr = new String[]{"Seoul City Hall", "Ewha Girls' High School", "Duck-Su Palace",
                            "Seoul City Hall", "Seodaemun Police Office"};

        MyAdapter adapter = new MyAdapter(this, nameArr, locationArr, amountArr);
        listView = findViewById(R.id.list_view);
        listView.setAdapter(adapter);

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