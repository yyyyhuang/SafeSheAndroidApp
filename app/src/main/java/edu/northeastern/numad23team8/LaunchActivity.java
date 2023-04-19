package edu.northeastern.numad23team8;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.Toast;
import android.widget.ToggleButton;

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

        profile = findViewById(R.id.profile);
        emergency_contact = findViewById(R.id.emergency_contact);
        track = findViewById(R.id.track);
        start = findViewById(R.id.start);

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

        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (start.isChecked()){
                    if (ActivityCompat.checkSelfPermission(LaunchActivity.this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                    && ContextCompat.checkSelfPermission(LaunchActivity.this, Manifest.permission.SEND_SMS) == PackageManager.PERMISSION_GRANTED) {
                        Intent smsIntent = new Intent(LaunchActivity.this, ShakeToSendSMS.class);
                        smsIntent.setAction("start");
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                            getApplicationContext().startForegroundService(smsIntent);
                            Toast.makeText(LaunchActivity.this,"Shake to send SOS message",
                                    Toast.LENGTH_SHORT).show();
                    }
                    }else {
                        ActivityCompat.requestPermissions(LaunchActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.SEND_SMS}, 44);
                    }
                }else {
                    Intent smsIntent = new Intent(LaunchActivity.this, ShakeToSendSMS.class);
                    smsIntent.setAction("STOP");
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//                        getApplicationContext().startForegroundService(smsIntent);
                        getApplicationContext().stopService(smsIntent);
                        Toast.makeText(LaunchActivity.this,"Service Stopped",
                                Toast.LENGTH_SHORT).show();
                    }

                }
            }
        });


    }

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