package edu.northeastern.numad23team8;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

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



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);

        Bundle bundle = getIntent().getExtras();
        String username = bundle.getString("username");

        firebasedatabase = FirebaseDatabase.getInstance();
        usersList = new ArrayList<>();
        count_0View = findViewById(R.id.count_0);
        count_1View = findViewById(R.id.count_1);
        count_2View = findViewById(R.id.count_2);
        count_3View = findViewById(R.id.count_3);
        count_4View = findViewById(R.id.count_4);
        count_5View = findViewById(R.id.count_5);

        DatabaseReference userRef = firebasedatabase.getReference().child("users");
        userRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
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
                    }
                }
                userAdapter.notifyDataSetChanged();
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
}