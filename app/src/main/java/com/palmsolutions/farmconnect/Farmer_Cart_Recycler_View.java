package com.palmsolutions.farmconnect;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
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

public class Farmer_Cart_Recycler_View extends RecyclerView.Adapter<Farmer_Cart_Recycler_View.ViewHolder> {
    private ArrayList<CartItem> cart_products;
    private Context context;
    private FirebaseAuth auth;
    private FirebaseUser user;
    private DatabaseReference database;
    public Farmer_Cart_Recycler_View(Context context){
        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        database = FirebaseDatabase.getInstance().getReference();
        cart_products = new ArrayList<>();
        this.context = context;
        fetch_cart_data_from_database();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.farmer_cart_item, parent, false);
        context = view.getContext();
        return new ViewHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Picasso.get().load(cart_products.get(position).getProduct_image()).into(holder.farmer_cart_item_image);
        holder.farmer_cart_item_title.setText(cart_products.get(position).getTitle());
        holder.farmer_cart_item_price.setText("RS. " + cart_products.get(position).getPrice());
        holder.farmer_cart_item_count.setText(cart_products.get(position).getCount());
        holder.farmer_cart_item_plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int count = Integer.parseInt(cart_products.get(position).getCount()) + 1;
                updateCount(position, cart_products.get(position), count);

            }
        });

        holder.farmer_cart_item_minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int count = Integer.parseInt(cart_products.get(position).getCount()) - 1;
                count = Math.max(count, 1);
                updateCount(position, cart_products.get(position), count);
            }
        });

        holder.farmer_cart_item_delete_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteProductFromCart(cart_products.get(position));
            }
        });
    }
    @Override
    public int getItemCount() {
        return cart_products.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView farmer_cart_item_image;
        ImageButton farmer_cart_item_delete_image;
        ImageButton farmer_cart_item_plus;
        ImageButton farmer_cart_item_minus;
        TextView farmer_cart_item_title;
        TextView farmer_cart_item_price;
        TextView farmer_cart_item_count;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            farmer_cart_item_image = itemView.findViewById(R.id.farmer_cart_item_image);
            farmer_cart_item_title = itemView.findViewById(R.id.farmer_cart_item_title);
            farmer_cart_item_price = itemView.findViewById(R.id.farmer_cart_item_price);
            farmer_cart_item_delete_image = itemView.findViewById(R.id.farmer_cart_item_delete_image);
            farmer_cart_item_count = itemView.findViewById(R.id.farmer_cart_item_count);
            farmer_cart_item_plus = itemView.findViewById(R.id.farmer_cart_item_plus);
            farmer_cart_item_minus = itemView.findViewById(R.id.farmer_cart_item_minus);
        }
    }
    private void fetch_cart_data_from_database(){
        try{
            ValueEventListener fetch_data = new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    cart_products.clear();
                    if (snapshot.child("Cart").child(user.getUid()).exists()) {
                        for (DataSnapshot productSnapshot : snapshot.child("Cart").child(user.getUid()).getChildren()) {
                            CartItem cartItem = productSnapshot.getValue(CartItem.class);
                            cart_products.add(cartItem);

                            notifyDataSetChanged();
                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            };
            database.addValueEventListener(fetch_data);
        }
        catch (Exception e){
            Toast.makeText(context, "Internet Error!", Toast.LENGTH_SHORT).show();
        }
    }
    private void updateCount(int position, CartItem product, int newCount) {
        int oldCount = Integer.parseInt(product.getCount());

        product.setCount(String.valueOf(newCount));

        int pricePerItem = Integer.parseInt(product.getPrice()) / oldCount;
        int priceDifference = (newCount - oldCount) * pricePerItem;

        int newPrice = Integer.parseInt(product.getPrice()) + priceDifference;
        product.setPrice(String.valueOf(newPrice));

        database.child("Cart").child(user.getUid()).child(product.getCart_product_id()).setValue(product);

        notifyItemChanged(position);
    }
    private void deleteProductFromCart(CartItem product) {
        database.child("Cart").child(user.getUid()).child(product.getCart_product_id()).removeValue();
    }
    public int getProductsCount() {
        return cart_products.size() + 1;
    }
}
