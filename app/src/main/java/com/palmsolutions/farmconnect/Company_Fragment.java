package com.palmsolutions.farmconnect;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class Company_Fragment extends Fragment {
    private Context context;
    public Company_Fragment(Context context){
        this.context = context;
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        FragmentManager manager = getFragmentManager();
        View view = inflater.inflate(R.layout.company_fragment, container, false);
        ConstraintLayout add_product_constraint_layout_company = view.findViewById(R.id.add_product_constraint_layout_company);
        RecyclerView company_products_recycler_view = view.findViewById(R.id.company_products_recycler_view);

        company_products_recycler_view.setLayoutManager(new LinearLayoutManager(context));
        company_products_recycler_view.setAdapter(new company_products_recycler_view(context, manager));
        Toast.makeText(context, "Recycler Vie Loaded", Toast.LENGTH_SHORT).show();

        add_product_constraint_layout_company.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                FragmentTransaction transaction = manager.beginTransaction();
                Company_Add_Product_Fragment companyAddProductFragment = new Company_Add_Product_Fragment(context);
                transaction.replace(R.id.Fragment_Home, companyAddProductFragment);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });

        return view;
    }
}
