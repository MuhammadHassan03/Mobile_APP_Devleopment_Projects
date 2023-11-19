package com.palmsolutions.farmconnect;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class Company_Update_Dialog_Fragment extends Fragment {
    private CompanyProduct product;
    private Context context;
    private FirebaseAuth auth;
    private FirebaseUser user;
    private DatabaseReference database;
    private FirebaseStorage storage;

    private Uri selected_image;
    public Company_Update_Dialog_Fragment(Context context, CompanyProduct product){
        this.context = context;
        this.product = product;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        database = FirebaseDatabase.getInstance().getReference();
        storage = FirebaseStorage.getInstance();

        View view = inflater.inflate(R.layout.company_edit_dialog, container, false);
        EditText company_update_dialog_title_edit_text = view.findViewById(R.id.company_update_dialog_title_edit_text);
        EditText company_update_dialog_description_edit_text = view.findViewById(R.id.company_update_dialog_description_edit_text);
        EditText company_update_dialog_price_edit_text = view.findViewById(R.id.company_update_dialog_price_edit_text);


        company_update_dialog_title_edit_text.setText(product.getTitle().toString());
        company_update_dialog_description_edit_text.setText(product.getDescription().toString());
        company_update_dialog_price_edit_text.setText(product.getPrice().toString());

        Toast.makeText(context, "Product ID : " + product.getProduct_id(), Toast.LENGTH_SHORT).show();

        Button company_update_dialog_update_button = view.findViewById(R.id.company_update_dialog_update_button);
        company_update_dialog_update_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    String Title = company_update_dialog_title_edit_text.getText().toString();
                    String Description = company_update_dialog_description_edit_text.getText().toString();
                    String Price = company_update_dialog_price_edit_text.getText().toString();


                    CompanyProduct updated_product = new CompanyProduct("image_url", Title, Description, Price, product.Product_id);
                    database.child("Products").child(user.getUid()).child(product.getProduct_id()).setValue(updated_product);
                    Toast.makeText(getContext(), "Product Updated Successfully", Toast.LENGTH_SHORT).show();

                }
                catch (Exception e){
                    Log.e("Error" , e.getMessage());
                }
            }
        });

        Button company_update_dialog_delete_button = view.findViewById(R.id.company_update_dialog_delete_button);
        company_update_dialog_delete_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new AlertDialog.Builder(context).setTitle("Do You really want to delete it?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                DatabaseReference delete_product = database.child("Products").child(user.getUid()).child(product.getProduct_id());
                                delete_product.removeValue();
                                Toast.makeText(getContext(), "Product Deletion Success", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                            }
                        }).show();
            }
        });

        return view;
    }
}
