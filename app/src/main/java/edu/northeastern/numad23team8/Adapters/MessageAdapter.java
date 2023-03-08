package edu.northeastern.numad23team8.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import edu.northeastern.numad23team8.R;
import edu.northeastern.numad23team8.models.Message;

public class MessageAdapter extends RecyclerView.Adapter {
    private Context context;
    private ArrayList<Message> messageList;
    private static final int ITEM_SEND = 1;
    private static final int ITEM_RECEIVE = 2;
    //private String sendername, receivername;
    public MessageAdapter (Context context, ArrayList<Message> messageList) {
        this.context = context;
        this.messageList = messageList;
//        this.sendername = sendername;
//        this.receivername=receivername;
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
        Class<? extends RecyclerView.ViewHolder> aClass = holder.getClass();
        if (SenderViewHolder.class.equals(aClass)) {
            ((SenderViewHolder) holder).bind(message);
        } else if (ReceiverViewHolder.class.equals(aClass)) {
            ((ReceiverViewHolder) holder).bind(message);
        }
    }

    @Override
    public int getItemViewType( int position) {
        //Toast.makeText(context, "sendername1"+sendername, Toast.LENGTH_SHORT).show();

        Message message = messageList.get(position);
        //System.out.println("sendername"+sendername+"message user"+message.getUser());

        if (message.getBelongstocurrent()==true) {
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
        ImageView messagetext;
        TextView  messagetime;

        public SenderViewHolder(@NonNull View itemView) {
            super(itemView);

            messagetext = (ImageView) itemView.findViewById(R.id.gchat_message1);
            messagetime = (TextView) itemView.findViewById(R.id.gchat_time1);
        }

        void bind(Message message) {
            if (message.getMessage().equals("sticker0")) {
                messagetext.setImageResource(R.drawable.sticker0);
            }else if(message.getMessage().equals("sticker1")) {
                messagetext.setImageResource(R.drawable.sticker1);
            }else if(message.getMessage().equals("sticker2")) {
                messagetext.setImageResource(R.drawable.sticker2);
            }else if(message.getMessage().equals("sticker3")) {
                messagetext.setImageResource(R.drawable.sticker3);
            }else if(message.getMessage().equals("sticker4")) {
                messagetext.setImageResource(R.drawable.sticker4);
            }else if(message.getMessage().equals("sticker5")) {
                messagetext.setImageResource(R.drawable.sticker5);
            }else{
                messagetext.setImageResource(R.drawable.ic_launcher_foreground);
            }
            messagetime.setText(message.getTime());
        }
    }

    private class ReceiverViewHolder extends RecyclerView.ViewHolder {
        ImageView messagetext;
        TextView messagetime;

        public ReceiverViewHolder(@NonNull View itemView) {
            super(itemView);

            messagetext = (ImageView) itemView.findViewById(R.id.gchat_message2);
            messagetime = (TextView) itemView.findViewById(R.id.gchat_time2);
        }

        void bind(Message message) {
            if (message.getMessage().equals("sticker0")) {
                messagetext.setImageResource(R.drawable.sticker0);
            }else if(message.getMessage().equals("sticker1")) {
                messagetext.setImageResource(R.drawable.sticker1);
            }else if(message.getMessage().equals("sticker2")) {
                messagetext.setImageResource(R.drawable.sticker2);
            }else if(message.getMessage().equals("sticker3")) {
                messagetext.setImageResource(R.drawable.sticker3);
            }else if(message.getMessage().equals("sticker4")) {
                messagetext.setImageResource(R.drawable.sticker4);
            }else if(message.getMessage().equals("sticker5")) {
                messagetext.setImageResource(R.drawable.sticker5);
            }else{
                messagetext.setImageResource(R.drawable.ic_launcher_foreground);
            }
            messagetime.setText(message.getTime());
        }
    }
}
