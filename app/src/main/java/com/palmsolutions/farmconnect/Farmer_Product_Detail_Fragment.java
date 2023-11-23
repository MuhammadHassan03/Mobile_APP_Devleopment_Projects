package com.palmsolutions.farmconnect;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.squareup.picasso.Picasso;

public class Farmer_Product_Detail_Fragment extends Fragment {
    CompanyProduct product;
    public Farmer_Product_Detail_Fragment(CompanyProduct product){
        this.product = product;
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.farmer_home_product_detail, container, false);

        ImageView farmer_product_detail_image = view.findViewById(R.id.farmer_product_detail_image);
        TextView farmer_product_detail_title = view.findViewById(R.id.farmer_product_detail_title);
        TextView farmer_product_detail_price = view.findViewById(R.id.farmer_product_detail_price);
        TextView farmer_product_detail_description = view.findViewById(R.id.farmer_product_detail_description);

        Picasso.get().load(product.getImage()).into(farmer_product_detail_image);
        farmer_product_detail_title.setText(product.getTitle());
        farmer_product_detail_price.setText(product.getPrice());
        farmer_product_detail_description.setText(product.getDescription());

        return view;
    }
}
