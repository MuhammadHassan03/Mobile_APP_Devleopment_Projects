package com.palmsolutions.farmconnect;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Farmer_Home_Fragment extends Fragment implements Farmer_Home_Category_Recycler_View.CategorySelectionListener {
    private FirebaseAuth auth;
    private FirebaseUser user;

    private ProgressBar progress_bar_home;
    private Farmer_Home_Products_Recycler_View productsRecyclerAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();

        FullScreenUtil.hideSystemUI(requireActivity().getWindow().getDecorView());

        View view = inflater.inflate(R.layout.farmer_layout, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        RecyclerView farmer_home_categories_recycler_view = view.findViewById(R.id.farmer_home_categories_recycler_view);
        farmer_home_categories_recycler_view.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false));
        progress_bar_home = view.findViewById(R.id.progress_bar_home);

        // Pass this fragment as the listener to the category adapter
        Farmer_Home_Category_Recycler_View categoryRecyclerAdapter = new Farmer_Home_Category_Recycler_View(getContext(), this);
        farmer_home_categories_recycler_view.setAdapter(categoryRecyclerAdapter);

        // Initialize the products recycler view adapter
        productsRecyclerAdapter = new Farmer_Home_Products_Recycler_View(getContext(), getChildFragmentManager(), progress_bar_home);

    }

    @Override
    public void onCategorySelected(String category) {
        if ((category).equals("Grains")) {
            productsRecyclerAdapter.grains_filter(true);
        } else if ((category).equals("Fertilizer")) {
            productsRecyclerAdapter.fertilizer_filter(true);
        } else if ((category).equals("Equipment")) {
            productsRecyclerAdapter.equipment_filter(true);
        } else {
        }

        // Set the adapter to the products recycler view
        RecyclerView farmer_home_products_recycler_view = getView().findViewById(R.id.farmer_home_all_products_recycler_view);
        farmer_home_products_recycler_view.setLayoutManager(new LinearLayoutManager(getContext()));
        farmer_home_products_recycler_view.setAdapter(productsRecyclerAdapter);
    }
}
