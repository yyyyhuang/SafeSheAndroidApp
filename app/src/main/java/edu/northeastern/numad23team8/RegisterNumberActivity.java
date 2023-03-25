package edu.northeastern.numad23team8;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class RegisterNumberActivity extends AppCompatActivity {
    EditText number;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_number);

        number = findViewById(R.id.etENum);
    }

    public void saveNumber(View view){
        String numberString = number.getText().toString();
        if(numberString.length() == 10){
            SharedPreferences sharedPreferences = getSharedPreferences("MySharedRef", MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("ENUM", numberString);
            editor.apply();
            RegisterNumberActivity.this.finish();
        } else {
            Toast.makeText(this, "Invalid number!", Toast.LENGTH_SHORT).show();
        }
    }
}