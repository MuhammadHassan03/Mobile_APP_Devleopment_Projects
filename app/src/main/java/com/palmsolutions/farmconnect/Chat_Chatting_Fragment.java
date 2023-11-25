package com.palmsolutions.farmconnect;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class Chat_Chatting_Fragment extends Fragment {

    private FirebaseAuth auth;
    private FirebaseUser user;
    private DatabaseReference database;
    private ChatModel chat;
    private User receiver_data;

    public Chat_Chatting_Fragment(ChatModel chat){
        this.chat = chat;

    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        database = FirebaseDatabase.getInstance().getReference();
        FullScreenUtil.hideSystemUI(requireActivity().getWindow().getDecorView());

        View view = inflater.inflate(R.layout.chat_chatting_fragment, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        fetch_receiver_data_from_database();


    }

    private void fetch_receiver_data_from_database(){
        try{
            ValueEventListener fetch_receiver_data = new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if(snapshot.child("Users").child(chat.getReciever()).exists()){
                        receiver_data = snapshot.child("Users").child(chat.getReciever()).getValue(User.class);
                        if (getView() != null) {
                            TextView chat_reciever_user_name = getView().findViewById(R.id.chat_reciever_user_name);
                            chat_reciever_user_name.setText(receiver_data.getUsername());
                            ImageView chat_reciever_profile_pic = getView().findViewById(R.id.chat_reciever_profile_pic);
                            Picasso.get().load(receiver_data.getUserImageUrl()).into(chat_reciever_profile_pic);

                            Button chat_send_button = getView().findViewById(R.id.chat_send_button);
                            chat_send_button.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    handle_messages();
                                }
                            });
                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Toast.makeText(getContext(), "Internet Error!", Toast.LENGTH_SHORT).show();
                }
            };
            database.addValueEventListener(fetch_receiver_data);
        }
        catch (Exception e){
            Toast.makeText(getContext(), "Internet Error!", Toast.LENGTH_SHORT).show();
        }
    }

    private void handle_messages(){
        try {
            EditText chat_send_message = getView().findViewById(R.id.chat_send_message);
            String message = chat_send_message.getText().toString();

            if (!message.isEmpty()) {
                DatabaseReference messagesRef = database.child("Chats").child(user.getUid()).child(chat.chat_id).child("Messages");
                String Message_ID = messagesRef.push().getKey();
                Message new_message = new Message(Message_ID, user.getUid(), chat.getChat_id(), message);
                messagesRef.child(Message_ID).setValue(new_message);

                chat_send_message.setText("");
            }

        }
        catch (Exception e){
            Toast.makeText(getContext(), "Internet Error!", Toast.LENGTH_SHORT).show();
        }
    }


}
