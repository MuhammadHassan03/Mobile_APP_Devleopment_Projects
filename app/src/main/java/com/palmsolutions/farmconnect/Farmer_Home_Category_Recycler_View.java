package com.palmsolutions.farmconnect;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;

public class Farmer_Home_Category_Recycler_View extends RecyclerView.Adapter<Farmer_Home_Category_Recycler_View.ViewHolder> {

    ArrayList<String> items;
    Context context;

    public Farmer_Home_Category_Recycler_View(Context context){
        this.context = context;
        String[] categories = context.getResources().getStringArray(R.array.farmer_category);
        items = new ArrayList<>(Arrays.asList(categories));
    }
    @NonNull
    @Override
    public Farmer_Home_Category_Recycler_View.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.farmer_category_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Farmer_Home_Category_Recycler_View.ViewHolder holder, int position) {
        holder.farmer_category_item_button.setText(items.get(position));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        Button farmer_category_item_button;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            farmer_category_item_button = itemView.findViewById(R.id.farmer_category_item_button);
        }
    }
}
