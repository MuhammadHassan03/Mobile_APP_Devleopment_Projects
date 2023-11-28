package com.palmsolutions.farmconnect;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;



public class Chat_Layout extends Fragment {
    private FragmentManager manager;
    public Chat_Layout(FragmentManager manager){
        this.manager = manager;
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.chat_layout, container, false);
        TextView farmer_chat_layout_no_chat = view.findViewById(R.id.farmer_chat_layout_no_chat);

        RecyclerView farmer_chat_layout_recycler_view = view.findViewById(R.id.farmer_chat_layout_recycler_view);
        farmer_chat_layout_recycler_view.setLayoutManager(new LinearLayoutManager(getContext()));
        All_Chats_Recycler_View chats_recycler_view = new All_Chats_Recycler_View(getContext(), manager);
        farmer_chat_layout_recycler_view.setAdapter(chats_recycler_view);

        if(chats_recycler_view.getSize() == 0){
            farmer_chat_layout_no_chat.setVisibility(View.VISIBLE);
        }
        else{
            farmer_chat_layout_no_chat.setVisibility(View.GONE);
        }

        return view;
    }
}
