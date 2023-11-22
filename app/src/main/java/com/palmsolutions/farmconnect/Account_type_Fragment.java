package com.palmsolutions.farmconnect;

import android.content.Context;
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
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class Account_type_Fragment extends Fragment {
    private String selected;
    private Context context;

    public Account_type_Fragment(Context context){
        this.context = context;
    };
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_account_type, container, false);
        TextView farmer_account_type = view.findViewById(R.id.farmer_account_type);
        TextView company_account_type = view.findViewById(R.id.company_account_type);

        ImageView farmerImage = view.findViewById(R.id.farmerImage);
        ImageView CompanyImage = view.findViewById(R.id.CompanyImage);

        farmerImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!"Farmer".equals(selected)) {
                    farmer_account_type.setTextColor(ContextCompat.getColor(context, R.color.PrimaryColor));
                    company_account_type.setTextColor(ContextCompat.getColor(context, android.R.color.black));
                    selected = "Farmer";
                }
            }
        });

        CompanyImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!"Company".equals(selected)) {
                    company_account_type.setTextColor(ContextCompat.getColor(context, R.color.PrimaryColor));
                    farmer_account_type.setTextColor(ContextCompat.getColor(context, android.R.color.black));
                    selected = "Company";
                }
            }
        });

        Button account_type_button = view.findViewById(R.id.account_type_button);

        account_type_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!selected.isEmpty()) {
                    Show_sign_up_activity();
                } else {
                    Toast.makeText(context, "Please Choose One Selection", Toast.LENGTH_SHORT).show();
                }
            }
        });
        return view;
    }
    protected void Show_sign_up_activity(){
        Bundle bundle = new Bundle();
        bundle.putString("accountType", selected);
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        Sign_up_fragment signUpFragment = new Sign_up_fragment(context);
        signUpFragment.setArguments(bundle);
        transaction.replace(R.id.Authentication, signUpFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}
