package com.palmsolutions.farmconnect;

import android.content.Context;
import android.graphics.drawable.Drawable;
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
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Map;

public class company_products_recycler_view extends RecyclerView.Adapter<company_products_recycler_view.ViewHolder> {
    private Context context;
    private ArrayList<CompanyProduct> products;
    private FirebaseAuth auth;
    private FirebaseUser user;
    private DatabaseReference database;
    private FragmentManager manager;
    public company_products_recycler_view(Context context, FragmentManager manager){
        this.context = context;
        this.products = new ArrayList<>();
        this.manager = manager;
        fetchDataFromFirebase();
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.company_product_item, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if(products.get(position).getImage().equals("")){
            holder.product_image_company.setImageResource(R.drawable.crops);
            holder.bind(products.get(position));
        }
        else{
            holder.bind(products.get(position));
        }
        holder.product_title_company.setText(products.get(position).getTitle());
        holder.product_description_company.setText(products.get(position).getDescription());
        holder.product_price_company.setText("RS. " + products.get(position).getPrice());
        holder.product_edit_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction transaction = manager.beginTransaction();
                Company_Update_Dialog_Fragment companyUpdateDialogFragment = new Company_Update_Dialog_Fragment(context, products.get(position));
                transaction.replace(R.id.Fragment_Home, companyUpdateDialogFragment);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });
    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    private void fetchDataFromFirebase() {
        try {
            auth = FirebaseAuth.getInstance();
            user = auth.getCurrentUser();
            database = FirebaseDatabase.getInstance().getReference();

            ValueEventListener valueEventListener = new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    try {
                        products.clear();
                        for (DataSnapshot snapshot1 : snapshot.child("Products").getChildren()) {
                            if (snapshot1.getKey().equals(user.getUid())) {
                                for (DataSnapshot productSnapshot : snapshot1.getChildren()) {
                                    CompanyProduct newProduct = productSnapshot.getValue(CompanyProduct.class);
                                    products.add(newProduct);
                                }
                                break;
                            }
                        }
                        notifyDataSetChanged();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Toast.makeText(context, "Internet Error", Toast.LENGTH_SHORT).show();
                }
            };

            database.addListenerForSingleValueEvent(valueEventListener);
        } catch (Exception e) {
            Toast.makeText(context, "Exception" + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView product_image_company;
        TextView product_title_company;
        TextView product_description_company;
        TextView product_price_company;
        Button product_edit_button;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            product_image_company = itemView.findViewById(R.id.product_image_company);
            product_title_company = itemView.findViewById(R.id.product_title_company);
            product_description_company = itemView.findViewById(R.id.product_description_company);
            product_price_company = itemView.findViewById(R.id.product_price_company);
            product_edit_button = itemView.findViewById(R.id.product_edit_button);


        }
        public void bind(CompanyProduct product) {
            Picasso.get().load(product.getImage()).transform(new CircularTransform()).into(product_image_company);
        }
    }
}
