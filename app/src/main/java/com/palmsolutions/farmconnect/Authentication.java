package com.palmsolutions.farmconnect;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Authentication extends AppCompatActivity {

    private FirebaseAuth auth;
    private FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authentication);


        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();

        if(user == null){
            Show_account_type_fragment();
        }
        else{
            show_sign_in_fragment();
        }
    }

    protected void Show_account_type_fragment(){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        Account_type_Fragment accountTypeFragment = new Account_type_Fragment(this);
        transaction.replace(R.id.Authentication, accountTypeFragment);
        transaction.commit();

    }

    protected void show_sign_in_fragment(){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        Sign_in_Fragment signInFragment = new Sign_in_Fragment(this);
        transaction.replace(R.id.Authentication, signInFragment);
        transaction.commit();
    }
}