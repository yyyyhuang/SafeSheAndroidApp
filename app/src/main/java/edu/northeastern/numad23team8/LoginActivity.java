package edu.northeastern.numad23team8;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import edu.northeastern.numad23team8.models.User;

public class LoginActivity extends AppCompatActivity {
    private static final String TAG = "LoginActivity";

    EditText userInput;
    Button loginButton;
    FirebaseDatabase firebasedatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        firebasedatabase = FirebaseDatabase.getInstance();



        userInput = findViewById(R.id.username);
        loginButton = (Button) findViewById(R.id.login_button);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = userInput.getText().toString();
                login(username);
            }
        });
    }

    private void login(String username){
        DatabaseReference userRef = firebasedatabase.getReference().child("users").child(username);
        ValueEventListener eventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (username.length() == 0) {
                    Toast.makeText(LoginActivity.this,"Username can't be empty",
                            Toast.LENGTH_SHORT).show();
                } else if(!dataSnapshot.exists()) {
                    //create new user
                    User new_user = new User(username, 0);
                    userRef.setValue(new_user);
                    startActivity(new Intent(LoginActivity.this, ContactActivity.class));
                }
                else {
                    startActivity(new Intent(LoginActivity.this, ContactActivity.class));
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d(TAG, databaseError.getMessage()); //Don't ignore errors!
            }
        };
        userRef.addListenerForSingleValueEvent(eventListener);
    }
}