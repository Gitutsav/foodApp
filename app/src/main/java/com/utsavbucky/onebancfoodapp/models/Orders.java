package com.utsavbucky.onebancfoodapp.models;

import java.util.ArrayList;
import java.util.List;

public class Orders {

    public ArrayList<Dishes> orderDishesList ;
    public String orderId ;
    public int orderPrice;
    public String orderDate;

    public Orders(ArrayList<Dishes> orderDishesList, String orderId, int orderPrice, String orderDate) {
        this.orderDishesList = orderDishesList;
        this.orderId = orderId;
        this.orderPrice = orderPrice;
        this.orderDate = orderDate;
    }
}
