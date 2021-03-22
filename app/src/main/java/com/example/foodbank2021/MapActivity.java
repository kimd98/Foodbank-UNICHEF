package com.example.foodbank2021;

import android.Manifest;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapActivity extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    private static final String TAG = "MapActivity";
    private static final String FINE_LOCATION = Manifest.permission.ACCESS_FINE_LOCATION;
    private static final String COURSE_LOCATION = Manifest.permission.ACCESS_COARSE_LOCATION;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1234;

    private Boolean mLocationPermissionsGranted = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        // initialize map
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        assert mapFragment != null;
        mapFragment.getMapAsync(MapActivity.this);
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // add LatLng locations
        // https://developers.google.com/maps/documentation/javascript/examples/event-click-latlng
        LatLng UBC = new LatLng(49.26690516255745, -123.25011871186251);
        LatLng vancouver_dt = new LatLng( 49.28179088593302, -123.12234495643217);
        LatLng dunbar = new LatLng( 49.2348974897013, -123.18440054420073);
        LatLng east_vancouver = new LatLng(49.25541932402888, -123.07366286281187);
        LatLng metrotown = new LatLng( 49.226672354432175, -123.00435458186705);
        LatLng SFU = new LatLng(49.277609627922764, -122.9095097796807);
        LatLng coquitlam = new LatLng(49.27802544568097,-122.80188136275119);
        LatLng richmond = new LatLng(49.175083486227386, -123.1321575516143);
        LatLng surrey = new LatLng(49.18538973916381, -122.84677546093297);

        // add foodbank markers
        mMap.addMarker(new MarkerOptions().position(UBC).title("Foodbank-UBC")
                .icon(BitmapFromVector(getApplicationContext(), R.drawable.foodbank)));
        mMap.addMarker(new MarkerOptions().position(vancouver_dt).title("Foodbank-Vancouver Downtown")
                .icon(BitmapFromVector(getApplicationContext(), R.drawable.foodbank)));
        mMap.addMarker(new MarkerOptions().position(dunbar).title("Foodbank-Dunbar")
                .icon(BitmapFromVector(getApplicationContext(), R.drawable.foodbank)));
        mMap.addMarker(new MarkerOptions().position(east_vancouver).title("Foodbank-East Vancouver")
                .icon(BitmapFromVector(getApplicationContext(), R.drawable.foodbank)));
        mMap.addMarker(new MarkerOptions().position(metrotown).title("Foodbank-Metrotown")
                .icon(BitmapFromVector(getApplicationContext(), R.drawable.foodbank)));
        mMap.addMarker(new MarkerOptions().position(SFU).title("Foodbank-SFU")
                .icon(BitmapFromVector(getApplicationContext(), R.drawable.foodbank)));
        mMap.addMarker(new MarkerOptions().position(coquitlam).title("Foodbank-Coquitlam")
                .icon(BitmapFromVector(getApplicationContext(), R.drawable.foodbank)));
        mMap.addMarker(new MarkerOptions().position(surrey).title("Foodbank-Surrey")
                .icon(BitmapFromVector(getApplicationContext(), R.drawable.foodbank)));
        mMap.addMarker(new MarkerOptions().position(richmond).title("Foodbank-Richmond")
                .icon(BitmapFromVector(getApplicationContext(), R.drawable.foodbank)));

        // move to the current location
        mMap.moveCamera(CameraUpdateFactory.newLatLng(UBC));
    }

    private BitmapDescriptor BitmapFromVector(Context context, int vectorResId) {
        Drawable vectorDrawable = ContextCompat.getDrawable(context, vectorResId);
        vectorDrawable.setBounds(0, 0, vectorDrawable.getIntrinsicWidth(), vectorDrawable.getIntrinsicHeight());

        Bitmap bitmap = Bitmap.createBitmap(vectorDrawable.getIntrinsicWidth(), vectorDrawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        vectorDrawable.draw(canvas);

        return BitmapDescriptorFactory.fromBitmap(bitmap);
    }

    /*
    private void getLocationPermission() {
        Log.d(TAG, "getting location permission");
        String[] permissions = {Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION};

        if (ContextCompat.checkSelfPermission(this.getApplicationContext(),
                FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            if (ContextCompat.checkSelfPermission(this.getApplicationContext(),
                    COURSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                mLocationPermissionsGranted = true;
            } else {
                ActivityCompat.requestPermissions(this, permissions, LOCATION_PERMISSION_REQUEST_CODE);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        mLocationPermissionsGranted = false;
        Log.d(TAG, "onRequestPermissionsResult: called");

        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0) {
                for (int i = 0; i < grantResults.length; i++) {
                    if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                        mLocationPermissionsGranted = false;
                        Log.d(TAG, "permission failed");
                        break;
                    }
                }
                mLocationPermissionsGranted = true;
                Log.d(TAG, "permission granted");

                // initialize map
                initMap();
            }
        }
    }*/
}