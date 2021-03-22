package com.example.foodbank2021;

import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.net.PlacesClient;

import static android.content.ContentValues.TAG;

public class MapActivity extends AppCompatActivity implements OnMapReadyCallback {

    private static final String KEY_LOCATION = "location";
    private static final String KEY_CAMERA_POSITION = "camera_position";
    private GoogleMap mMap;
    private CameraPosition cameraPosition;
    private Boolean mLocationPermissionsGranted = false;
    private PlacesClient placesClient;
    private Location lastKnownLocation;
    private FusedLocationProviderClient fusedLocationProviderClient;
    private static final int PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1;
    private final LatLng defaultLocation = new LatLng(49.26690516255745, -123.25011871186251);
    private static final int DEFAULT_ZOOM = 15;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Retrieve location and camera position from saved instance state.
        if (savedInstanceState != null) {
            lastKnownLocation = savedInstanceState.getParcelable(KEY_LOCATION);
            cameraPosition = savedInstanceState.getParcelable(KEY_CAMERA_POSITION);
        }

        setContentView(R.layout.activity_map);

        Places.initialize(getApplicationContext(), getString(R.string.google_api_key));
        placesClient = Places.createClient(this);
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

        // initialize map
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        assert mapFragment != null;
        mapFragment.getMapAsync(MapActivity.this);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        if (mMap != null) {
            outState.putParcelable(KEY_CAMERA_POSITION, mMap.getCameraPosition());
            outState.putParcelable(KEY_LOCATION, lastKnownLocation);
        }
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.mMap = googleMap;
        Toast.makeText(this, "Map is Ready", Toast.LENGTH_SHORT).show();
        Log.d(TAG, "onMapReady: ready");

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

        getLocationPermission();
        updateLocationUI();
        getDeviceLocation();
    }

    private void getDeviceLocation() {
        try {
            if (mLocationPermissionsGranted) {
                Task<Location> locationResult = fusedLocationProviderClient.getLastLocation();
                locationResult.addOnCompleteListener(this, new OnCompleteListener<Location>() {
                    @Override
                    public void onComplete(@NonNull Task<Location> task) {
                        if (task.isSuccessful()) {
                            // Set the map's camera position to the current location of the device.
                            lastKnownLocation = task.getResult();
                            if (lastKnownLocation != null) {
                                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(
                                        new LatLng(lastKnownLocation.getLatitude(),
                                                lastKnownLocation.getLongitude()), DEFAULT_ZOOM));
                            }
                        } else {
                            Log.d(TAG, "Current location is null. Using defaults.");
                            Log.e(TAG, "Exception: %s", task.getException());
                            mMap.moveCamera(CameraUpdateFactory
                                    .newLatLngZoom(defaultLocation, DEFAULT_ZOOM));
                            mMap.getUiSettings().setMyLocationButtonEnabled(false);
                        }
                    }
                });
            }
        } catch (SecurityException e)  {
            Log.e("Exception: %s", e.getMessage(), e);
        }
    }

    private BitmapDescriptor BitmapFromVector(Context context, int vectorResId) {
        Drawable vectorDrawable = ContextCompat.getDrawable(context, vectorResId);
        vectorDrawable.setBounds(0, 0, vectorDrawable.getIntrinsicWidth(), vectorDrawable.getIntrinsicHeight());

        Bitmap bitmap = Bitmap.createBitmap(vectorDrawable.getIntrinsicWidth(), vectorDrawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        vectorDrawable.draw(canvas);

        return BitmapDescriptorFactory.fromBitmap(bitmap);
    }

    private void getLocationPermission() {
        if (ContextCompat.checkSelfPermission(this.getApplicationContext(),
                android.Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            mLocationPermissionsGranted = true;
        } else {
            ActivityCompat.requestPermissions(this,
                    new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                    PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        mLocationPermissionsGranted = false;
        switch (requestCode) {
            case PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    mLocationPermissionsGranted = true;
                }
            }
        }
        updateLocationUI();
    }

    private void updateLocationUI() {
        if (mMap == null) {
            return;
        }
        try {
            if (mLocationPermissionsGranted) {
                mMap.setMyLocationEnabled(true);
                mMap.getUiSettings().setMyLocationButtonEnabled(true);
            } else {
                mMap.setMyLocationEnabled(false);
                mMap.getUiSettings().setMyLocationButtonEnabled(false);
                lastKnownLocation = null;
                getLocationPermission();
            }
        } catch (SecurityException e)  {
            Log.e("Exception: %s", e.getMessage());
        }
    }
}