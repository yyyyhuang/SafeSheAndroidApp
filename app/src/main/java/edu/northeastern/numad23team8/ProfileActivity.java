package edu.northeastern.numad23team8;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import edu.northeastern.numad23team8.Adapters.FriendAdapter;
import edu.northeastern.numad23team8.Adapters.UserAdapter;
import edu.northeastern.numad23team8.models.Friend;

public class ProfileActivity extends AppCompatActivity {

    private ImageView change_profile;
    private ImageView profile_img;
    private FloatingActionButton fab;
    private TextView name_view;

    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;
    private String userKey;
    private String url;
    private String name;

    private List<String> idList;
    private ArrayList<Friend> mUsers;
    private RecyclerView recyclerView;
    private FriendAdapter friendAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        profile_img = findViewById(R.id.profile_picture);
        fab = findViewById(R.id.addFriendFAB);
        name_view = findViewById(R.id.username);

        idList = new ArrayList<>();
        mUsers = new ArrayList<>();

        recyclerView = findViewById(R.id.friends_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        mDatabase = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        userKey = user.getUid();

        friendAdapter = new FriendAdapter(this, mUsers, userKey);
        recyclerView.setAdapter(friendAdapter);

        DatabaseReference ref = mDatabase.child("accounts").child(userKey);
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Friend user = snapshot.getValue(Friend.class);
                url = user.getImageUrl();
                name = user.getUsername();
                if(!url.equals(" ")){
                    Log.i("url", url);
                    Picasso.get()
                            .load(url)
                            .placeholder(R.mipmap.ic_launcher_team8)
                            .resize(150, 150)
                            .centerCrop()
                            .into(profile_img);
                }
                name_view.setText(name);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        change_profile = findViewById(R.id.edit_profile_picture_button);

        change_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ProfileActivity.this, ChangeImageActivity.class));
            }
        });


        getContacts();

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog();
            }
        });
    }

    private void getContacts(){
        mDatabase.child("contacts").child(userKey).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                idList.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                    idList.add(snapshot.getKey());
                }
                Log.d("idlist", idList.toString());
                showFriends();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void showFriends(){
        mDatabase.child("accounts").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                mUsers.clear();
                for(DataSnapshot snapshot: dataSnapshot.getChildren()){

                    String currentId = snapshot.getKey();
                    Friend friend = snapshot.getValue(Friend.class);

                    for (String id : idList) {
                        if (currentId.equals(id)) {
                            mUsers.add(friend);
                        }
                    }

                }
                // Log.d("list f", mUsers.toString());
                friendAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void dialog(){
        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog);

        EditText edEmail = dialog.findViewById(R.id.edit_email);
        Button saveEmail = dialog.findViewById(R.id.save);


        saveEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String friendEmail = edEmail.getText().toString();
                if(friendEmail.length() != 0) {
                    mDatabase.child("accounts").addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            for(DataSnapshot snapshot: dataSnapshot.getChildren()){
                                String currentId = snapshot.getKey();
                                Friend friend = snapshot.getValue(Friend.class);

                                if (friendEmail.equals(friend.getEmail())){
                                    mDatabase.child("contacts").child(userKey).child(currentId).setValue(true);
                                    mDatabase.child("contacts").child(currentId).child(userKey).setValue(true);
                                    dialog.dismiss();
                                    mUsers.add(friend);
                                    friendAdapter.notifyDataSetChanged();
                                    Snackbar succeed = Snackbar.make(recyclerView, "Friend added", Snackbar.LENGTH_LONG);
                                    succeed.show();
                                    return;
                                }
                            }
                            Snackbar fail = Snackbar.make(recyclerView, "User doesn't exit", Snackbar.LENGTH_LONG);
                            fail.show();
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }
            }
        });
        dialog.show();

    }




}



