package com.palmsolutions.farmconnect;

import android.content.Context;
import android.content.Intent;
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
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Sign_up_fragment extends Fragment {
    private FirebaseAuth mAuth;
    private DatabaseReference database;
    private Context context;
    public Sign_up_fragment(Context context){
        this.context = context;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_sign_up,container,false);
        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance().getReference();

        EditText SignupEmailInput = view.findViewById(R.id.SignupEmailInput);
        EditText SignupPassInput = view.findViewById(R.id.SignupPasswordInput);
        EditText UserName = view.findViewById(R.id.username_sign_up_edit_view);

        ConstraintLayout error_constraint_layout = view.findViewById(R.id.error_constraint_layout);
        Button signupBtn = view.findViewById(R.id.signupbtn);
        signupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String accountType = getArguments().getString("accountType");
                String email = SignupEmailInput.getText().toString();
                String password = SignupPassInput.getText().toString();
                String username = UserName.getText().toString();
                mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            add_data_to_database(error_constraint_layout, view,username, email, password, accountType);
                        }
                        else{
                            error_constraint_layout.setVisibility(View.VISIBLE);
                            Exception exception = task.getException();
                            TextView ToastText = view.findViewById(R.id.ToastText);
                            ToastText.setText(exception.getMessage());
                        }
                    }
                });
            };
        });

        TextView AlreadyAccount = view.findViewById(R.id.AlreadyAccount);
        AlreadyAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                show_sign_in_activity();            }
        });
        return view;
    }
    protected void add_data_to_database(ConstraintLayout error_constraint_layout, View view, String username, String email, String password, String accountType){

        error_constraint_layout.setVisibility(View.GONE);
        FirebaseUser firebaseUser = mAuth.getCurrentUser();

        User user = new User(firebaseUser.getUid(), username, email, password, accountType);
        database.child("Users").child(firebaseUser.getUid()).setValue(user);
        startActivity(new Intent(context, Home.class));
        getActivity().finish();
    }
    protected void show_sign_in_activity(){
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        Sign_in_Fragment signInFragment = new Sign_in_Fragment(context);
        transaction.replace(R.id.Authentication, signInFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}