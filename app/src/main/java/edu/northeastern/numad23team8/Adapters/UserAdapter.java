package edu.northeastern.numad23team8.Adapters;

import static androidx.core.content.ContextCompat.startActivity;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import java.util.ArrayList;

import edu.northeastern.numad23team8.ContactActivity;
import edu.northeastern.numad23team8.R;
import edu.northeastern.numad23team8.ChatView;
import edu.northeastern.numad23team8.models.User;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.ViewHolder> {

    Context contactActivity;
    private ArrayList<User> usersList;
    String username;


    public UserAdapter(Context contactActivity, ArrayList<User> usersList, String username) {
        this.contactActivity = contactActivity;
        this.usersList = usersList;
        this.username=username;
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

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), ChatView.class);
                String receivername = user.getUsername();
                intent.putExtra("receivername", receivername);
                intent.putExtra("sendername", username);
                view.getContext().startActivity(intent);
            }
        });

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