package edu.northeastern.numad23team8;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class RegisterNumberActivity extends AppCompatActivity {
    private EditText number;
    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;
    private String userKey;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_number);

        number = findViewById(R.id.etENum);
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        userKey = user.getUid();
    }

    public void saveNumber(View view){
        String numberString = number.getText().toString();
        if(numberString.length() == 10){

//            SharedPreferences sharedPreferences = getSharedPreferences("MySharedRef", MODE_PRIVATE);
//            SharedPreferences.Editor editor = sharedPreferences.edit();
//            editor.putString("ENUM", numberString);
//            editor.apply();

            mDatabase.child("accounts").child(userKey).child("emergency_contact").setValue(Integer.valueOf(numberString));

            RegisterNumberActivity.this.finish();
        } else {
            Toast.makeText(this, "Invalid number!", Toast.LENGTH_SHORT).show();
        }
    }
}