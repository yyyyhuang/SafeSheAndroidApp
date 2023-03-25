package edu.northeastern.numad23team8;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;

public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        new CountDownTimer(2000, 500){
            @Override
            public void onTick(long millsUntilFinished){}

            @Override
            public void onFinish(){
                startActivity(new Intent(SplashScreen.this, LaunchActivity.class));
                SplashScreen.this.finish();
            }
        }.start();
    }
}