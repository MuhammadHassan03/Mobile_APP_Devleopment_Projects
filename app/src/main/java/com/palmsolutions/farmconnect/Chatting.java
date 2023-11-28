package com.palmsolutions.farmconnect;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.widget.Toast;

import java.io.Serializable;

public class Chatting extends AppCompatActivity implements Serializable{

    private ChatModel chat;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chatting);

        chat = (ChatModel) getIntent().getSerializableExtra("chats");
        Toast.makeText(this, "chat Reciever" + chat, Toast.LENGTH_SHORT).show();
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        Chat_Chatting_Fragment chat_chatting_fragment = new Chat_Chatting_Fragment(chat);
        transaction.replace(R.id.Fragment_Chatting, chat_chatting_fragment);
        transaction.commit();
    }
}