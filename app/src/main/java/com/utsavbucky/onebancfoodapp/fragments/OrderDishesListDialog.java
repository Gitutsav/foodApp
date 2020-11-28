package com.utsavbucky.onebancfoodapp.fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.utsavbucky.onebancfoodapp.MainActivity;
import com.utsavbucky.onebancfoodapp.R;
import com.utsavbucky.onebancfoodapp.adapters.OrdersAdapter;
import com.utsavbucky.onebancfoodapp.adapters.OrdersListAdapter;
import com.utsavbucky.onebancfoodapp.models.Dishes;
import com.utsavbucky.onebancfoodapp.models.Orders;

import java.util.ArrayList;


public class OrderDishesListDialog extends DialogFragment {
    RelativeLayout dismissButton ;
    private View dialogView;
    RecyclerView ordersListRecyclerView;
    private OrdersListAdapter orderAdapter;
    ArrayList<Dishes> orderlist = new ArrayList<>();
    int nos, total;
    TextView orderQuantity, orderPrice;
    Context mContext;
    Orders selectedOrder;
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        mContext = getActivity();
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext, R.style.Dialog);
        final LayoutInflater inflater = LayoutInflater.from(mContext);
        dialogView = inflater.inflate(R.layout.order_list_dialog,null);
        dismissButton = dialogView.findViewById(R.id.dismiss_btn);
        ordersListRecyclerView = dialogView.findViewById(R.id.orders_list);
        orderPrice = dialogView.findViewById(R.id.order_price);
        orderQuantity = dialogView.findViewById(R.id.order_quantity);
        dismissButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDialog().dismiss();
            }
        });

        Gson gson = new Gson();
        String orderString = getArguments().getString("selected_order");
        selectedOrder = gson.fromJson(orderString, Orders.class);

        setOrderList(selectedOrder);
        builder.setView(dialogView);


        return builder.create();
    }
    private void setOrderList(Orders selectedOrder) {
        orderlist = selectedOrder.orderDishesList;
        for(int i=0;i<orderlist.size();i++)    {
            nos = nos + orderlist.get(i).quantity;
            for (double x : orderlist.get(i).quantityQueue) {
                total += x;
            }
        }
        orderAdapter = new OrdersListAdapter(orderlist);
        LinearLayoutManager layoutManager = new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false);
        ordersListRecyclerView.setLayoutManager(layoutManager);
        ordersListRecyclerView.setItemAnimator(new DefaultItemAnimator());
        ordersListRecyclerView.setAdapter(orderAdapter);
        orderQuantity.setText(""+nos);
        orderPrice.setText("Rs."+total);
    }
    @Override
    public void onStart() {
        super.onStart();
        Dialog dialog = getDialog();
        if (dialog != null) {
            Window window = getDialog().getWindow();
            Point size = new Point();
            Display display = window.getWindowManager().getDefaultDisplay();
            display.getSize(size);
            window.setLayout((int) (size.x * 0.85), WindowManager.LayoutParams.WRAP_CONTENT);
        }
    }
}
