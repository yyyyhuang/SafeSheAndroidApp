package edu.northeastern.numad23team8;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import edu.northeastern.numad23team8.models.User;

//import edu.northeastern.numad23team8.models.User;

public class ChatView extends AppCompatActivity {
    EditText message;
    Button send;
    private static final String TAG = "CHATVIEW";
    Intent intent;
    private String enteredmessage;
    String receivername, sendername;
    private FirebaseAuth firebaseAuth;
    FirebaseDatabase firebaseDatabase;
    String senderroom, receiverroom;
    Calendar calendar;
    SimpleDateFormat simpleDateFormat;
    String currenttime;

    MessageAdapter messageAdapter;
    RecyclerView messagerc;
    ArrayList<Message> messageArrayList;

    ImageButton sticker0, sticker1, sticker2, sticker3, sticker4, sticker5;
    Integer countSticker0, countSticker1, countSticker2, countSticker3, countSticker4, countSticker5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_view);

//        message = findViewById(R.id.gchat_message);
//        send = findViewById(R.id.gchat_send);

        sticker0 = findViewById(R.id.sticker0);
        sticker1 = findViewById(R.id.sticker1);
        sticker2 = findViewById(R.id.sticker2);
        sticker3 = findViewById(R.id.sticker3);
        sticker4 = findViewById(R.id.sticker4);
        sticker5 = findViewById(R.id.sticker5);

        messageArrayList = new ArrayList<>();
        messagerc = findViewById(R.id.rcmessage);
        messagerc.setHasFixedSize(true);
        messageAdapter = new MessageAdapter(ChatView.this, messageArrayList);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        layoutManager.setStackFromEnd(true);
        messagerc.setLayoutManager(layoutManager);
        messagerc.setAdapter(messageAdapter);


        intent = getIntent();

        sendername = getIntent().getStringExtra("sendername");
        receivername= getIntent().getStringExtra("receivername");


        firebaseAuth=FirebaseAuth.getInstance();
        firebaseDatabase= FirebaseDatabase.getInstance();
        calendar = Calendar.getInstance();
        simpleDateFormat=new SimpleDateFormat("hh:mm a");

        senderroom = sendername+receivername;
        receiverroom = receivername+sendername;


        DatabaseReference databaseReference = firebaseDatabase.getReference().child("chats").child(senderroom).child("message");
        //messageAdapter=new MessageAdapter(ChatView.this, messageArrayList);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                messageArrayList.clear();
                for (DataSnapshot snapshot1:snapshot.getChildren()) {
                    String message = snapshot1.child("message").getValue(String.class);
                    String username = snapshot1.child("user").getValue(String.class);
                    String time = snapshot1.child("time").getValue(String.class);
                    //Message message1 = snapshot1.getValue(Message.class);
                    boolean belongstocurrent;
                    if(sendername.equals(username)) {
                        belongstocurrent=true;
                    } else{
                        belongstocurrent=false;
                    }
                    Message message1 = new Message(message, username, time,belongstocurrent);
                    messageArrayList.add(message1);
                }
                messageAdapter.notifyDataSetChanged();
                //messagerc.setAdapter(messageAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        // get sticker count



        databaseReference = firebaseDatabase.getReference().child("users");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot: snapshot.getChildren()){
                    User user = dataSnapshot.getValue(User.class);
                    // skip current user
                    if (user.getUsername().equals(sendername)) {
                        countSticker0 = user.getCount_0();
                        countSticker1 = user.getCount_1();
                        countSticker2 = user.getCount_2();
                        countSticker3 = user.getCount_3();
                        countSticker4 = user.getCount_4();
                        countSticker5 = user.getCount_5();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
//        databaseReference = firebaseDatabase.getReference().child("users").child(sendername);
//        databaseReference.child("users").child(sendername).addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                countSticker0 = snapshot.child("count_0").getValue(Integer.class);
//                countSticker1 = snapshot.child("count_1").getValue(Integer.class);
//                countSticker2 = snapshot.child("count_2").getValue(Integer.class);
//                countSticker3 = snapshot.child("count_3").getValue(Integer.class);
//                countSticker4 = snapshot.child("count_4").getValue(Integer.class);
//                countSticker5 = snapshot.child("count_5").getValue(Integer.class);
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });


        sticker0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Date date = new Date();
                currenttime = simpleDateFormat.format(calendar.getTime());
                Message message1 = new Message("sticker0", sendername, currenttime, true);

                firebaseDatabase = FirebaseDatabase.getInstance();
                firebaseDatabase.getReference().child("chats")
                        .child(senderroom).child("message")
                        .push()
                        .setValue(message1).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                firebaseDatabase.getReference().child("chats")
                                        .child(receiverroom).child("message")
                                        .push()
                                        .setValue(message1).addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {

                                            }
                                        });
