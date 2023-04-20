package edu.northeastern.numad23team8;

import android.Manifest;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.SensorManager;
import android.location.Location;
import android.os.IBinder;
import android.telephony.SmsManager;

import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;

import com.github.tbouron.shakedetector.library.ShakeDetector;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;


public class ShakeToSendSMS extends Service {
    boolean isOn = false;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private SmsManager smsManager = SmsManager.getDefault();
    private String currLocation;

    private float accel = 10f;
    private float currAccel = SensorManager.GRAVITY_EARTH;
    private float lastAccel = SensorManager.GRAVITY_EARTH;

    @Override
    public void onCreate() {
        super.onCreate();
        FusedLocationProviderClient fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        if (ActivityCompat.checkSelfPermission(ShakeToSendSMS.this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

        }
        fusedLocationProviderClient.getLastLocation().addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if (location != null) {
                    location.getAltitude();
                    location.getLongitude();
                    currLocation = "http://maps.google.com/maps?q=loc:"+location.getLatitude()+","+location.getLongitude();
                }else {
                    currLocation = "Location invalid";
                }
            }
        });

        ShakeDetector.create(this, () -> sendSMS());

    }

    private void sendSMS() {
//        TODO: get emergency phone number
        smsManager.sendTextMessage("123456678", null,
                "EMERGENCY, here's my location" + currLocation, null, null);
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (intent.getAction().equalsIgnoreCase("STOP")) {
            if(isOn) {
                this.stopForeground(true);
                this.stopSelf();
            }
        } else {
            Intent notificationIntent = new Intent(this, MainActivity.class);
            PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent, PendingIntent.FLAG_IMMUTABLE);

            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                NotificationChannel channel = new NotificationChannel("SafeShe", "EMERGENCYNOTI", NotificationManager.IMPORTANCE_DEFAULT);

                NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                manager.createNotificationChannel(channel);

                Notification notification = new Notification.Builder(this, "SafeShe")
                        .setContentTitle("SafeShe")
                        .setContentText("Shake to Send SOS MESSAGE")
                        .setSmallIcon(R.drawable.dog)
                        .setContentIntent(pendingIntent)
                        .build();
                this.startForeground(100, notification);
                isOn = true;
                return START_NOT_STICKY;
            }
        }

        return super.onStartCommand(intent,flags,startId);
    }



}
