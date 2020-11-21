package com.utsavbucky.onebancfoodapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.utsavbucky.onebancfoodapp.adapters.OrdersAdapter;
import com.utsavbucky.onebancfoodapp.fragments.OrderConfirmationDialog;
import com.utsavbucky.onebancfoodapp.models.Dishes;
import com.utsavbucky.onebancfoodapp.models.Orders;
import com.utsavbucky.onebancfoodapp.utils.Util;
import com.utsavbucky.onebancfoodapp.utils.constants;

import java.security.SecureRandom;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class checkout extends AppCompatActivity {
    RecyclerView orderRecyclerView;
    private OrdersAdapter orderAdapter;
    ArrayList<Dishes> orderlist = new ArrayList<>();
    ArrayList<Dishes> disheslist = new ArrayList<>();

    SharedPreferences ordersSharedPreferences, dishSharedPreferences;
    RelativeLayout orderButton;
    TextView orderQuantity, orderPrice;
    int nos, total;
    static final String AB = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
    static SecureRandom rnd = new SecureRandom();
    ArrayList<Orders> previousOrdersList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);
        orderRecyclerView = findViewById(R.id.orders_list);
        orderButton = findViewById(R.id.checkout_button);
        orderPrice = findViewById(R.id.order_price);
        orderQuantity = findViewById(R.id.order_quantity);

        Intent i = getIntent();
        orderlist = (ArrayList<Dishes>) i.getSerializableExtra("orderList");
        disheslist = Util.getDishesList(checkout.this);
        setOrderList();

        previousOrdersList = Util.getOrdersList(checkout.this);
        orderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String orderId = generateOrderId(10);
                DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
                Date date = new Date();
                String currentDate = dateFormat.format(date);
                Orders currentOrder = new Orders(orderlist,orderId,total,currentDate);

                if(previousOrdersList!=null && previousOrdersList.size()>0){
                previousOrdersList.add(currentOrder);
                } else {
                    previousOrdersList = new ArrayList<>();
                    previousOrdersList.add(currentOrder);
                }

                ordersSharedPreferences = getSharedPreferences(constants.ORDERS_LIST,0);

                SharedPreferences.Editor editorOrders = ordersSharedPreferences.edit();
                editorOrders.putString("ordersList",new Gson().toJson(previousOrdersList));
                editorOrders.apply();

                for(int i=0;i<orderlist.size();i++)    {
                    nos = nos + orderlist.get(i).quantity;
                    total = total + orderlist.get(i).quantity*orderlist.get(i).price;

                    for(int j=0; j<disheslist.size();j++){
                        if(orderlist.get(i).dishId==disheslist.get(j).dishId){
                            disheslist.get(j).soldQuantity = disheslist.get(j).soldQuantity + orderlist.get(i).quantity;
                            break;
                        }
                    }
                }

                dishSharedPreferences=getSharedPreferences(constants.DISHES_LIST,0);

                SharedPreferences.Editor editorDishes = dishSharedPreferences.edit();
                editorDishes.putString("dishesList",new Gson().toJson(disheslist));
                editorDishes.apply();
                showOrderConfirmationDialog();

            }

        });
    }

    private void setOrderList() {
         for(int i=0;i<orderlist.size();i++)    {
            nos = nos + orderlist.get(i).quantity;
            total = total + orderlist.get(i).quantity*orderlist.get(i).price;

        }
        orderAdapter = new OrdersAdapter(orderlist);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        orderRecyclerView.setLayoutManager(layoutManager);
        orderRecyclerView.setItemAnimator(new DefaultItemAnimator());
        orderRecyclerView.setAdapter(orderAdapter);
        orderQuantity.setText(""+nos);
        orderPrice.setText("Rs."+total);
    }

    public void showOrderConfirmationDialog(){

            DialogFragment orderDialog = new OrderConfirmationDialog();
            orderDialog.show(getSupportFragmentManager(), orderDialog.getTag());
            orderDialog.setCancelable(false);
    }

    String generateOrderId(int len){
        StringBuilder sb = new StringBuilder(len);
        for(int i = 0; i < len; i++)
            sb.append(AB.charAt(rnd.nextInt(AB.length())));
        return sb.toString();
    }
}