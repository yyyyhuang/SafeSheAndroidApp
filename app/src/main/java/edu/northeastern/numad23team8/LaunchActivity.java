package edu.northeastern.numad23team8;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.Location;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LaunchActivity extends AppCompatActivity {
    private ImageView profile, emergency_contact;
    private Button track;
    private ToggleButton start;
    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;
    private String userKey;
    private long ENum;
    private float accel = 10f;
    private float currAccel = SensorManager.GRAVITY_EARTH;
    private float lastAccel = SensorManager.GRAVITY_EARTH;
    private boolean isSend = false;
    private String emergencyNum;
    private MediaPlayer mediaPlayer;

//    private TextView curr, last;

    @Override
    public void onResume(){
        super.onResume();
        // TODO: CHECK ENUM;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launch);


//        mDatabase = FirebaseDatabase.getInstance().getReference();
//        mAuth = FirebaseAuth.getInstance();
//        FirebaseUser user = mAuth.getCurrentUser();
//        userKey = user.getUid();
//        mDatabase.child("accounts").child(userKey).addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                ENum = (long) snapshot.child("emergency_contact").getValue();
//                Log.i("enum", String.valueOf(ENum));
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });
//        if(ENum == 0){startActivity(new Intent(LaunchActivity.this,RegisterNumberActivity.class));}

//        curr = findViewById(R.id.textcurr);
//        last = findViewById(R.id.textlast);

        profile = findViewById(R.id.profile);
        emergency_contact = findViewById(R.id.emergency_contact);
        track = findViewById(R.id.track);
        start = findViewById(R.id.start);

//        get emergency SMS number
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        userKey = user.getUid();
        mDatabase = FirebaseDatabase.getInstance().getReference("accounts/"+userKey+"/emergency_contact");
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                emergencyNum = String.valueOf(snapshot.getValue());
                Toast.makeText(LaunchActivity.this,"emergencyNum" + emergencyNum,Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        // set up sensor to detect device shake
        SensorManager sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        Sensor sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        SensorEventListener sensorEventListener = new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent sensorEvent) {
                if (sensorEvent != null) {
                    float x_val = sensorEvent.values[0];
                    float y_val = sensorEvent.values[1];
                    float z_val = sensorEvent.values[2];
                    currAccel = (float) Math.sqrt((double) (x_val * x_val) + (y_val * y_val) + (z_val * z_val));
                    float delta = Math.abs(currAccel - lastAccel);
                    lastAccel = currAccel;
                    accel = accel * 0.9f + delta;
                    if (accel > 12 && !isSend ) {
                        if (mediaPlayer == null) {
                            mediaPlayer = MediaPlayer.create(LaunchActivity.this, R.raw.emergency_alert);
                        }
                        mediaPlayer.start();
                        mediaPlayer.setLooping(true);

                        sendSMS();
                        Toast.makeText(LaunchActivity.this,"SMS Send",
                                Toast.LENGTH_SHORT).show();
                        isSend = true;
                    }
//                    curr.setText("curr: " + currAccel +" last" + lastAccel);
//                    last.setText("delta: " + accel);
                }
            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int i) {

            }
        };


        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (start.isChecked()){
                    if (ActivityCompat.checkSelfPermission(LaunchActivity.this, android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
                    && ContextCompat.checkSelfPermission(LaunchActivity.this, Manifest.permission.SEND_SMS) == PackageManager.PERMISSION_GRANTED) {
                        Toast.makeText(LaunchActivity.this,"Shake to send SOS message",
                                Toast.LENGTH_SHORT).show();
                        isSend = false;
                        sensorManager.registerListener(sensorEventListener, sensor, SensorManager.SENSOR_DELAY_NORMAL);
                    }else {
                        ActivityCompat.requestPermissions(LaunchActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.SEND_SMS}, 44);
                    }

                }else {
                    if (ActivityCompat.checkSelfPermission(LaunchActivity.this, android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
                            && ContextCompat.checkSelfPermission(LaunchActivity.this, Manifest.permission.SEND_SMS) == PackageManager.PERMISSION_GRANTED) {
                        sensorManager.unregisterListener(sensorEventListener, sensor);
                        Toast.makeText(LaunchActivity.this,"Service Stopped",
                                Toast.LENGTH_SHORT).show();
                        if (mediaPlayer != null) {
                            mediaPlayer.release();
                            mediaPlayer = null;
                        }
                    }else {
                        ActivityCompat.requestPermissions(LaunchActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.SEND_SMS}, 44);
                    }

                }
            }
        });

        track.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LaunchActivity.this,MapsActivity.class));
            }
        });

        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LaunchActivity.this, ProfileActivity.class));
            }
        });

        emergency_contact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LaunchActivity.this, NumberActivity.class));
            }
        });




    }
    private void sendSMS(){

//    private String currLocation;
        FusedLocationProviderClient fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        if (ActivityCompat.checkSelfPermission(LaunchActivity.this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(LaunchActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.SEND_SMS}, 44);
        }
        fusedLocationProviderClient.getLastLocation().addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                SmsManager smsManager = SmsManager.getDefault();
                String currLocation;
                if (location != null) {
                    location.getAltitude();
                    location.getLongitude();
                    currLocation = "http://maps.google.com/maps?q=loc:"+location.getLatitude()+","+location.getLongitude();
                }else {
                    currLocation = "Location invalid";
                }
                smsManager.sendTextMessage(emergencyNum, null,
                        "EMERGENCY, here's my location" + currLocation, null, null);
            }


        });
    }


//    @Override
//    protected void onStop() {
//        super.onStop();
//        if (mediaPlayer != null) {
//            mediaPlayer.release();
//            mediaPlayer = null;
//        }
//    }
//    public void PopupMenu(View view){
//        PopupMenu popupMenu = new PopupMenu(LaunchActivity.this, view);
//        popupMenu.getMenuInflater().inflate(R.menu.popup, popupMenu.getMenu());
//        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
//            @Override
//            public boolean onMenuItemClick(MenuItem menuItem) {
//                if(menuItem.getItemId() == R.id.edit_contact){
//                    // Toast.makeText(LaunchActivity.this, "You Clicked " + menuItem.getTitle(), Toast.LENGTH_SHORT).show();
//                    startActivity(new Intent(LaunchActivity.this,RegisterNumberActivity.class));
//                }
//                return true;
//            }
//        });
//        popupMenu.show();
//    }


}