package edu.northeastern.numad23team8;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class NumberActivity extends AppCompatActivity {
    private TextView curnumber;
    private ImageButton update;
    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;
    private String userKey;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_number);

        curnumber = findViewById(R.id.txt_number);
        update = findViewById(R.id.btn_update);

        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        userKey = user.getUid();
        mDatabase = FirebaseDatabase.getInstance().getReference("accounts/"+userKey+"/emergency_contact");


//        mDatabase.child("accounts").child(userKey).child("emergency_contact").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
//            @Override
//            public void onComplete(@NonNull Task<DataSnapshot> task) {
//                curnumber.setText(String.valueOf(task.getResult().getValue()));
//            }
//        });

        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                Toast.makeText(NumberActivity.this,""+snapshot,Toast.LENGTH_SHORT).show();
                curnumber.setText(String.valueOf(snapshot.getValue()));
                curnumber.setTextColor(Color.WHITE);
                curnumber.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20f);
                curnumber.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                curnumber.setGravity(Gravity.CENTER);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                PopupMenu(view);
                startActivity(new Intent(NumberActivity.this,RegisterNumberActivity.class));
            }
        });
    }
//
//    private void PopupMenu(View view) {
//        PopupMenu popupMenu = new PopupMenu(NumberActivity.this,view);
//        popupMenu.getMenuInflater().inflate(R.menu.popup, popupMenu.getMenu());
//        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
//            @Override
//            public boolean onMenuItemClick(MenuItem menuItem) {
//
//                startActivity(new Intent(NumberActivity.this,RegisterNumberActivity.class));
//
//                return true;
//            }
//        });
//        popupMenu.show();
//    }
}