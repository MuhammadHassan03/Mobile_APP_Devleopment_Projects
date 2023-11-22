package com.palmsolutions.farmconnect;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class Company_User_Fragment extends Fragment {
    private FirebaseAuth auth;
    private FirebaseUser user;
    private DatabaseReference database;
    private StorageReference storage;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        database = FirebaseDatabase.getInstance().getReference();
        storage = FirebaseStorage.getInstance().getReference();

        View view = inflater.inflate(R.layout.company_user_fragment, container, false);

        EditText company_user_name_edit_text = view.findViewById(R.id.company_user_name_edit_text);
        EditText company_user_email_edit_text = view.findViewById(R.id.company_user_email_edit_text);
        EditText company_user_city_edit_text = view.findViewById(R.id.company_user_city_edit_text);

        fetch_data_from_firebase();

        Button company_user_save_changes_button = view.findViewById(R.id.company_user_save_changes_button);
        company_user_save_changes_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try{
                    String Name = company_user_name_edit_text.getText().toString();
                    String Email = company_user_email_edit_text.getText().toString();
                    String City = company_user_city_edit_text.getText().toString();

                    if(Name.equals("") || Email.equals("") || City.equals("")){
                        Toast.makeText(getContext(), "Please Fill All Fields First", Toast.LENGTH_SHORT).show();
                    }
                    else{

                    }
                }
                catch (Exception e){
                    Toast.makeText(getContext(), "User Profile Update Failed", Toast.LENGTH_SHORT).show();
                }
            }
        });
        return view;
    }

    private void fetch_data_from_firebase() {
        ValueEventListener userEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User userData = snapshot.child("Users").child(user.getUid()).getValue(User.class);

                TextView company_user_username_text_view = getView().findViewById(R.id.company_user_username_text_view);
                EditText company_user_email_edit_text = getView().findViewById(R.id.company_user_email_edit_text);
                EditText company_user_city_edit_text = getView().findViewById(R.id.company_user_city_edit_text);
                EditText company_user_name_edit_text = getView().findViewById(R.id.company_user_name_edit_text);

                if (userData != null) {
                    if(userData.getUsername() != null){
                        company_user_username_text_view.setText(userData.getUsername());
                    }
                    if(userData.getUsername() == null){
                        company_user_username_text_view.setText("Set Username Here");
                    }
                    if(userData.getName() != null){
                        company_user_name_edit_text.setText(userData.getName());
                    }
                    if(userData.getName() == null){
                        company_user_name_edit_text.setText("Enter Your Name e.g Farmers etc");
                    }
                    if(userData.getEmail() != null){
                        company_user_email_edit_text.setText(userData.getEmail());
                    }
                    if(userData.getEmail() == null){
                        company_user_email_edit_text.setText("Enter Your Email Here e.g example@example.com");
                    }
                    if(userData.getCity() != null){
                        company_user_city_edit_text.setText(userData.getCity());
                    }
                    if(userData.getCity() == null){
                        company_user_city_edit_text.setText("Enter Your City Here e.g Jhelum etc");
                    }

                }
                else{
                    company_user_username_text_view.setHint("Enter Your Name e.g Hassan");
                    company_user_email_edit_text.setHint("Enter Your Email e.g example@example.com");
                    company_user_city_edit_text.setHint("Enter Your City Name e.g Lahore, Jhelum etc");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        };

        database.addValueEventListener(userEventListener);
    }



}
