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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

public class Company_Update_Dialog_Fragment extends Fragment {
    private CompanyProduct product;
    private Context context;
    private FirebaseAuth auth;
    private FirebaseUser user;
    private DatabaseReference database;
    private FirebaseStorage storage;
    private static int PICK_IMAGE_REQUEST;
    private String product_type;


    private Uri selected_image;
    public Company_Update_Dialog_Fragment(Context context, CompanyProduct product){
        this.context = context;
        this.product = product;
        PICK_IMAGE_REQUEST = 1;
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

                    if (selected_image != null) {
                        // If a new image is selected, upload it to Firebase Storage
                        upload_image_to_firebase_storage(selected_image)
                                .addOnSuccessListener(uri -> {
                                    String imageUrl = uri.toString();
                                    updateProduct(Title, Description, Price, imageUrl);
                                })
                                .addOnFailureListener(e -> {
                                    Toast.makeText(getContext(), "Product Image Upload Failed: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                                });
                    } else {
                        // If no new image is selected, update with the existing image URL
                        updateProduct(Title, Description, Price, product.getImage());
                    }
                } catch (Exception e) {
                    Log.e("Error", e.getMessage());
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

        ImageView company_update_dialog_image = view.findViewById(R.id.company_update_dialog_image);
        company_update_dialog_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                open_gallery();
            }
        });

        if(!product.getImage().equals("")){
            Picasso.get().load(product.getImage()).into(company_update_dialog_image);
        }

        Spinner company_update_dialog_spinner = view.findViewById(R.id.company_update_dialog_spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(), R.array.farmer_category, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        company_update_dialog_spinner.setAdapter(adapter);

        if("Grains".equals(product.getProduct_type())){
            company_update_dialog_spinner.setSelection(1);
            product_type = "Grains";
        }
        else if("Fertilizer".equals(product.getProduct_type())){
            company_update_dialog_spinner.setSelection(1);
            product_type = "Fertilizer";
        }
        else if("Equipment".equals(product.getProduct_type())){
            company_update_dialog_spinner.setSelection(2);
            product_type = "Equipment";
        }
        else{
            company_update_dialog_spinner.setSelection(0);
            product_type = "Grains";
        }

        company_update_dialog_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                product_type = adapterView.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        return view;
    }

    private void updateProduct(String title, String description, String price, String imageUrl) {
        CompanyProduct updated_product = new CompanyProduct(imageUrl, title, description, price, product.getProduct_id(), product_type, user.getUid());
        updated_product.setUser_uuid(user.getUid());
        database.child("Products").child(user.getUid()).child(product.getProduct_id()).setValue(updated_product)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(getContext(), "Product Updated Successfully", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getContext(), "Product Update Failed: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    public Task<Uri> upload_image_to_firebase_storage(Uri image){
        try{

            final String[] image_name = {"product." + System.currentTimeMillis() + ".jpg"};
            String image_path = "images/" + user.getUid() + "/products/"+ image_name[0];

            StorageReference storage_reference = storage.getReference().child(image_path);

            UploadTask uploadTask = storage_reference.putFile(image);

            return uploadTask.continueWithTask(task -> {
                if(!task.isSuccessful()){
                    Toast.makeText(getContext(), "" + task.getException(), Toast.LENGTH_SHORT).show();
                }
                return storage_reference.getDownloadUrl();
            });
        }
        catch (Exception e){
            Toast.makeText(context, "Exception : " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
        return null;
    }

    private void open_gallery(){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK && data != null && data.getData() != null){
            selected_image = data.getData();
            ImageView company_user_profile_image_view = getView().findViewById(R.id.company_update_dialog_image);
            company_user_profile_image_view.setImageURI(selected_image);
            Picasso.get().load(selected_image).transform(new CircularTransform()).into(company_user_profile_image_view);
        }
    }
}
