package com.utsavbucky.onebancfoodapp.models;

public class Category {

    String categoryImage;
    int categoryId;
    String categoryName;

    public Category(String categoryImage, int categoryId, String categoryName) {
        this.categoryImage = categoryImage;
        this.categoryId = categoryId;
        this.categoryName = categoryName;
    }

    public String getCategoryImage() {
        return categoryImage;
    }

    public void setCategoryImage(String categoryImage) {
        this.categoryImage = categoryImage;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }
}
