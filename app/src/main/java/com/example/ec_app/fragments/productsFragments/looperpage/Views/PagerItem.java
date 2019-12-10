package com.example.ec_app.fragments.productsFragments.looperpage.Views;

public class PagerItem {
    private String title;

    private Integer picResId;

    public int getProduct_id() {
        return product_id;
    }

    public void setProduct_id(int product_id) {
        this.product_id = product_id;
    }

    private int product_id;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getPicResId() {
        return picResId;
    }

    public void setPicResId(Integer picResId) {
        this.picResId = picResId;
    }

    public PagerItem(String title,Integer picResId,int product_id) {
        this.title = title;
        this.picResId = picResId;
        this.product_id = product_id;
    }
}
