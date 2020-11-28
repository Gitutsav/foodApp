package com.utsavbucky.onebancfoodapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.utsavbucky.onebancfoodapp.adapters.MenusAdapter;
import com.utsavbucky.onebancfoodapp.models.Dishes;
import com.utsavbucky.onebancfoodapp.utils.Util;

import java.io.Serializable;
import java.util.ArrayList;

public class MenuActivity extends AppCompatActivity {
    RecyclerView menuRecyclerView;
    private MenusAdapter menuAdapter;
    ArrayList<Dishes> menulist = new ArrayList<>();
    ArrayList<Dishes> dishesList = new ArrayList<>();
    ArrayList<Dishes> finalOrderList = new ArrayList<>();
    RelativeLayout checkoutButton;
    int categoryId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        menuRecyclerView = findViewById(R.id.items_list);
        checkoutButton = findViewById(R.id.order_button);
        Intent intent = getIntent();
        categoryId = intent.getIntExtra("category_id",0);
        setMenuList();

        dishesList = Util.getDishesList(MenuActivity.this);
        for(int i=0;i<dishesList.size();i++){
           if(dishesList.get(i).dishCategory == categoryId){
               menulist.add(dishesList.get(i));
           }
        }

        checkoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finalOrderList.clear();
                for(int i=0;i<menulist.size();i++)
                {
                    if(menulist.get(i).quantity>0) {
                        finalOrderList.add(menulist.get(i));
                    }
                }
                if(finalOrderList.size()>0){
                    Intent intent = new Intent(MenuActivity.this, checkout.class);
                    intent.putExtra("orderList", (Serializable) finalOrderList);
                    startActivity(intent);
                }

            }
        });
    }

    private void setMenuList() {

        menuAdapter = new MenusAdapter(MenuActivity.this,menulist);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        menuRecyclerView.setLayoutManager(layoutManager);
        menuRecyclerView.setItemAnimator(new DefaultItemAnimator());
        menuRecyclerView.setAdapter(menuAdapter);
    }
}