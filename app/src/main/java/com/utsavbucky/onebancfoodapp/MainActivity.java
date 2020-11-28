package com.utsavbucky.onebancfoodapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;
import androidx.viewpager.widget.ViewPager;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import com.google.gson.Gson;
import com.utsavbucky.onebancfoodapp.adapters.CategoryAdapter;
import com.utsavbucky.onebancfoodapp.adapters.DishAdapter;
import com.utsavbucky.onebancfoodapp.adapters.PreviousOrdersAdapter;
import com.utsavbucky.onebancfoodapp.adapters.ViewPagerAdapter;
import com.utsavbucky.onebancfoodapp.databinding.ActivityMainBinding;
import com.utsavbucky.onebancfoodapp.models.Category;
import com.utsavbucky.onebancfoodapp.models.Dishes;
import com.utsavbucky.onebancfoodapp.models.Orders;
import com.utsavbucky.onebancfoodapp.utils.BaseActivity;
import com.utsavbucky.onebancfoodapp.utils.LocaleManager;
import com.utsavbucky.onebancfoodapp.utils.Util;
import com.utsavbucky.onebancfoodapp.utils.constants;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends BaseActivity {
    private ActivityMainBinding binding;
    private int currentPage=0;
    private Timer timer;

    RecyclerView topDishes, previousOrders;
    private DishAdapter topDishesAdapter;

    ArrayList<Dishes> dishesList = new ArrayList<>();
    ArrayList<Category> categoryList = new ArrayList<>();
    SharedPreferences dishSharedPreferences, categorySharedPreferences;
    ArrayList<Orders> ordersList = new ArrayList<>();
    private PreviousOrdersAdapter previousOrdersAdapter;
    private BroadcastReceiver reciever = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if(intent.getAction().equals("order_placed")){
                setTopDishes();
                setPreviousOrders();
            }
        }
    };



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= DataBindingUtil.setContentView(this,R.layout.activity_main);
        getSupportActionBar().setTitle("Home");
        LocalBroadcastManager.getInstance(this).registerReceiver(reciever, new IntentFilter("order_placed"));

        topDishes = findViewById(R.id.top_dishes);
        previousOrders = findViewById(R.id.previous_orders);
        /*if(Util.getCategoryList(MainActivity.this)== null ||
                Util.getDishesList(MainActivity.this) == null)
        {*/
            prepareData();
       // }
        setCategoriesList();
        setTopDishes();
        setPreviousOrders();

        final Handler handler = new Handler();
        final int delay = 30000;

        handler.postDelayed(new Runnable() {
            public void run() {
                updateDishPrice();
                handler.postDelayed(this, delay);
            }
        }, delay);
    }

    public void setCategoriesList(){
        categoryList = Util.getCategoryList(MainActivity.this);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);
        binding.categories.setLayoutManager(layoutManager);
        binding.categories.setItemAnimator(new DefaultItemAnimator());
        binding.categories.setAdapter(new CategoryAdapter(this, categoryList));
        layoutManager.scrollToPosition(((Integer.MAX_VALUE/2)-((Integer.MAX_VALUE/2)%categoryList.size())));


    }

    public void prepareData(){
        categoryList.add(new Category("https://d4t7t8y8xqo0t.cloudfront.net/resized/750X436/eazytrendz%2F2137%2Ftrend20181213060017.jpg", constants.NORTH_INDIAN,"North Indian"));
        categoryList.add(new Category("https://www.kohinoorfoods.co.uk/wp-content/uploads/2020/01/indo-chinese-food.jpg",constants.CHINESE,"Chinese"));
        categoryList.add(new Category("https://nomadparadise.com/wp-content/uploads/2020/05/mediterranean-food-011.jpg",constants.MEDITERRANEAN,"Mediterranean"));
        categoryList.add(new Category("https://d4t7t8y8xqo0t.cloudfront.net/resized/750X436/eazytrendz%2F2033%2Ftrend20180920125236.jpg",constants.SOUTH_INDIAN,"South Indian"));
        categoryList.add(new Category("https://restaurantindia.s3.ap-south-1.amazonaws.com/s3fs-public/content10735.jpg",constants.ITALIAN,"Italian"));


        dishesList.add(new Dishes("https://cdn2.foodviva.com/static-content/food-images/snacks-recipes/khaman-dhokla-recipe/khaman-dhokla-recipe.jpg","Dhokla",1,constants.NORTH_INDIAN,100.0,0,0, new PriorityQueue<Double>()));
        dishesList.add(new Dishes("https://sukhis.com/wp-content/uploads/2020/01/Dosa-500x400.jpg","Dosa",2, constants.SOUTH_INDIAN,80.0,0,0,new PriorityQueue<Double>()));
        dishesList.add(new Dishes("https://farm1.staticflickr.com/269/19741628821_7ff0dd8b7c_o.jpg","Paneer pasanda",3, constants.NORTH_INDIAN, 200.0,0,0,new PriorityQueue<Double>()));
        dishesList.add(new Dishes("https://farm1.staticflickr.com/269/19741628821_7ff0dd8b7c_o.jpg","Pizza",4, constants.ITALIAN, 350.0,0,0, new PriorityQueue<Double>()));
        dishesList.add(new Dishes("https://simply-delicious-food.com/wp-content/uploads/2015/07/Bacon-and-cheese-burgers-3-500x500.jpg","Burger",5, constants.CHINESE,120.0,0,0, new PriorityQueue<Double>()));
        dishesList.add(new Dishes("https://cdn2.foodviva.com/static-content/food-images/snacks-recipes/khaman-dhokla-recipe/khaman-dhokla-recipe.jpg","Spanakopita",6,constants.MEDITERRANEAN,100.0,0,0, new PriorityQueue<Double>()));
        dishesList.add(new Dishes("https://sukhis.com/wp-content/uploads/2020/01/Dosa-500x400.jpg","Chicken Shawarma",7, constants.MEDITERRANEAN,80.0,0,0 , new PriorityQueue<Double>()));
        dishesList.add(new Dishes("https://farm1.staticflickr.com/269/19741628821_7ff0dd8b7c_o.jpg","Paneer kofta",8, constants.NORTH_INDIAN, 200.0,0,0, new PriorityQueue<Double>()));
        dishesList.add(new Dishes("https://farm1.staticflickr.com/269/19741628821_7ff0dd8b7c_o.jpg","Garlic Bread",9, constants.ITALIAN, 350.0,0,0, new PriorityQueue<Double>()));
        dishesList.add(new Dishes("https://simply-delicious-food.com/wp-content/uploads/2015/07/Bacon-and-cheese-burgers-3-500x500.jpg","Chowmein",0, constants.CHINESE,120.0,0,0, new PriorityQueue<Double>()));

        dishSharedPreferences=getSharedPreferences(constants.DISHES_LIST,0);
        categorySharedPreferences = getSharedPreferences(constants.CATEGORIES_LIST,0);

        SharedPreferences.Editor editorDishes = dishSharedPreferences.edit();
        editorDishes.putString("dishesList",new Gson().toJson(dishesList));
        editorDishes.apply();

        SharedPreferences.Editor editorCategories = categorySharedPreferences.edit();
        editorCategories.putString("categoryList",new Gson().toJson(categoryList));
        editorCategories.apply();

    }

    public void updateDishPrice()
    {
        ArrayList<Dishes> toUpdateDishList = Util.getDishesList(MainActivity.this);
        for(int i = 0;i<toUpdateDishList.size();i++){
            double newPrice = toUpdateDishList.get(i).price + toUpdateDishList.get(i).price*0.01;
            toUpdateDishList.get(i).price = Double.parseDouble(new DecimalFormat("##.##").format(newPrice));
        }
        dishSharedPreferences=getSharedPreferences(constants.DISHES_LIST,0);
        SharedPreferences.Editor editorDishes = dishSharedPreferences.edit();
        editorDishes.putString("dishesList",new Gson().toJson(toUpdateDishList));
        editorDishes.apply();
    }
    public void setTopDishes() {
        if(dishesList!=null && dishesList.size()>0) {
            dishesList.clear();}
        dishesList = Util.getDishesList(MainActivity.this);

        Collections.sort(dishesList, new Comparator<Dishes>() {
            @Override
            public int compare(Dishes d1, Dishes d2) {
                return d2.soldQuantity - d1.soldQuantity;
            }
        });
        List<Dishes> top5Dishes = new ArrayList<>();
        top5Dishes = dishesList.subList(0,5);
        topDishesAdapter = new DishAdapter(MainActivity.this,top5Dishes);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);
        topDishes.setLayoutManager(layoutManager);
        topDishes.setItemAnimator(new DefaultItemAnimator());
        topDishes.setAdapter(topDishesAdapter);

    }

    public void setPreviousOrders() {
        if(ordersList!=null && ordersList.size()>0) {
        ordersList.clear();}
        ordersList = Util.getOrdersList(MainActivity.this);

        if(ordersList!=null && ordersList.size()>0) {
            Collections.reverse(ordersList);
            binding.previousOrderHeader.setVisibility(View.VISIBLE);
            previousOrdersAdapter = new PreviousOrdersAdapter(MainActivity.this,ordersList);
            LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
            previousOrders.setLayoutManager(layoutManager);
            previousOrders.setItemAnimator(new DefaultItemAnimator());
            previousOrders.setAdapter(previousOrdersAdapter);
        }
    }


    private void setNewLocale(AppCompatActivity mContext, @LocaleManager.LocaleDef String language) {
        LocaleManager.setNewLocale(this, language);
        Intent intent = mContext.getIntent();
        startActivity(intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK));
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.laungua_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.one:
                setNewLocale(this, LocaleManager.ENGLISH);
                return true;
            case R.id.two:
                setNewLocale(this, LocaleManager.HINDI);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
