package com.palmsolutions.farmconnect;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class All_Chats_Recycler_View extends RecyclerView.Adapter<All_Chats_Recycler_View.ViewHolder> {
    private List<Message> chatList;
    private Context context;
    private FirebaseAuth auth;
    private FirebaseUser user;
    private DatabaseReference database;
    private FragmentManager manager;

    public All_Chats_Recycler_View(Context context, FragmentManager manager){
        this.context = context;
        this.manager = manager;
        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        database = FirebaseDatabase.getInstance().getReference();
        chatList = new ArrayList<>();
        fetch_all_chats_from_database();
    }
    @NonNull
    @Override
    public All_Chats_Recycler_View.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.chat_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull All_Chats_Recycler_View.ViewHolder holder, int position) {
        if (position < chatList.size()) {
            Message message = chatList.get(position);
            if (message != null) {
                Long timeStamp = message.getTimeStamp();
                Date date = new Date(timeStamp);
                if (date != null) {
                    SimpleDateFormat dateFormat = new SimpleDateFormat("HH mm a", Locale.getDefault());
                    String formattedDate = dateFormat.format(date);
                    holder.farmer_cart_item_date.setText(formattedDate);
                    holder.farmer_cart_item_price.setText(message.getContent());
                    fetch_sender_user_data(message.getReciever_ID(), holder);
                    holder.chat_item.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            ChatModel chat = new ChatModel(message.getSender_ID(), message.getReciever_ID(), message.getChat_id());
                            FragmentTransaction transaction = manager.beginTransaction();
                            Chat_Chatting_Fragment chat_chatting_fragment = new Chat_Chatting_Fragment(chat);
                            transaction.replace(R.id.Fragment_Chat, chat_chatting_fragment);
                            transaction.addToBackStack(null);
                            transaction.commit();
                        }
                    });
                }
            }
        }
    }

    @Override
    public int getItemCount() {
        return chatList.size();
    }

    public int getSize(){
        return chatList.size();
    }



    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView farmer_cart_item_image;
        TextView farmer_cart_item_title;
        TextView farmer_cart_item_price;
        TextView farmer_cart_item_date;
        ConstraintLayout chat_item;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            farmer_cart_item_image = itemView.findViewById(R.id.farmer_cart_item_image);
            farmer_cart_item_title = itemView.findViewById(R.id.farmer_cart_item_title);
            farmer_cart_item_price = itemView.findViewById(R.id.farmer_cart_item_price);
            farmer_cart_item_date = itemView.findViewById(R.id.farmer_cart_item_date);
            chat_item = itemView.findViewById(R.id.chat_item);
        }
    }

    private void fetch_sender_user_data(String Receiver, ViewHolder holder) {
        ValueEventListener fetch_users_data = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.child("Users").child(Receiver).exists()) {
                    User receiverData = snapshot.child("Users").child(Receiver).getValue(User.class);
                    if (receiverData != null) {
                        holder.farmer_cart_item_title.setText(receiverData.getUsername());
                        Picasso.get().load(receiverData.getUserImageUrl()).into(holder.farmer_cart_item_image);

                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        };
        database.addValueEventListener(fetch_users_data);
    }


    private void fetch_all_chats_from_database() {
        try {
            ValueEventListener fetch_all_chats = new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    chatList.clear();

                    if(snapshot.exists() && snapshot.child("Chats").exists()){
                        if(snapshot.child("Chats").exists()){
                            for (DataSnapshot user_chats : snapshot.child("Chats").getChildren()){
                                Message latestMessage = null;
                                for (DataSnapshot messages_snapshot : user_chats.child("Messages").getChildren()){
                                    Message message = messages_snapshot.getValue(Message.class);
                                    if (message != null) {
                                        if (latestMessage == null || (latestMessage.getTimeStamp() == null) || (message.getTimeStamp() != null && message.getTimeStamp() > latestMessage.getTimeStamp())) {
                                            latestMessage = message;
                                        }
                                    }
                                }
                                if (latestMessage != null) {
                                    chatList.add(latestMessage);
                                }
                            }
                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                }
            };
            database.addValueEventListener(fetch_all_chats);
        } catch (Exception e) {
            Toast.makeText(context, "Internet Error!", Toast.LENGTH_SHORT).show();
        }
    }
}
