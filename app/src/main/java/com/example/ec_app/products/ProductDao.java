package com.example.ec_app.products;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao//@Dao 是一个访问数据库的接口
public interface ProductDao {
    @Insert
    public void inserProduct(Product...products);
    @Delete
    public void deleteProduct(Product...products);
    @Update
    public void updateProduct(Product...products);
    @Query("SELECT * FROM Product")
    public LiveData<List<Product>> getAllProducts();
    @Query("SELECT * FROM Product WHERE protect_id = :pattern")
    public Product getProductById(int pattern);

//    @Query("SELECT * FROM PRODUCT ORDER BY ID DESC")//查询，返回所有的内容，降序，最新记录放在前边
//    public LiveData<Product> getAllProductsLive();
}
