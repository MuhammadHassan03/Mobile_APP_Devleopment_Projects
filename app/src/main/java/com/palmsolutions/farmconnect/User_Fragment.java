package com.palmsolutions.farmconnect;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

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

public class User_Fragment extends Fragment {
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

        View view = inflater.inflate(R.layout.user_fragment, container, false);

        TextView company_user_username_text_view = view.findViewById(R.id.company_user_username_text_view);
        EditText company_user_name_edit_text = view.findViewById(R.id.company_user_name_edit_text);
        EditText company_user_email_edit_text = view.findViewById(R.id.company_user_email_edit_text);
        EditText company_user_city_edit_text = view.findViewById(R.id.company_user_city_edit_text);

        User user_data = fetch_data_from_firebase();

        company_user_username_text_view.setText(user_data.getUsername());
        company_user_name_edit_text.setText(user_data.getName());
        company_user_email_edit_text.setText(user_data.getEmail());
        company_user_city_edit_text.setText(user_data.getCity());

        return view;
    }

    private User fetch_data_from_firebase(){
        final User[] user_data = new User[1];
        final ValueEventListener[] user_event_listener = {new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                user_data[0] = snapshot.child("users").child(user.getUid()).getValue(User.class);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        }};
        database.addValueEventListener(user_event_listener[0]);
        return user_data[0];
    }
}
