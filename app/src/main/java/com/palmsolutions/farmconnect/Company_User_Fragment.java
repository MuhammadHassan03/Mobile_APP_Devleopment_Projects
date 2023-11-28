package com.palmsolutions.farmconnect;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;
import java.util.Map;

public class Company_User_Fragment extends Fragment {
    private FirebaseAuth auth;
    private FirebaseUser user;
    private DatabaseReference database;
    private StorageReference storage;
    private Uri image_uri;
    private User userData;
    private static int PICK_IMAGE_REQUEST;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        database = FirebaseDatabase.getInstance().getReference();
        storage = FirebaseStorage.getInstance().getReference();
        PICK_IMAGE_REQUEST = 1;
        FullScreenUtil.hideSystemUI(requireActivity().getWindow().getDecorView());

        View view = inflater.inflate(R.layout.company_user_fragment, container, false);

        EditText company_user_name_edit_text = view.findViewById(R.id.company_user_name_edit_text);
        EditText company_user_email_edit_text = view.findViewById(R.id.company_user_email_edit_text);
        EditText company_user_city_edit_text = view.findViewById(R.id.company_user_city_edit_text);
        TextView company_user_change_image_text_view = view.findViewById(R.id.company_user_change_image_text_view);

        fetch_data_from_firebase();

        company_user_change_image_text_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                open_gallery();
            }
        });

        Button company_user_save_changes_button = view.findViewById(R.id.company_user_save_changes_button);
        company_user_save_changes_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try{
                    company_user_save_changes_button.setText("");
                    String Name = company_user_name_edit_text.getText().toString();
                    String Email = company_user_email_edit_text.getText().toString();
                    String City = company_user_city_edit_text.getText().toString();

                    if(Name.equals("") || Email.equals("") || City.equals("")){
                        Toast.makeText(getContext(), "Please Fill All Fields First", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        ProgressBar company_user_update_progress_bar = getView().findViewById(R.id.company_user_update_progress_bar);
                        company_user_update_progress_bar.setVisibility(View.VISIBLE);
                        String user_pic_url = upload_image_in_firestorage();
                        update_data_in_firebase(Name, Email, City, user_pic_url);
                        company_user_update_progress_bar.setVisibility(View.GONE);
                    }
                    company_user_save_changes_button.setText("SAVE CHANGES");
                }
                catch (Exception e){
                    Toast.makeText(getContext(), "User Profile Update Failed", Toast.LENGTH_SHORT).show();
                }
            }
        });

        ImageView company_navbar_home_btn_fragment = view.findViewById(R.id.company_navbar_home_btn_fragment);
        company_navbar_home_btn_fragment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                Company_Fragment companyFragment = new Company_Fragment(getContext());
                transaction.replace(R.id.Fragment_Home, companyFragment);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });

        Button company_user_logout_button = view.findViewById(R.id.company_user_logout_button);
        company_user_logout_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                getActivity().finish();
                startActivity(new Intent(getContext(), Authentication.class));
            }
        });

        return view;
    }

    private void fetch_data_from_firebase() {
        final long ONE_MEGABYTE = 1024 * 1024;
        ValueEventListener userEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                userData = snapshot.child("Users").child(user.getUid()).getValue(User.class);

                TextView company_user_username_text_view = getView().findViewById(R.id.company_user_username_text_view);
                EditText company_user_email_edit_text = getView().findViewById(R.id.company_user_email_edit_text);
                EditText company_user_city_edit_text = getView().findViewById(R.id.company_user_city_edit_text);
                EditText company_user_name_edit_text = getView().findViewById(R.id.company_user_name_edit_text);
                ImageView company_user_profile_image_view = getView().findViewById(R.id.company_user_profile_image_view);

                StorageReference user_image = storage.child("images/" + user.getUid() + "/user_picture");
                user_image.getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
                    @Override
                    public void onSuccess(byte[] bytes) {
                        Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                        company_user_profile_image_view.setImageBitmap(bitmap);
                        Picasso.get().load("file:///").transform(new CircularTransform()).into(company_user_profile_image_view);
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        company_user_profile_image_view.setImageDrawable(getResources().getDrawable(R.drawable.user_default));

                    }
                });

                if(userData.getUsername() != null){
                    company_user_username_text_view.setText(userData.getUsername());
                }
                if(userData.getUsername() == null){
                    company_user_username_text_view.setText("Set Username Here");
                }
                if(userData.getName() != null){
                    company_user_name_edit_text.setAlpha(0.5f);
                    company_user_name_edit_text.setText(userData.getName());
                }
                if(userData.getName() == null){
                    company_user_name_edit_text.setAlpha(0.5f);
                    company_user_name_edit_text.setHint("Enter Your Name e.g Farmers etc");
                }
                if(userData.getEmail() != null){
                    company_user_name_edit_text.setAlpha(0.5f);
                    company_user_email_edit_text.setText(userData.getEmail());
                    company_user_email_edit_text.setFocusable(false);
                    company_user_email_edit_text.setFocusableInTouchMode(false);
                }
                if(userData.getEmail() == null){
                    company_user_email_edit_text.setAlpha(0.5f);
                    company_user_email_edit_text.setHint("Enter Your Email Here e.g example@example.com");
                }
                if(userData.getCity() != null){
                    company_user_city_edit_text.setAlpha(0.5f);
                    company_user_city_edit_text.setText(userData.getCity());
                }
                if(userData.getCity() == null){
                    company_user_city_edit_text.setAlpha(0.5f);
                    company_user_city_edit_text.setHint("Enter Your City Here e.g Jhelum etc");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        };

        database.addValueEventListener(userEventListener);
    }
    private void update_data_in_firebase(String Name, String Email, String City, String user_pic_url){
        try{

            User update_user = new User(user.getUid(), userData.getUsername(), Email, userData.getPassword(), userData.accountType, Name, City, user_pic_url);
            Map<String, Object> update =  update_user.to_Map();
            database.child("Users").child(user.getUid()).updateChildren(update);
        }
        catch (Exception e){
            Toast.makeText(getContext(), "Internet Error!", Toast.LENGTH_SHORT).show();
        }
    }

    private String upload_image_in_firestorage(){
        final String[] user_pic_url = new String[1];
        StorageReference user_pic = storage.child("images").child(user.getUid()).child("user_picture");

        UploadTask upload_image = user_pic.putFile(image_uri);

        upload_image.addOnSuccessListener(taskSnapshot -> {
            user_pic.getDownloadUrl().addOnSuccessListener(uri -> {
                user_pic_url[0] = uri.toString();
            });
        }).addOnFailureListener(e -> {
            Toast.makeText(getContext(), "Internet Error!", Toast.LENGTH_SHORT).show();
        });
        return user_pic_url[0];
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
            image_uri = data.getData();
            ImageView company_user_profile_image_view = getView().findViewById(R.id.company_user_profile_image_view);
            company_user_profile_image_view.setImageURI(image_uri);
            Picasso.get().load(image_uri).transform(new CircularTransform()).into(company_user_profile_image_view);
        }
    }

}
