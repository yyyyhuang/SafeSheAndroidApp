package edu.northeastern.numad23team8;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MessageAdapter extends RecyclerView.Adapter {
    private Context context;
    private ArrayList<Message> messageList;
    private static final int ITEM_SEND = 1;
    private static final int ITEM_RECEIVE = 2;
    private String sendername, receivername;
    public MessageAdapter (Context context, ArrayList<Message> messageList, String sendername, String receivername) {
        this.context = context;
        this.messageList = messageList;
        this.sendername = sendername;
        this.receivername=receivername;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType==ITEM_SEND) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_chat1, parent, false);
            return new SenderViewHolder(view);
        }
        else{
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_chat2, parent, false);
            return new ReceiverViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Message message = (Message) messageList.get(position);
        switch (holder.getItemViewType()) {
            case ITEM_SEND:
                ((SenderViewHolder) holder).bind(message);
                break;
            case ITEM_RECEIVE:
                ((ReceiverViewHolder) holder).bind(message);
        }
    }

    @Override
    public int getItemViewType( int position) {
        Message message = messageList.get(position);
        if(message.getUser().equals(sendername)) {
            return ITEM_SEND;
        } else {
            return ITEM_RECEIVE;
        }
    }

    @Override
    public int getItemCount(){
        return messageList.size();
    }

    private class SenderViewHolder extends RecyclerView.ViewHolder {
        TextView messagetext, messagetime;

        public SenderViewHolder(@NonNull View itemView) {
            super(itemView);

            messagetext = (TextView) itemView.findViewById(R.id.gchat_message1);
            messagetime = (TextView) itemView.findViewById(R.id.gchat_time1);
        }

        void bind(Message message) {
            messagetext.setText(message.getMessage());
            messagetime.setText(message.getTime());
        }
    }

    private class ReceiverViewHolder extends RecyclerView.ViewHolder {
        TextView messagetext, messagetime;

        public ReceiverViewHolder(@NonNull View itemView) {
            super(itemView);

            messagetext = (TextView) itemView.findViewById(R.id.gchat_message2);
            messagetime = (TextView) itemView.findViewById(R.id.gchat_time2);
        }

        void bind(Message message) {
            messagetext.setText(message.getMessage());
            messagetime.setText(message.getTime());
        }
    }
}
