package edu.northeastern.numad23team8.Adapters;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import edu.northeastern.numad23team8.R;
import edu.northeastern.numad23team8.models.Friend;


public class FriendAdapter extends RecyclerView.Adapter<FriendAdapter.ViewHolder> {

    Context profileActivity;
    private ArrayList<Friend> friendsList;
    String userKey;

    private DatabaseReference mDatabase;


    public FriendAdapter(Context profileActivity, ArrayList<Friend> friendsList, String userKey){
        this.profileActivity = profileActivity;
        this.userKey = userKey;
        this.friendsList = friendsList;

        mDatabase = FirebaseDatabase.getInstance().getReference();

    }

    @NonNull
    @Override
    public FriendAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(profileActivity).inflate(R.layout.friend_item, parent, false);
        return new FriendAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FriendAdapter.ViewHolder holder, int position) {
        Friend friend = friendsList.get(position);
        holder.usernameView.setText(friend.getUsername());
        if(!friend.getImageUrl().equals(" ")){
            Picasso.get()
                    .load(friend.getImageUrl())
                    .placeholder(R.drawable.ic_baseline_account_circle_24)
                    .resize(60,60)
                    .centerCrop()
                    .into(holder.userImg);
        }


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDatabase.child("accounts").child(userKey).child("emergency_contact").setValue(friend.getNumber());
                Toast.makeText(profileActivity, "EMERGENCY CONTACT UPDATED!", Toast.LENGTH_SHORT).show();
            }
        });

    }





    @Override
    public int getItemCount() {
        return friendsList.size();
    }




    static class ViewHolder extends RecyclerView.ViewHolder {

        TextView usernameView;
        ImageView userImg;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            usernameView = itemView.findViewById(R.id.username);
            userImg = itemView.findViewById(R.id.user_img);
        }
    }
}
