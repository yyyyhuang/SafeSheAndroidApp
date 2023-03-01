package edu.northeastern.numad23team8;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

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
    TextView countView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);

        Bundle bundle = getIntent().getExtras();
        String username = bundle.getString("username");

        firebasedatabase = FirebaseDatabase.getInstance();
        usersList = new ArrayList<>();
        countView = findViewById(R.id.count);

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
                        String msg = "Total stickers sent: " + user.getCount();
                        countView.setText(msg);
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
        userAdapter = new UserAdapter(ContactActivity.this, usersList);
        recyclerView.setAdapter(userAdapter);
    }
}