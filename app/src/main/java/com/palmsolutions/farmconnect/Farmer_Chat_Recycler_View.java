package com.palmsolutions.farmconnect;

import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class Farmer_Chat_Recycler_View extends RecyclerView.Adapter<Farmer_Chat_Recycler_View.ViewHolder> {

    @NonNull
    @Override
    public Farmer_Chat_Recycler_View.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull Farmer_Chat_Recycler_View.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }



}