//                                countSticker0 += 1;
//                                firebaseDatabase.getReference().child("users").child(sendername).child("count_0").setValue(2);
                            }
                        });
                countSticker0 += 1;
                firebaseDatabase.getReference().child("users").child(sendername).child("count_0").setValue(countSticker0);

            }
        });

        sticker1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                currenttime = simpleDateFormat.format(calendar.getTime());
                Message message1 = new Message("sticker1", sendername, currenttime, true);
                firebaseDatabase=FirebaseDatabase.getInstance();
                firebaseDatabase.getReference().child("chats")
                        .child(senderroom).child("message")
                        .push()
                        .setValue(message1).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                firebaseDatabase.getReference().child("chats")
                                        .child(receiverroom).child("message")
                                        .push()
                                        .setValue(message1).addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {

                                            }
                                        });
                            }
                        });
                countSticker1 += 1;
                firebaseDatabase.getReference().child("users").child(sendername).child("count_1").setValue(countSticker1);
            }
        });

        sticker2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                currenttime = simpleDateFormat.format(calendar.getTime());
                Message message1 = new Message("sticker2", sendername, currenttime, true);
                firebaseDatabase=FirebaseDatabase.getInstance();
                firebaseDatabase.getReference().child("chats")
                        .child(senderroom).child("message")
                        .push()
                        .setValue(message1).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                firebaseDatabase.getReference().child("chats")
                                        .child(receiverroom).child("message")
                                        .push()
                                        .setValue(message1).addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {

                                            }
                                        });
                            }
                        });
                countSticker2 += 1;
                firebaseDatabase.getReference().child("users").child(sendername).child("count_2").setValue(countSticker2);
            }
        });

        sticker3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                currenttime = simpleDateFormat.format(calendar.getTime());
                Message message1 = new Message("sticker3", sendername, currenttime, true);
                firebaseDatabase=FirebaseDatabase.getInstance();
                firebaseDatabase.getReference().child("chats")
                        .child(senderroom).child("message")
                        .push()
                        .setValue(message1).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                firebaseDatabase.getReference().child("chats")
                                        .child(receiverroom).child("message")
                                        .push()
                                        .setValue(message1).addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {

                                            }
                                        });
                            }
                        });
                countSticker3 += 1;
                firebaseDatabase.getReference().child("users").child(sendername).child("count_3").setValue(countSticker3);
            }
        });

        sticker4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                currenttime = simpleDateFormat.format(calendar.getTime());
                Message message1 = new Message("sticker4", sendername, currenttime, true);
                firebaseDatabase=FirebaseDatabase.getInstance();
                firebaseDatabase.getReference().child("chats")
                        .child(senderroom).child("message")
                        .push()
                        .setValue(message1).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                firebaseDatabase.getReference().child("chats")
                                        .child(receiverroom).child("message")
                                        .push()
                                        .setValue(message1).addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {

                                            }
                                        });
                            }
                        });
                countSticker4 += 1;
                firebaseDatabase.getReference().child("users").child(sendername).child("count_4").setValue(countSticker4);
            }
        });
        sticker5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                currenttime = simpleDateFormat.format(calendar.getTime());
                Message message1 = new Message("sticker5", sendername, currenttime, true);
                firebaseDatabase=FirebaseDatabase.getInstance();
                firebaseDatabase.getReference().child("chats")
                        .child(senderroom).child("message")
                        .push()
                        .setValue(message1).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                firebaseDatabase.getReference().child("chats")
                                        .child(receiverroom).child("message")
                                        .push()
                                        .setValue(message1).addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {

                                            }
                                        });
                            }
                        });
                countSticker5 += 1;
                firebaseDatabase.getReference().child("users").child(sendername).child("count_5").setValue(countSticker5);
            }
        });

    }

    @Override
    public void onStart() {
        super.onStart();
        messageAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (messageAdapter != null) {
            messageAdapter.notifyDataSetChanged();
        }
    }
}