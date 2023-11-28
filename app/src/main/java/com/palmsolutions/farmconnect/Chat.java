package com.palmsolutions.farmconnect;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

public class Chat extends AppCompatActivity {

    public Chat(){

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        ConstraintLayout Fragment_Chat = findViewById(R.id.Fragment_Chat);

        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        Chat_Layout chat_layout = new Chat_Layout(manager);
        transaction.replace(R.id.Fragment_Chat, chat_layout);
        transaction.commit();
    }
}