package edu.northeastern.numad23team8;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.os.Looper;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

public class MapsActivity extends AppCompatActivity {
    SupportMapFragment supportMapFragment;
    FusedLocationProviderClient client;
    LocationRequest locationRequest;
    LocationCallback locationCallback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        supportMapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.maps);
        client = LocationServices.getFusedLocationProviderClient(this);
        locationRequest = new LocationRequest();
        locationRequest.setInterval(5000);
        locationRequest.setFastestInterval(2000);
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        locationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(@NonNull LocationResult locationResult) {
                super.onLocationResult(locationResult);
                if (locationResult == null) {
                    return;
                }
                for (Location location : locationResult.getLocations()) {
                    updateMap(location);
                }
//                if (locationResult != null) {
//                    Location location = locationResult.getLastLocation();
//                    if (location != null){
//                        supportMapFragment.getMapAsync(new OnMapReadyCallback() {
//                            @Override
//                            public void onMapReady(@NonNull GoogleMap googleMap) {
//                                LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
//                                MarkerOptions markerOptions = new MarkerOptions().position(latLng).title("I am here!");
//                                googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng,10));
//                                googleMap.addMarker(markerOptions);
//                            }
//                        });
//                    }
//                }
            }
        };

        if (ActivityCompat.checkSelfPermission(MapsActivity.this, android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            //permission granted, call location
            startLocationUpdates();
        } else {
            // request permission
            ActivityCompat.requestPermissions(MapsActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 44);
        }
    }

    private void startLocationUpdates() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        client.requestLocationUpdates(locationRequest, locationCallback, Looper.getMainLooper());
    }

    private void stopLocationUpdates() {
        client.removeLocationUpdates(locationCallback);
    }

    private void updateMap(Location location) {
        supportMapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(@NonNull GoogleMap googleMap) {
                LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
                MarkerOptions markerOptions = new MarkerOptions().position(latLng).title("I am here!");
                googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng,10));
                googleMap.addMarker(markerOptions);
            }
        });
    }

    private void getCurrentLocation() {

        @SuppressLint("MissingPermission") Task<Location> task = client.getLastLocation();


//        task.addOnSuccessListener(new OnSuccessListener<Location>() {
//            @Override
//            public void onSuccess(Location location) {
//                if(location != null){
//                    supportMapFragment.getMapAsync(new OnMapReadyCallback() {
//                        @Override
//                        public void onMapReady(@NonNull GoogleMap googleMap) {
//                            LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
//                            //create marker
//                            MarkerOptions markerOptions = new MarkerOptions().position(latLng).title("I am here!");
//                            //zoom map
//                            googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng,10));
//                            googleMap.addMarker(markerOptions);
//                        }
//                    });
//                }
//            }
//        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 44) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                //permission granted
                //getCurrentLocation();
                startLocationUpdates();
            }
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        stopLocationUpdates();
    }

    @Override
    protected void onResume() {
        super.onResume();
        startLocationUpdates();
    }
}