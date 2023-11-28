package com.palmsolutions.farmconnect;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class Chat_Receiver_Messages_Recycler_View extends RecyclerView.Adapter<Chat_Receiver_Messages_Recycler_View.ViewHolder> {
    private Context context;
    private FirebaseAuth auth;
    private FirebaseUser user;
    private DatabaseReference database;
    private ChatModel chatModel;
    private ArrayList<Message> messages;
    private SimpleDateFormat dateFormat;
    private Date date;
    public Chat_Receiver_Messages_Recycler_View(Context context, ChatModel chatModel){
        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        database = FirebaseDatabase.getInstance().getReference();
        this.context = context;
        this.chatModel = chatModel;
        messages = new ArrayList<>();
        dateFormat = new SimpleDateFormat("HH mm a", Locale.getDefault());
        fetch_all_messages_from_database();
    }
    @NonNull
    @Override
    public Chat_Receiver_Messages_Recycler_View.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.message_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Chat_Receiver_Messages_Recycler_View.ViewHolder holder, int position) {
        holder.message_item.setText(messages.get(position).getContent());
        date = new Date(messages.get(position).getTimeStamp());
        String time = dateFormat.format(date);
        holder.timestamp.setText(time);
    }

    @Override
    public int getItemCount() {
        return messages.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView message_item;
        TextView timestamp;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            message_item = itemView.findViewById(R.id.message_item);
            timestamp = itemView.findViewById(R.id.timestamp);
        }
    }

    private void fetch_all_messages_from_database(){
        try{
            ValueEventListener fetch_messages = new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if(snapshot.child("Chats").child(chatModel.getChatId()).exists()){
                        for (DataSnapshot messages_snapshot : snapshot.child("Chats").child(chatModel.getChatId()).child("Messages").getChildren()){
                            Message message = messages_snapshot.getValue(Message.class);
                            if (message.getSender_ID() != user.getUid()){
                                messages.add(message);
                                notifyDataSetChanged();
                            }
                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            };
            database.addValueEventListener(fetch_messages);
        }
        catch (Exception e){
            Toast.makeText(context, "Internet Error!", Toast.LENGTH_SHORT).show();
        }
    }

}
