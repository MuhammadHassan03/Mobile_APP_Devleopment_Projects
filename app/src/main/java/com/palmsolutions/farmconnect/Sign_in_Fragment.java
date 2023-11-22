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
import android.view.View;

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

public class Sign_in_Fragment extends Fragment {
    private FirebaseAuth auth;
    private FirebaseUser user;
    Context context;

    public Sign_in_Fragment(Context context){
        this.context = context;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_sign_in, container, false);
        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();

        ConstraintLayout warningLayout = view.findViewById(R.id.warningLayout);
        TextView ToastTextLogin = view.findViewById(R.id.ToastTextLogin);

        if(user == null){
            EditText loginEmailInput = view.findViewById(R.id.loginEmailInput);
            EditText loginPasswordInput = view.findViewById(R.id.loginPasswordInput);

            Button loginBtn = view.findViewById(R.id.loginBtn);
            loginBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String email = loginEmailInput.getText().toString();
                    String pass = loginPasswordInput.getText().toString();
                    if (email.isEmpty() || pass.isEmpty()) {
                        Toast.makeText(context, "Email and password are required", Toast.LENGTH_SHORT).show();
                    } else {
                        auth.signInWithEmailAndPassword(email, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()){
                                    warningLayout.setVisibility(View.GONE);
                                    startActivity(new Intent(context, Home.class));
                                    getActivity().finish();
                                }
                                else{
                                    warningLayout.setVisibility(View.VISIBLE);
                                    ToastTextLogin.setText("Email or password not matched");
                                }
                            }
                        });
                    }
                }
            });

            TextView ForgetPasswordLogin = view.findViewById(R.id.ForgetPasswordLogin);
            ForgetPasswordLogin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    show_forget_password_activity();
                }
            });
        } else {
            startActivity(new Intent(context, Home.class));
            getActivity().finish();
        }

        return view;
    }

    protected void show_forget_password_activity(){
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        Forget_Password_Fragment forgetPasswordFragment = new Forget_Password_Fragment(context);
        transaction.replace(R.id.Authentication, forgetPasswordFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}
