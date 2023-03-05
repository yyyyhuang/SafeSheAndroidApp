package edu.northeastern.numad23team8;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

public class ChatView extends AppCompatActivity {
    EditText message;
    Button send;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_view);

        message = findViewById(R.id.gchat_message);
        send = findViewById(R.id.gchat_send);

        messageArrayList = new ArrayList<>();
        messagerc = findViewById(R.id.rcmessage);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setStackFromEnd(true);

        messagerc.setLayoutManager(layoutManager);
        messageAdapter = new MessageAdapter(ChatView.this, messageArrayList, sendername, receivername);
        messagerc.setAdapter((messageAdapter));

        firebaseAuth=FirebaseAuth.getInstance();
        firebaseDatabase= FirebaseDatabase.getInstance();
        calendar = Calendar.getInstance();
        simpleDateFormat=new SimpleDateFormat("hh:mm a");


        sendername = getIntent().getStringExtra("sendername");
        receivername=getIntent().getStringExtra("receivername");

        senderroom = sendername+receivername;
        receiverroom = receivername+sendername;


        DatabaseReference databaseReference = firebaseDatabase.getReference().child("chat").child(senderroom).child("message");
        messageAdapter=new MessageAdapter(ChatView.this, messageArrayList, sendername, receivername);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                messageArrayList.clear();
                for (DataSnapshot snapshot1:snapshot.getChildren()) {
                    Message message1 = snapshot1.getValue(Message.class);
                    messageArrayList.add(message1);
                }
                messageAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                enteredmessage = message.getText().toString();
                if (enteredmessage.isEmpty()){
                    Toast.makeText(getApplicationContext(), "entermessage", Toast.LENGTH_SHORT).show();
                }else{
                    Date date = new Date();
                    currenttime = simpleDateFormat.format(calendar.getTime());
                    User user = new User();
                    Message message1 = new Message(enteredmessage, sendername, currenttime);
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
                    message.setText(null);
                }
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