package com.palmsolutions.farmconnect;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class Farmer_Fragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        FragmentManager manager = getFragmentManager();
        View view = inflater.inflate(R.layout.farmer_layout, container ,  false);

        RecyclerView farmer_home_categories_recycler_view = view.findViewById(R.id.farmer_home_categories_recycler_view);
        farmer_home_categories_recycler_view.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false));
        farmer_home_categories_recycler_view.setAdapter(new Farmer_Home_Category_Recycler_View(getContext()));

        RecyclerView farmer_home_all_products_recycler_view = view.findViewById(R.id.farmer_home_all_products_recycler_view);
        farmer_home_all_products_recycler_view.setLayoutManager(new GridLayoutManager(getContext(), 2));
        farmer_home_all_products_recycler_view.setAdapter(new Farmer_Home_Products_Recycler_View(getContext(), manager));

        return view;
    }
}
