package edu.northeastern.numad23team8.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import edu.northeastern.numad23team8.R;
import edu.northeastern.numad23team8.models.Friend;


public class FriendAdapter extends RecyclerView.Adapter<FriendAdapter.ViewHolder> {

    Context profileActivity;
    private ArrayList<Friend> friendsList;
    String first_name, last_name;

    public FriendAdapter(Context profileActivity, ArrayList<Friend> friendsList, String first_name, String last_name){
        this.profileActivity = profileActivity;
        this.first_name = first_name;
        this.friendsList = friendsList;
        this.last_name = last_name;
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
        holder.usernameView.setText(friend.getFirst_name() + " " + friend.getLast_name());

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

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            usernameView = itemView.findViewById(R.id.username);
        }
    }
}
