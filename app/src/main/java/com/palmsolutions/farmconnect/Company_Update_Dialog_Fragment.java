package com.palmsolutions.farmconnect;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class Company_Update_Dialog_Fragment extends Fragment {
    CompanyProduct product;
    Context context;
    public Company_Update_Dialog_Fragment(Context context, CompanyProduct product){
        this.context = context;
        this.product = product;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.company_edit_dialog, container, false);
        EditText company_update_dialog_title_edit_text = view.findViewById(R.id.company_update_dialog_title_edit_text);
        EditText company_update_dialog_description_edit_text = view.findViewById(R.id.company_update_dialog_description_edit_text);
        EditText company_update_dialog_price_edit_text = view.findViewById(R.id.company_update_dialog_price_edit_text);

        company_update_dialog_title_edit_text.setText(product.getTitle().toString());
        company_update_dialog_description_edit_text.setText(product.getDescription().toString());
        company_update_dialog_price_edit_text.setText(product.getPrice().toString());

        Button company_update_dialog_update_button = view.findViewById(R.id.company_update_dialog_update_button);
        company_update_dialog_update_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    String Title = company_update_dialog_title_edit_text.getText().toString();
                    String Description = company_update_dialog_description_edit_text.getText().toString();
                    String Price = company_update_dialog_price_edit_text.getText().toString();

                    CompanyProduct updateProduct = new CompanyProduct(Title, Description, Price);

                }
                catch (Exception e){
                    Toast.makeText(context, "Error " + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
        return view;
    }
}
