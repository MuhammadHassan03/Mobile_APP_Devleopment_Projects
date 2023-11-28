package com.palmsolutions.farmconnect;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.EventListener;

public class Home extends AppCompatActivity {

    private FirebaseAuth auth;
    private FirebaseUser user;
    private DatabaseReference database;
    private String account_type;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FullScreenUtil.hideSystemUI(getWindow().getDecorView());

        setContentView(R.layout.activity_home);
        try {
            auth = FirebaseAuth.getInstance();
            user = auth.getCurrentUser();
            database = FirebaseDatabase.getInstance().getReference();

        if(user!=null){

            fetchUserDataFromDataBase();

        }
        else{
            Toast.makeText(this, "User is not Logged in Please Log in again", Toast.LENGTH_SHORT).show();
        }
        }
        catch (Exception e){
            Toast.makeText(this, "Error Occurred " + e, Toast.LENGTH_LONG).show();
            Log.e("Error", "Occured" + e.getMessage());
        }
    }

    private void fetchUserDataFromDataBase(){
        ProgressBar progress_bar_home = findViewById(R.id.progress_bar_home);
        progress_bar_home.setVisibility(View.VISIBLE);
        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot snapshot1 : snapshot.child("Users").getChildren()){
                    if(snapshot1.getKey().equals(user.getUid())){
                        User user1 = snapshot1.getValue(User.class);
                        account_type = user1.getAccountType();
                        if("Farmer".equals(account_type)){
                            ImageView home_cart_icon = findViewById(R.id.home_cart_icon);
                            home_cart_icon.setVisibility(View.VISIBLE);
                            home_cart_icon.setImageDrawable(getResources().getDrawable(R.drawable.cart));
                            home_cart_icon.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    FragmentManager manager = getSupportFragmentManager();
                                    FragmentTransaction transaction = manager.beginTransaction();
                                    Farmer_Cart_Fragment cart_fragment = new Farmer_Cart_Fragment();
                                    transaction.replace(R.id.Fragment_Home, cart_fragment);
                                    transaction.addToBackStack(null);
                                    transaction.commit();
                                }
                            });
                        }
                        else if("Company".equals(account_type)){
                            ImageView home_cart_icon = findViewById(R.id.home_cart_icon);
                            home_cart_icon.setVisibility(View.GONE);
                        }
                        load_fragment_according_to_account_type();
                        break;
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        };
        database.addValueEventListener(valueEventListener);
    }

    private void load_fragment_according_to_account_type(){
        ProgressBar progress_bar_home = findViewById(R.id.progress_bar_home);
        if("Company".equals(account_type)){
            progress_bar_home.setVisibility(View.VISIBLE);
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            Company_Fragment companyFragment = new Company_Fragment(this);
            transaction.add(R.id.Fragment_Home, companyFragment);
            transaction.commit();
        }
        else if("Farmer".equals(account_type)){
            progress_bar_home.setVisibility(View.VISIBLE);
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            Farmer_Fragment farmerFragment = new Farmer_Fragment(progress_bar_home);
            transaction.add(R.id.Fragment_Home, farmerFragment);
            transaction.commit();
        }
        else{
            Toast.makeText(this, "Please Select Your Correct Account Type", Toast.LENGTH_SHORT).show();
        }
    }
}