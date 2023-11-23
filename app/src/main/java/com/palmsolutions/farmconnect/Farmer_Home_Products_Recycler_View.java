package com.palmsolutions.farmconnect;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.EventListener;
import java.util.Map;
import java.util.Objects;

public class Farmer_Home_Products_Recycler_View extends RecyclerView.Adapter<Farmer_Home_Products_Recycler_View.ViewHolder> {
    private Context context;
    private FirebaseAuth auth;
    private FirebaseUser user;
    private DatabaseReference database;
    private ArrayList<CompanyProduct> products;
    private FragmentManager manager;
    public Farmer_Home_Products_Recycler_View(Context context, FragmentManager manager){
        this.manager = manager;
        this.context = context;
        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        database = FirebaseDatabase.getInstance().getReference();
        fetch_all_data_from_firebase();
        products = new ArrayList<>();
    }
    @NonNull
    @Override
    public Farmer_Home_Products_Recycler_View.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.famer_home_product_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Farmer_Home_Products_Recycler_View.ViewHolder holder, int position) {
        Picasso.get().load(products.get(position).getImage()).into(holder.farmer_home_product_item_image_view);
        holder.farmer_home_product_item_title_text_view.setText(products.get(position).getTitle());
        holder.farmer_home_product_item_price_text_view.setText("PKR " + products.get(position).getPrice());
        holder.farmer_home_product_item_button.setText("Details");
        holder.farmer_home_product_item_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction transaction = manager.beginTransaction();
                Farmer_Product_Detail_Fragment product_detail_fragment = new Farmer_Product_Detail_Fragment(products.get(position));
                transaction.replace(R.id.Fragment_Home, product_detail_fragment);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });
    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView farmer_home_product_item_image_view;
        TextView farmer_home_product_item_title_text_view;
        TextView farmer_home_product_item_price_text_view;
        Button farmer_home_product_item_button;
        FragmentManager manager;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            farmer_home_product_item_image_view = itemView.findViewById(R.id.farmer_home_product_item_image_view);
            farmer_home_product_item_title_text_view = itemView.findViewById(R.id.farmer_home_product_item_title_text_view);
            farmer_home_product_item_price_text_view = itemView.findViewById(R.id.farmer_home_product_item_price_text_view);
            farmer_home_product_item_button = itemView.findViewById(R.id.farmer_home_product_item_button);

        }
    }

    private void fetch_all_data_from_firebase(){
        try{
            ValueEventListener fetch_data = new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    products.clear();
                    for (DataSnapshot user_snapshot : snapshot.child("Products").getChildren()){
                        for (DataSnapshot product_snapshot : user_snapshot.getChildren()){
                            CompanyProduct product = product_snapshot.getValue(CompanyProduct.class);
                            if(product != null){
                                products.add(product);
                            }
                        }
                    }
                    notifyDataSetChanged();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            };
            database.addValueEventListener(fetch_data);
        }
        catch (Exception e){
            Toast.makeText(context, "Internet Error!", Toast.LENGTH_SHORT).show();
            Log.e("Error", e.getMessage());
        }
    }
}
