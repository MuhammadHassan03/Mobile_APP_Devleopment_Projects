package com.palmsolutions.farmconnect;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

public class Farmer_Product_Detail_Fragment extends Fragment {
    CompanyProduct product;
    private FirebaseAuth auth;
    private FirebaseUser user;
    private DatabaseReference database;
    public Farmer_Product_Detail_Fragment(CompanyProduct product){
        this.product = product;
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        database = FirebaseDatabase.getInstance().getReference();
        FullScreenUtil.hideSystemUI(requireActivity().getWindow().getDecorView());

        View view = inflater.inflate(R.layout.farmer_home_product_detail, container, false);

        ImageView farmer_product_detail_image = view.findViewById(R.id.farmer_product_detail_image);
        TextView farmer_product_detail_title = view.findViewById(R.id.farmer_product_detail_title);
        TextView farmer_product_detail_price = view.findViewById(R.id.farmer_product_detail_price);
        TextView farmer_product_detail_description = view.findViewById(R.id.farmer_product_detail_description);

        Picasso.get().load(product.getImage()).into(farmer_product_detail_image);
        farmer_product_detail_title.setText(product.getTitle());
        farmer_product_detail_price.setText("RS. " + product.getPrice());
        farmer_product_detail_description.setText(product.getDescription());

        Button farmer_product_detail_conditional_btn = view.findViewById(R.id.farmer_product_detail_conditional_btn);
        Button farmer_product_detail_chat_btn = view.findViewById(R.id.farmer_product_detail_chat_btn);

        if("Grains".equals(product.getProduct_type())){
            farmer_product_detail_conditional_btn.setText("Sell Now");
            farmer_product_detail_chat_btn.setVisibility(View.GONE);
        }

        else if("Equipment".equals(product.getProduct_type())){
            farmer_product_detail_conditional_btn.setText("Buy Now");
            farmer_product_detail_chat_btn.setVisibility(View.VISIBLE);

            farmer_product_detail_chat_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String chat_id;
                    String uid1 = user.getUid();
                    String uid2 = product.getUser_uuid();
                    if (uid1.compareTo(uid2) < 0) {
                        chat_id = uid1 + "_" + uid2;
                    } else {
                        chat_id = uid2 + "_" + uid1;
                    }
                    ChatModel chat = new ChatModel(user.getUid(), product.getUser_uuid(), chat_id);

                    Intent intent = new Intent(requireContext(), Chatting.class);
                    intent.putExtra("chats", chat);
                    startActivity(intent);
                }
            });

        }
        else if("Fertilizer".equals(product.getProduct_type())){
            farmer_product_detail_conditional_btn.setText("Buy Now");
            farmer_product_detail_chat_btn.setVisibility(View.GONE);

            farmer_product_detail_conditional_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String cart_product_id = database.child("Cart").child(user.getUid()).child(product.getProduct_id()).getKey();
                    CartItem cartItem = new CartItem(product.getTitle(), "1", product.getPrice(), product.getImage(), product.getProduct_type(), product.getProduct_id(), cart_product_id);
                    database.child("Cart").child(user.getUid()).child(cart_product_id).setValue(cartItem);

                    FragmentManager manager = getFragmentManager();
                    FragmentTransaction transaction = manager.beginTransaction();
                    Farmer_Cart_Fragment cart_fragment = new Farmer_Cart_Fragment();
                    transaction.replace(R.id.Fragment_Home, cart_fragment);
                    transaction.addToBackStack(null);
                    transaction.commit();
                }
            });

        }
        else{
            farmer_product_detail_conditional_btn.setText("Buy Now");
        }

        FragmentManager manager = getFragmentManager();
        ImageView farmer_navbar_product_detail_home_btn_fragment = view.findViewById(R.id.farmer_navbar_product_detail_home_btn_fragment);

        farmer_navbar_product_detail_home_btn_fragment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction transaction = manager.beginTransaction();
                Farmer_Home_Fragment farmer_home_fragment = new Farmer_Home_Fragment();
                transaction.replace(R.id.Fragment_Home, farmer_home_fragment);
                transaction.commit();
            }
        });

//        ImageView farmer_navbar_weather_btn_fragment = view.findViewById(R.id.farmer_navbar_weather_btn_fragment);
//        ImageView farmer_navbar_chat_btn_fragment = view.findViewById(R.id.farmer_navbar_chat_btn_fragment);
//        ImageView farmer_user_icon = view.findViewById(R.id.farmer_user_icon);
//        FragmentManager manager = getFragmentManager();
//
//        farmer_navbar_weather_btn_fragment.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                FragmentTransaction transaction = manager.beginTransaction();
//                Farmer_Weather_Fragment farmer_weather_fragment = new Farmer_Weather_Fragment();
//                transaction.replace(R.id.Fragment_Home, farmer_weather_fragment);
//                transaction.addToBackStack(null);
//                transaction.commit();
//            }
//        });
//
//        farmer_navbar_chat_btn_fragment.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                startActivity(new Intent(getContext(), Chat.class));
//            }
//        });
//
//        farmer_user_icon.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                FragmentTransaction transaction = manager.beginTransaction();
//                User_Fragment user_fragment = new User_Fragment();
//                transaction.replace(R.id.Fragment_Home, user_fragment);
//                transaction.addToBackStack(null);
//                transaction.commit();
//            }
//        });


        return view;
    }
}
