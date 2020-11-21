package com.utsavbucky.onebancfoodapp.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.utsavbucky.onebancfoodapp.models.Category;
import com.utsavbucky.onebancfoodapp.models.Dishes;
import com.utsavbucky.onebancfoodapp.models.Orders;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import static android.content.Context.MODE_PRIVATE;

public class Util {

    public static ArrayList<Dishes> getDishesList(Context context) {

        Gson gson = new Gson();
        SharedPreferences applicationPrefs= context.getSharedPreferences(constants.DISHES_LIST, MODE_PRIVATE);
        String json = applicationPrefs.getString("dishesList", "");
        TypeToken<ArrayList<Dishes>> token = new TypeToken<ArrayList<Dishes>>() {
        };
        ArrayList<Dishes> dishesList = gson.fromJson(json, token.getType());
        return dishesList;

    }

    public static ArrayList<Orders> getOrdersList(Context context) {

        Gson gson = new Gson();
        SharedPreferences applicationPrefs= context.getSharedPreferences(constants.ORDERS_LIST, MODE_PRIVATE);
        String json = applicationPrefs.getString("ordersList", "");
        TypeToken<ArrayList<Orders>> token = new TypeToken<ArrayList<Orders>>() {
        };
        ArrayList<Orders> ordersList = gson.fromJson(json, token.getType());
        return ordersList;

    }

    public static ArrayList<Category> getCategoryList(Context context) {

        Gson gson = new Gson();
        SharedPreferences applicationPrefs= context.getSharedPreferences(constants.CATEGORIES_LIST, MODE_PRIVATE);
        String json = applicationPrefs.getString("categoryList", "");
        TypeToken<ArrayList<Category>> token = new TypeToken<ArrayList<Category>>() {
        };
        ArrayList<Category> categoryList = gson.fromJson(json, token.getType());
        return categoryList;

    }

    public static String convertDateToString(Date givenDate) {
        String dateTime="";
        if (givenDate!=null){
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd-dd_HH-mm-ss");
            dateTime = dateFormat.format(givenDate);
        }

        return  dateTime;


    }


}
