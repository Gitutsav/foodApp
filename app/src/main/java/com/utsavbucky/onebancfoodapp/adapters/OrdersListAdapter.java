package com.utsavbucky.onebancfoodapp.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.utsavbucky.onebancfoodapp.R;
import com.utsavbucky.onebancfoodapp.models.Dishes;

import java.util.List;

public class OrdersListAdapter extends RecyclerView.Adapter<OrdersListAdapter.MyViewHolder> {
    private List<Dishes> dishesList;
    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView dishName, quantity, itemTotal, price;
        ImageView dishImage;

        MyViewHolder(View view) {
            super(view);
            dishImage = view.findViewById(R.id.item_img);
            dishName = view.findViewById(R.id.item_name);
            quantity = view.findViewById(R.id.quantity);
            itemTotal = view.findViewById(R.id.item_total);
            price = view.findViewById(R.id.item_price);
        }
    }
    public OrdersListAdapter(List<Dishes> dishesList)
    {
        this.dishesList = dishesList;
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.checkout_item, parent, false);
        return new MyViewHolder(itemView);
    }
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Dishes dish = dishesList.get(position);
        holder.dishName.setText(dish.dishName);
        holder.price.setText("Rs."+String.valueOf(dish.price));
        int itemCost = 0;
        holder.quantity.setText(dish.quantity+"");
        itemCost = dish.quantity  * dish.price;
        String tot = "Rs."+itemCost;
        holder.itemTotal.setText(tot);

    }
    @Override
    public int getItemCount() {
        return dishesList.size();
    }
}