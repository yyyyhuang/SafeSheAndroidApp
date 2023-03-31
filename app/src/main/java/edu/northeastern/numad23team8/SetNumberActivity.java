package edu.northeastern.numad23team8;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SetNumberActivity extends AppCompatActivity {

    private EditText number;
    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;
    private String userKey;
    private Button save;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_number);

        number = findViewById(R.id.etNum);
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        userKey = user.getUid();
        save = findViewById(R.id.saveNumber);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveNumber(view);
            }
        });
    }

    public void saveNumber(View view){
        String numberString = number.getText().toString();
        if(numberString.length() == 10){

            mDatabase.child("accounts").child(userKey).child("number").setValue(Integer.valueOf(numberString));

            Intent intent
                    = new Intent(SetNumberActivity.this,
                    LaunchActivity.class);
            startActivity(intent);
            SetNumberActivity.this.finish();
        } else {
            Toast.makeText(this, "Invalid number!", Toast.LENGTH_SHORT).show();
        }
    }
}