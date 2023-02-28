package edu.northeastern.numad23team8.Adapters;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import edu.northeastern.numad23team8.R;
import edu.northeastern.numad23team8.models.User;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.ViewHolder> {

    Context contactActivity;
    private ArrayList<User> usersList;

    public UserAdapter(Context contactActivity, ArrayList<User> usersList) {
        this.contactActivity = contactActivity;
        this.usersList = usersList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(contactActivity).inflate(R.layout.user_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        User user = usersList.get(position);
        holder.usernameView.setText(user.getUsername());

        //TODO: implement onclick to chatActivity

    }

    @Override
    public int getItemCount() {
        return usersList.size();
    }


    static class ViewHolder extends RecyclerView.ViewHolder {

        TextView usernameView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            usernameView = itemView.findViewById(R.id.username);
        }
    }




}