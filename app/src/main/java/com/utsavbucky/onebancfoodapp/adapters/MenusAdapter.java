package com.utsavbucky.onebancfoodapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.utsavbucky.onebancfoodapp.R;
import com.utsavbucky.onebancfoodapp.models.Dishes;

import java.util.ArrayList;

public class MenusAdapter extends RecyclerView.Adapter<MenusAdapter.MyViewHolder> {
    private ArrayList<Dishes> dishesList;
    int q = 0;
    Context mContext;
    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView dishName, quantity, plus, minus, price;
        ImageView dishImage;

        MyViewHolder(View view) {
            super(view);
            dishImage = view.findViewById(R.id.item_img);
            dishName = view.findViewById(R.id.item_name);
            quantity = view.findViewById(R.id.item_quantity);
            plus = view.findViewById(R.id.plus);
            price = view.findViewById(R.id.item_price);
            minus = view.findViewById(R.id.minus);
        }
    }
    public MenusAdapter(Context context, ArrayList<Dishes> dishesList)
    {
        this.dishesList = dishesList;
        this.mContext = context;
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.menu_item, parent, false);
        return new MyViewHolder(itemView);
    }
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Dishes dish = dishesList.get(position);
        holder.dishName.setText(dish.dishName);
        holder.price.setText("Rs."+String.valueOf(dish.price));
        holder.minus.setVisibility(View.GONE);
        try{
            Glide.with(mContext).load(dishesList.get(position).getDishImg()).into(holder.dishImage);
        }catch (Exception e){
            e.printStackTrace();
        }
        q = dish.quantity;
        dish.quantityQueue.clear();
        holder.plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(dish.quantity==0) {
                    dish.quantity += 1;
                    dish.quantityQueue.add(dish.price);
                    holder.quantity.setText(String.valueOf(dish.quantity));
                    holder.minus.setVisibility(View.VISIBLE);
                } else if(dish.quantity>0) {
                    dish.quantity += 1;
                    dish.quantityQueue.add(dish.price);
                    holder.quantity.setText(String.valueOf(dish.quantity));
                }
            }
        });
        holder.minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(dish.quantity>1) {
                    dish.quantity -= 1;
                    dish.quantityQueue.poll();
                    holder.quantity.setText(String.valueOf(dish.quantity));
                } else if(dish.quantity==1) {
                    dish.quantity -= 1;
                    dish.quantityQueue.poll();
                    holder.quantity.setText(String.valueOf(dish.quantity));
                    holder.minus.setVisibility(View.GONE);
                }

            }
        });

    }
    @Override
    public int getItemCount() {
        return dishesList.size();
    }
}