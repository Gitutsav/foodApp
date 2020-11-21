package com.utsavbucky.onebancfoodapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.utsavbucky.onebancfoodapp.R;
import com.utsavbucky.onebancfoodapp.models.*;

import java.util.List;

public class DishAdapter extends RecyclerView.Adapter<DishAdapter.MyViewHolder> {
    private List<Dishes> dishesList;
    Context context;

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView dishName;
        ImageView dishImage;
        FrameLayout parentLayout;

        MyViewHolder(View view) {
            super(view);
            dishImage = view.findViewById(R.id.dish_img);
            dishName = view.findViewById(R.id.dish_name);
            parentLayout = view.findViewById(R.id.parent_layout);
        }
    }

    public DishAdapter(Context context, List<Dishes> dishesList) {
        this.context = context;
        this.dishesList = dishesList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.dishes_item, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Dishes dish = dishesList.get(position);
        holder.dishName.setText(dish.dishName);
        try{
            Glide.with(context).load(dishesList.get(position).getDishImg()).into(holder.dishImage);
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    @Override
    public int getItemCount() {
        return dishesList.size();
    }
}