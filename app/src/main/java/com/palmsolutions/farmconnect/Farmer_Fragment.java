package com.palmsolutions.farmconnect;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class Farmer_Fragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        FragmentManager manager = getFragmentManager();
        FullScreenUtil.hideSystemUI(requireActivity().getWindow().getDecorView());

        View view = inflater.inflate(R.layout.farmer_layout, container ,  false);

        RecyclerView farmer_home_categories_recycler_view = view.findViewById(R.id.farmer_home_categories_recycler_view);
        farmer_home_categories_recycler_view.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false));
        farmer_home_categories_recycler_view.setAdapter(new Farmer_Home_Category_Recycler_View(getContext()));

        RecyclerView farmer_home_all_products_recycler_view = view.findViewById(R.id.farmer_home_all_products_recycler_view);
        farmer_home_all_products_recycler_view.setLayoutManager(new GridLayoutManager(getContext(), 2));
        farmer_home_all_products_recycler_view.setAdapter(new Farmer_Home_Products_Recycler_View(getContext(), manager));

        ImageButton farmer_home_categories_filter = view.findViewById(R.id.farmer_home_categories_filter);
        farmer_home_categories_filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (farmer_home_categories_recycler_view.getVisibility() == View.VISIBLE) {
                    farmer_home_categories_recycler_view.setVisibility(View.GONE);
                } else {
                    farmer_home_categories_recycler_view.setVisibility(View.VISIBLE);
                }
            }
        });

        ImageView farmer_navbar_weather_btn_fragment = view.findViewById(R.id.farmer_navbar_weather_btn_fragment);
        ImageView farmer_navbar_chat_btn_fragment = view.findViewById(R.id.farmer_navbar_chat_btn_fragment);
        ImageView farmer_user_icon = view.findViewById(R.id.farmer_user_icon);

        farmer_navbar_weather_btn_fragment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction transaction = manager.beginTransaction();
                Farmer_Weather_Fragment farmer_weather_fragment = new Farmer_Weather_Fragment();
                transaction.replace(R.id.Fragment_Home, farmer_weather_fragment);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });

        farmer_navbar_chat_btn_fragment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction transaction = manager.beginTransaction();
                Chat_Layout chat_layout = new Chat_Layout();
                transaction.replace(R.id.Fragment_Home, chat_layout);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });

        farmer_user_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction transaction = manager.beginTransaction();
                User_Fragment user_fragment = new User_Fragment();
                transaction.replace(R.id.Fragment_Home, user_fragment);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });


        return view;
    }
}
