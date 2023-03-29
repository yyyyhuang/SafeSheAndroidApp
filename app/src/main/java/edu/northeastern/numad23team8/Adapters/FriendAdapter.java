package edu.northeastern.numad23team8.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import edu.northeastern.numad23team8.R;
import edu.northeastern.numad23team8.models.Friend;


public class FriendAdapter extends RecyclerView.Adapter<FriendAdapter.ViewHolder> {

    Context profileActivity;
    private ArrayList<Friend> friendsList;
    String userKey;

    public FriendAdapter(Context profileActivity, ArrayList<Friend> friendsList, String userKey){
        this.profileActivity = profileActivity;
        this.userKey = userKey;
        this.friendsList = friendsList;
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
        // TODO: DETAIL IMAGEVIEW
        // Picasso.get()
        // .load(friend.getImageUrl())
        // .placeholder(R.drawable/ic_baseline_account_circle_24)
        // .resize(60,60)
        // .centerCrop()
        // .into(holder.userImg);

        //TODO: implement onclick to change friend as emergency contact
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

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
