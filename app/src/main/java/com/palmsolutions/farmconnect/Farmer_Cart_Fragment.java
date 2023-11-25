package com.palmsolutions.farmconnect;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class Farmer_Cart_Fragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        FullScreenUtil.hideSystemUI(requireActivity().getWindow().getDecorView());

        View view = inflater.inflate(R.layout.farmer_cart_fragment, container, false);

        RecyclerView farmer_cart_recycler_view = view.findViewById(R.id.farmer_cart_recycler_view);
        farmer_cart_recycler_view.setLayoutManager(new LinearLayoutManager(getContext()));
        Farmer_Cart_Recycler_View recyclerView = new Farmer_Cart_Recycler_View(view.getContext());
        farmer_cart_recycler_view.setAdapter(recyclerView);

        TextView Farmer_Cart_size_text = view.findViewById(R.id.Farmer_Cart_size_text);
        Farmer_Cart_size_text.setText("List (" + recyclerView.getProductsCount() + ")");

        Button farmer_cart_pay_button = view.findViewById(R.id.farmer_cart_pay_button);
        farmer_cart_pay_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        return view;
    }

}
