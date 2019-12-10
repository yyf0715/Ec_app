package com.example.ec_app.products;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Product {
    @PrimaryKey(autoGenerate = true)//主键，自动生成
    private int protect_id;

    @ColumnInfo(name = "product_name")
    private String product_name;//商品名

    @ColumnInfo(name = "product_price")
    private float product_price;//价格

    @ColumnInfo(name = "product_repertory")
    private int product_repertory;//库存

    @ColumnInfo(name = "product_res")
    private int product_res;//图片资源

    public Product(String product_name, float product_price, int product_repertory, int product_res) {
        this.product_name = product_name;
        this.product_price = product_price;
        this.product_repertory = product_repertory;
        this.product_res = product_res;
    }

    public int getProtect_id() {
        return protect_id;
    }

    public void setProtect_id(int protect_id) {
        this.protect_id = protect_id;
    }

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public float getProduct_price() {
        return product_price;
    }

    public void setProduct_price(float product_price) {
        this.product_price = product_price;
    }

    public int getProduct_repertory() {
        return product_repertory;
    }

    public void setProduct_repertory(int product_repertory) {
        this.product_repertory = product_repertory;
    }

    public int getProduct_res() {
        return product_res;
    }

    public void setProduct_res(int product_res) {
        this.product_res = product_res;
    }
}
