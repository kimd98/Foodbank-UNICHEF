package com.example.foodbank2021;

import android.content.Context;
import android.content.Intent;
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

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import static java.lang.Boolean.parseBoolean;

public class UserActivity extends AppCompatActivity {

    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private final String user_uid= FirebaseAuth.getInstance().getCurrentUser().getUid();

    boolean verified = false;
    Toolbar toolbar;
    FloatingActionButton donate;

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
        toolbar.setTitle("Home");

        // side bar
        drawerLayout=findViewById(R.id.drawer_layout);
        navigationView=findViewById(R.id.nav_view);

        // display user email on drawer
        String user_email= FirebaseAuth.getInstance().getCurrentUser().getEmail();
        View headerView = navigationView.getHeaderView(0);
        TextView textViewToChange = (TextView) headerView.findViewById(R.id.username);
        textViewToChange.setText(user_email);

        navigationView.bringToFront();
        ActionBarDrawerToggle toggle=new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.navigation_drawer_open,R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(sidenavListener);

        // show the food list
        showList();
    }

    public class MyAdapter extends ArrayAdapter<String> {
        Context context;
        String rName[];
        String rLocation[];
        int rImage[];

        MyAdapter (Context c, String s1[], String s2[], int img[]) {
            super(c, R.layout.row, R.id.foodname, s1);
            this.context = c;
            this.rName = s1;
            this.rLocation = s2;
            this.rImage = img;
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            LayoutInflater layoutInflater = (LayoutInflater) getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View row = layoutInflater.inflate(R.layout.row, parent, false);
            ImageView images = row.findViewById(R.id.image);
            TextView name = row.findViewById(R.id.foodname);
            TextView location = row.findViewById(R.id.location);

            images.setImageResource(rImage[position]);
            name.setText(rName[position]);
            location.setText(rLocation[position]);

            return row;
        }
    }

    public void showList() {

        ListView listView;
        ArrayList<String> nameList = new ArrayList<>();
        ArrayList<String> locationList = new ArrayList<>();
        int image[] = {R.drawable.burger, R.drawable.ic_launcher_foreground};

        DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference();
        dbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                DataSnapshot foodsn = snapshot.child("Food");
                DataSnapshot fridgesn = snapshot.child("Fridge");

                for (DataSnapshot dataSnapshot : foodsn.getChildren()) {
                    String name_str = dataSnapshot.child("name").getValue().toString();
                    String fridge_str = dataSnapshot.child("fridge").getValue().toString();
                    String location_str = fridgesn.child(fridge_str).child("location").getValue().toString();

                    nameList.add(name_str);
                    locationList.add(location_str);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // do nothing
            }
        });

        String[] nameArr = (String[]) nameList.toArray(new String[0]);
        String[] locationArr = (String[]) locationList.toArray(new String[0]);

        MyAdapter adapter = new MyAdapter(this, nameArr, locationArr, image);
        listView = findViewById(R.id.list_view);
        listView.setAdapter(adapter);

        donate = findViewById(R.id.donate);
        /*donate.setOnClickListener(new View.OnClickListener() {
            boolean click = true;

            @Override
            public void onClick(View v) {
                if (click) {
                    Toast.makeText(UserActivity.this, "Hello?", Toast.LENGTH_SHORT).show();
                    click = false;
                } else {
                    click = true;
                }
            }
        });
         */
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
//                          startActivity(new Intent(UserActivity.this, UserActivity.class));
                            break;
                        case R.id.nav_profile:
                            startActivity(new Intent(UserActivity.this, ProfileActivity.class));
                            break;
                        case R.id.nav_qrcode:
                            if (verified) {
                                startActivity(new Intent(UserActivity.this, createQRcode.class));
                                break;
                            } else {
                                Toast.makeText(UserActivity.this, "Please verify your account first!",
                                        Toast.LENGTH_LONG).show();
                            }
                        case R.id.nav_logout:
                            startActivity(new Intent(UserActivity.this, MainActivity.class));
                            break;
                    }
                    drawerLayout.closeDrawer(GravityCompat.START);
                    return true;
                }
            };

    /*
    // bottom action bar landing fragments
    private final BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @SuppressLint("NonConstantResourceId")
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    Fragment selectedFragment;

                    switch (item.getItemId()) {
                        case R.id.navigation_home:
                            startActivity(new Intent(UserActivity.this, UserActivity.class));
                            donate.show();
                            toolbar.setTitle("Home");
                            break;
                        case R.id.navigation_map:
                            selectedFragment = new MapFragment();
                            donate.hide();
                            toolbar.setTitle("Map");
                            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                                    selectedFragment).commit();
                            break;
                        case R.id.navigation_notifications:
                            selectedFragment = new NotificationsFragment();
                            donate.hide();
                            toolbar.setTitle("Notifications");
                            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                                    selectedFragment).commit();
                            break;
                        case R.id.navigation_messenger:
                            selectedFragment = new MessengerFragment();
                            donate.hide();
                            toolbar.setTitle("Messenger");
                            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                                    selectedFragment).commit();
                            break;
                        default:
                            throw new IllegalStateException("Unexpected value: " + item.getItemId());
                    }
                    return true;
                }
            };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.user, menu);
        return true;
    }
    */


    public void logout(MenuItem item) {
        startActivity(new Intent(UserActivity.this, MainActivity.class));
        preferences.clearData(this);
        finish();
    }
    public void qrcode(MenuItem item){
        if (verified) {
            startActivity(new Intent(UserActivity.this, createQRcode.class));
        }
    }

}