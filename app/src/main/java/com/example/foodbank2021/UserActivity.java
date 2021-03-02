package com.example.foodbank2021;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class UserActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private MenuItem item;
    Toolbar toolbar;
    private final String user_uid= FirebaseAuth.getInstance().getCurrentUser().getUid();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_activity_user);

        // fragment title tool bar
        toolbar = findViewById(R.id.toolbar);

        // instantiated landing page
        if (savedInstanceState == null) {
            toolbar.setTitle("Home");
            getSupportFragmentManager().beginTransaction()
                    .setReorderingAllowed(true)
                    .add(R.id.fragment_container, HomeFragment.class, null)
                    .commit();
        }

        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        BottomNavigationView navView = findViewById(R.id.bottom_nav);
        navView.setOnNavigationItemSelectedListener(navListener);

        /*
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.side_nav);
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_profile, R.id.nav_qrcode)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
         */
    }

    // bottom action bar landing fragments
    private final BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @SuppressLint("NonConstantResourceId")
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    Fragment selectedFragment = new HomeFragment();

                    switch (item.getItemId()) {
                        case R.id.navigation_home:
                            selectedFragment = new HomeFragment();
                            toolbar.setTitle("Home");
                            break;
                        case R.id.navigation_map:
                            selectedFragment = new MapFragment();
                            toolbar.setTitle("Map");
                            break;
                        case R.id.navigation_notifications:
                            selectedFragment = new NotificationsFragment();
                            toolbar.setTitle("Notifications");
                            break;
                        case R.id.navigation_messenger:
                            selectedFragment = new MessengerFragment();
                            toolbar.setTitle("Messenger");
                            break;
                        default:
                            throw new IllegalStateException("Unexpected value: " + item.getItemId());
                    }

                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                            selectedFragment).commit();
                    return true;
                }
            };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.user, menu);
        return true;
    }

    /*
    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }
     */

    public void logout(MenuItem item) {
        startActivity(new Intent(UserActivity.this, MainActivity.class));
        preferences.clearData(this);
        finish();
    }
    public void qrcode(MenuItem item){
        if (checkVerified(user_uid)) {
            startActivity(new Intent(UserActivity.this, createQRcode.class));
        }
    }

    public boolean checkVerified (String checkUid) {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Users");
        DatabaseReference userRef = databaseReference.child(user_uid);
        final boolean[] verified = {true};

        ValueEventListener valueEventListener = userRef.addValueEventListener(new ValueEventListener() {    // attach a listener to get "as" value
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.child("verified").getValue(String.class).equals("no")) {
                    Toast.makeText(UserActivity.this, "Please verify your account.", Toast.LENGTH_LONG).show();
                    verified[0] = false;
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // do nothing
            }
        });
        return verified[0];
    }
}