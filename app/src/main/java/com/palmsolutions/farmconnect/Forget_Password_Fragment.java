package com.palmsolutions.farmconnect;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Forget_Password_Fragment extends Fragment {
    private DatabaseReference databaseReference;
    private FirebaseAuth mAuth;
    private Context context;
    public Forget_Password_Fragment(Context context){
        this.context = context;
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_forget_password, container, false);
        mAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference();

        EditText forgotEmailInput = view.findViewById(R.id.forgotEmailInput);
        Button forgotBtn = view.findViewById(R.id.forgotBtn);


        forgotBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = forgotEmailInput.getText().toString();
//                mAuth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
//                    @Override
//                    public void onComplete(@NonNull Task<Void> task) {
//                        if (task.isSuccessful()){
//                            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
//                            Toast.makeText(ForgetPassword.this, "User is" + user.getEmail(), Toast.LENGTH_SHORT).show();
//                        }
//                        else{
//                            Toast.makeText(ForgetPassword.this, "User is not registered", Toast.LENGTH_SHORT).show();
//                        }
//                    }
//                });
            }
        });

        return view;
    }
}
