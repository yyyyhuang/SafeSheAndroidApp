package edu.northeastern.numad23team8;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class LaunchActivity extends AppCompatActivity {
    private ImageView profile, emergency_contact;
    private Button track;
    private Button start;
    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;
    private String userKey;

    @Override
    protected void onResume() {
        super.onResume();
        // TODO: USE FIRBASE DATABASE TO CHECK
//        SharedPreferences sharedPreferences = getSharedPreferences("MySharedPref",MODE_PRIVATE);
//        String ENUM = sharedPreferences.getString("ENUM","NONE");
//        if(ENUM.equalsIgnoreCase("NONE")){
//            startActivity(new Intent(this,RegisterNumberActivity.class));
//        }
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        userKey = user.getUid();
        if(mDatabase.child("accounts").child(userKey).child("emergency_contact").equals(0)){
            startActivity(new Intent(this,RegisterNumberActivity.class));
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launch);

        profile = findViewById(R.id.profile);
        emergency_contact = findViewById(R.id.emergency_contact);
        track = findViewById(R.id.track);
        start = findViewById(R.id.start);

        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LaunchActivity.this, ProfileActivity.class));
            }
        });

        emergency_contact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupMenu(view);
            }
        });

    }

    public void PopupMenu(View view){
        PopupMenu popupMenu = new PopupMenu(LaunchActivity.this, view);
        popupMenu.getMenuInflater().inflate(R.menu.popup, popupMenu.getMenu());
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                if(menuItem.getItemId() == R.id.edit_contact){
                    // Toast.makeText(LaunchActivity.this, "You Clicked " + menuItem.getTitle(), Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(LaunchActivity.this,RegisterNumberActivity.class));
                }
                return true;
            }
        });
        popupMenu.show();
    }


}