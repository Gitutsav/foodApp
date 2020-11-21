package com.utsavbucky.onebancfoodapp.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.bumptech.glide.Glide;
import com.utsavbucky.onebancfoodapp.MenuActivity;
import com.utsavbucky.onebancfoodapp.R;
import com.utsavbucky.onebancfoodapp.models.Category;

import java.util.ArrayList;
import java.util.Objects;

public class ViewPagerAdapter extends PagerAdapter {
    Context context;
    ArrayList<Category> categoryList = new ArrayList<>();
    LayoutInflater mLayoutInflater;

    public ViewPagerAdapter(Context context, ArrayList<Category> categoryList) {
        this.context = context;
        this.categoryList = categoryList;
        mLayoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return categoryList.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == ((FrameLayout) object);
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, final int position) {
        View itemView = mLayoutInflater.inflate(R.layout.category_viewpager_item, container, false);
        ImageView imageView = itemView.findViewById(R.id.image_food);
        FrameLayout parentlayout = itemView.findViewById(R.id.parent_layout);

        parentlayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, MenuActivity.class);
                intent.putExtra("category_id",categoryList.get(position).getCategoryId());
                context.startActivity(intent);
            }
        });
        TextView textView=itemView.findViewById(R.id.name);
        textView.setText(categoryList.get(position).getCategoryName());
        try{
            Glide.with(context).load(categoryList.get(position).getCategoryImage()).into(imageView);
        }catch (Exception e){
            e.printStackTrace();
        }
        Objects.requireNonNull(container).addView(itemView);

        return itemView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {

        container.removeView((FrameLayout) object);
    }
}
