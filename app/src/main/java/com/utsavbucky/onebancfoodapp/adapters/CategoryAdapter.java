package com.utsavbucky.onebancfoodapp.adapters;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.utsavbucky.onebancfoodapp.MenuActivity;
import com.utsavbucky.onebancfoodapp.R;
import com.utsavbucky.onebancfoodapp.models.Category;
import com.utsavbucky.onebancfoodapp.models.Dishes;

import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.MyViewHolder> {
    private List<Category> categoryList;
    Context context;

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView categoryName;
        ImageView categoryImage;
        FrameLayout parentLayout;

        MyViewHolder(View view) {
            super(view);
            categoryImage = view.findViewById(R.id.image_food);
            categoryName = view.findViewById(R.id.name);
            parentLayout = view.findViewById(R.id.parent_layout);
        }
    }

    public CategoryAdapter(Context context, List<Category> categoryList) {
        this.context = context;
        this.categoryList = categoryList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.category_viewpager_item, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        Log.d("position",position+"  position");
        int positionInList = position % categoryList.size();

        Category category = categoryList.get(positionInList);
        holder.categoryName.setText(category.getCategoryName());
        try{
            Glide.with(context).load(category.getCategoryImage()).into(holder.categoryImage);
        }catch (Exception e){
            e.printStackTrace();
        }

        holder.parentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, MenuActivity.class);
                intent.putExtra("category_id",categoryList.get(positionInList).getCategoryId());
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        Log.d("test",categoryList.size()+"  size");
        return categoryList == null ? 0 : categoryList.size() * 2 + 1;
    }
}