package com.palmsolutions.farmconnect;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Farmer_Home_Fragment extends Fragment {
    private FirebaseAuth auth;
    private FirebaseUser user;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();

        View view = inflater.inflate(R.layout.farmer_layout, container, false);

        RecyclerView farmer_home_categories_recycler_view = view.findViewById(R.id.farmer_home_categories_recycler_view);
        farmer_home_categories_recycler_view.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false));
        farmer_home_categories_recycler_view.setAdapter(new Farmer_Home_Category_Recycler_View(getContext()));

        Toast.makeText(getContext(), "Loaded", Toast.LENGTH_SHORT).show();

        return view;
    }


}
