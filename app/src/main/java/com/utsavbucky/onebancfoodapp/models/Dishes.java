package com.utsavbucky.onebancfoodapp.models;

import java.io.Serializable;

public class Dishes implements Serializable {
    public String dishImg;
    public String dishName;
    public int dishId;
    public int dishCategory;
    public int price;
    public int quantity;
    public int soldQuantity;

    public String getDishImg() {
        return dishImg;
    }

    public void setDishImg(String dishImg) {
        this.dishImg = dishImg;
    }

    public String getDishName() {
        return dishName;
    }

    public void setDishName(String dishName) {
        this.dishName = dishName;
    }

    public int getDishId() {
        return dishId;
    }

    public void setDishId(int dishId) {
        this.dishId = dishId;
    }

    public int getDishCategory() {
        return dishCategory;
    }

    public void setDishCategory(int dishCategory) {
        this.dishCategory = dishCategory;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getSoldQuantity() {
        return soldQuantity;
    }

    public void setSoldQuantity(int soldQuantity) {
        this.soldQuantity = soldQuantity;
    }

    public Dishes(String dishImg, String dishName, int dishId, int dishCategory, int price, int quantity, int soldQuantity) {
         this.dishImg = dishImg;
         this.dishName = dishName;
         this.dishId = dishId;
         this.dishCategory = dishCategory;
         this.price = price;
         this.quantity = quantity;
         this.soldQuantity = soldQuantity;
     }
 }
