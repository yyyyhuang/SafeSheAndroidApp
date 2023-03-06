package edu.northeastern.numad23team8;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import edu.northeastern.numad23team8.Adapters.UserAdapter;
import edu.northeastern.numad23team8.models.User;

public class ContactActivity extends AppCompatActivity {

    FirebaseDatabase firebasedatabase;

    ArrayList<User> usersList;
    UserAdapter userAdapter;
    RecyclerView recyclerView;
    TextView count_0View;
    TextView count_1View;
    TextView count_2View;
    TextView count_3View;
    TextView count_4View;
    TextView count_5View;
    TextView user_View;
    Boolean isFirstOpen = false;
    Boolean isNotificationSend = false;

    String[] users;
    ArrayList<String> friends;
    Calendar calendar;
    SimpleDateFormat simpleDateFormat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        createNotificationChannel();
        setContentView(R.layout.activity_contact);

        Bundle bundle = getIntent().getExtras();
        String username = bundle.getString("username");

        users = new String[]{"test1", "xingbi", "yaqun", "jingjie"};
        friends = new ArrayList<>();
        for (int i = 0; i < users.length; i++) {
            if (!users[i].equals(username)) {
                friends.add(username+users[i]);
            }
        }
        calendar = Calendar.getInstance();
        simpleDateFormat = new SimpleDateFormat("hh:mm a");

        firebasedatabase = FirebaseDatabase.getInstance();
        usersList = new ArrayList<>();
        count_0View = findViewById(R.id.count_0);
        count_1View = findViewById(R.id.count_1);
        count_2View = findViewById(R.id.count_2);
        count_3View = findViewById(R.id.count_3);
        count_4View = findViewById(R.id.count_4);
        count_5View = findViewById(R.id.count_5);
        user_View = findViewById(R.id.curr_user);

        DatabaseReference userRef = firebasedatabase.getReference().child("users");
        userRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                usersList.clear();
                for(DataSnapshot dataSnapshot: snapshot.getChildren()){
                    User user = dataSnapshot.getValue(User.class);
                    // skip current user
                    if (!user.getUsername().equals(username)) {
                        usersList.add(user);
                    }
                    else {

                        String msg0 = "Total sticker0 sent: " + user.getCount_0();
                        count_0View.setText(msg0);

                        String msg1 = "Total sticker1 sent: " + user.getCount_1();
                        count_1View.setText(msg1);

                        String msg2 = "Total sticker2 sent: " + user.getCount_2();
                        count_2View.setText(msg2);

                        String msg3 = "Total sticker3 sent: " + user.getCount_3();
                        count_3View.setText(msg3);

                        String msg4 = "Total sticker4 sent: " + user.getCount_4();
                        count_4View.setText(msg4);

                        String msg5 = "Total sticker5 sent: " + user.getCount_5();
                        count_5View.setText(msg5);
                        // Log.d("count: ", String.valueOf(user.getCount()));
                        user_View.setText("User: " + username);
                    }
                }
                userAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        DatabaseReference notificationRef1 = firebasedatabase.getReference().child("chats").child(friends.get(0)).child("message");
//        notificationRef1.addChildEventListener(new ChildEventListener() {
//            @Override
//            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
//                Message newMessage = snapshot.getValue(Message.class);
//                if (!newMessage.getUser().equals(username)) {
//                    sendNotification();
//                }
//            }
//            @Override
//            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {}
//            @Override
//            public void onChildRemoved(@NonNull DataSnapshot snapshot) {}
//            @Override
//            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {}
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {}
//        });
        notificationRef1.limitToLast(1).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot sp : snapshot.getChildren()){

                    if (snapshot != null && sp.child("time").getValue().toString().equals(simpleDateFormat.format(calendar.getTime()))
                    && !sp.child("user").getValue().toString().equals(username)){
//                        str.replaceAll(word, "");
                        sendNotification(sp.child("user").getValue().toString().replaceAll(username,""));
                    }

                }


            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        DatabaseReference notificationRef2 = firebasedatabase.getReference().child("chats").child(friends.get(1)).child("message");
        notificationRef2.limitToLast(1).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot sp : snapshot.getChildren()){

                    if (snapshot != null && sp.child("time").getValue().toString().equals(simpleDateFormat.format(calendar.getTime()))
                            && !sp.child("user").getValue().toString().equals(username)){
//                        sendNotification();
                        sendNotification(sp.child("user").getValue().toString().replaceAll(username,""));
                    }


                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        DatabaseReference notificationRef3 = firebasedatabase.getReference().child("chats").child(friends.get(2)).child("message");
        notificationRef3.limitToLast(1).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot sp : snapshot.getChildren()){

                    if (snapshot != null && sp.child("time").getValue().toString().equals(simpleDateFormat.format(calendar.getTime()))
                            && !sp.child("user").getValue().toString().equals(username)){
//                        sendNotification();
                        sendNotification(sp.child("user").getValue().toString().replaceAll(username,""));
                    }


                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        recyclerView = findViewById(R.id.contact_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        userAdapter = new UserAdapter(ContactActivity.this, usersList, username);
        recyclerView.setAdapter(userAdapter);


    }

    public void createNotificationChannel() {
        // This must be called early because it must be called before a notification is sent.
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "Chat Notifcation";
            String description = "Someone send you a new notification";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel("stick it", name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    public void sendNotification(String sender){
        // Prepare intent which is triggered if the
        // notification is selected
        Intent intent = new Intent(this, ChatView.class);

//        PendingIntent pIntent = PendingIntent.getActivity(this, (int) System.currentTimeMillis(), intent, 0);
        PendingIntent pendingIntent;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            pendingIntent = PendingIntent.getActivity(this,
                    0, intent, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);

        }else {
            pendingIntent = PendingIntent.getActivity(this,
                    0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        }



        // Build notification
        // Actions are just fake
        String channelId = "stick it";

//        Notification noti = new Notification.Builder(this)   DEPRECATED
        Notification noti = new NotificationCompat.Builder(this,channelId)
//        BitmapFactory.decodeResource(getResources(), R.mipmap.ic_message)
                .setContentTitle(sender)
                .setContentText("A sticker message")
                .setSmallIcon(R.drawable.sticker0)
                .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher_team8))
//                .setContentIntent(pendingIntent)
                .build();
//                .addAction(R.drawable.sticker0, "Call", callIntent).setContentIntent(pIntent).build();
//                .addAction(R.drawable.icon, "More", pIntent)
//              .addAction(R.drawable.icon, "And more", pIntent).build();

        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        // hide the notification after its selected
        noti.flags |= Notification.FLAG_AUTO_CANCEL ;

        notificationManager.notify(0, noti);
    }
}