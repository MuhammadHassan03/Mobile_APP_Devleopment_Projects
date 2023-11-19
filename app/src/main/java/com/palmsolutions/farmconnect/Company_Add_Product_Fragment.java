package com.palmsolutions.farmconnect;

import android.app.Activity;
import android.content.Context;
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

public class Company_Add_Product_Fragment extends Fragment {
    private static final int PICK_IMAGE_REQUEST = 1;
    private Context context;
    private FirebaseAuth auth;
    private FirebaseUser user;
    private DatabaseReference database;
    private FirebaseStorage storage;
    private Uri selected_image;

    public Company_Add_Product_Fragment(Context context){
        this.context = context;
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        database = FirebaseDatabase.getInstance().getReference();
        storage = FirebaseStorage.getInstance();

        View view = inflater.inflate(R.layout.company_add_product_fragment, container, false);

        EditText company_new_product_form_title_edit_text = view.findViewById(R.id.company_new_product_form_title_edit_text);
        EditText company_new_product_form_description_edit_text = view.findViewById(R.id.company_new_product_form_description_edit_text);
        EditText company_new_product_form_price_edit_text = view.findViewById(R.id.company_new_product_form_price_edit_text);

        Button company_new_product_form_add_button = view.findViewById(R.id.company_new_product_form_add_button);

        company_new_product_form_add_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try{

                    String title = company_new_product_form_title_edit_text.getText().toString();
                    String description = company_new_product_form_description_edit_text.getText().toString();
                    String price = company_new_product_form_price_edit_text.getText().toString();

                    Log.e("Title", "Value : " +  title.toString());
                    Log.e("Description" , "Value" + description.toString());
                    Log.e("Price", "Value" + price.toString());


                    if(selected_image == null || title.isEmpty() || description.isEmpty() || price.isEmpty()){
                        Toast.makeText(context, "Please Enter All Fields First", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        String image_url = upload_image_to_firebase_storage(selected_image);
                        Toast.makeText(getContext(), "Image "+image_url, Toast.LENGTH_SHORT).show();
                        String product_id = database.child("Products").child(user.getUid()).push().getKey();
                        CompanyProduct newProduct = new CompanyProduct(image_url, title, description, price, product_id);
                        database.child("Products").child(user.getUid()).child(product_id).setValue(newProduct);
                        Toast.makeText(getContext(), "Product Added Successfully", Toast.LENGTH_SHORT).show();
                    }

                }
                catch (Exception exception){
                    Toast.makeText(context, "Error Occurred " + exception.getMessage(), Toast.LENGTH_SHORT).show();
                    Log.e("Error" , "Occured : " + exception.getMessage());
                }
            }
        });

        ImageView company_new_product_image = view.findViewById(R.id.company_new_product_image);
        company_new_product_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                open_gallery_intent();
            }
        });
        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK && data != null && data.getData() != null){
            selected_image = data.getData();


            ImageView company_new_product_image = getView().findViewById(R.id.company_new_product_image);
            company_new_product_image.setImageURI(selected_image);
            Toast.makeText(context, "Image Set", Toast.LENGTH_SHORT).show();
        }
    }

    private void open_gallery_intent(){
        Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(galleryIntent, PICK_IMAGE_REQUEST);
    }

    private String upload_image_to_firebase_storage(Uri image){
        final String[] image_url = {""};
        try{

            final String[] image_name = {"product." + System.currentTimeMillis() + ".jpg"};
            String image_path = "images/" + user.getUid() + "/products/"+ image_name[0];

            StorageReference storage_reference = storage.getReference().child(image_path);

            storage_reference.putFile(image).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    storage_reference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            image_url[0] = uri.toString();
                            Log.e("URL" , image_url[0]);
                        }
                    });
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(context, "Product Image Upload Failed", Toast.LENGTH_SHORT).show();
                }
            });

        }
        catch (Exception e){
            Toast.makeText(context, "Exception : " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
        return image_url[0];
    }


}
