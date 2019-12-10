package com.example.ec_app.buybus;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Buybus {
    @PrimaryKey(autoGenerate = true)
    private int order_id;
    @ColumnInfo(name = "product_id")
    private int product_id;
    @ColumnInfo(name = "user_name")
    private String user_name;
    @ColumnInfo(name = "product_num")
    private int product_num;

    public Buybus(int product_id, String user_name, int product_num) {
        this.product_id = product_id;
        this.user_name = user_name;
        this.product_num = product_num;
    }

    public int getOrder_id() {
        return order_id;
    }

    public void setOrder_id(int order_id) {
        this.order_id = order_id;
    }

    public int getProduct_id() {
        return product_id;
    }

    public void setProduct_id(int product_id) {
        this.product_id = product_id;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public int getProduct_num() {
        return product_num;
    }

    public void setProduct_num(int product_num) {
        this.product_num = product_num;
    }
}
