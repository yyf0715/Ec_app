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
    void inserProduct(Product... products);
    @Delete
    void deleteProduct(Product... products);
    @Update
    void updateProduct(Product... products);
    @Query("SELECT * FROM Product")
    LiveData<List<Product>> getAllProducts();
    @Query("SELECT * FROM Product WHERE protect_id = :pattern")
    Product getProductById(int pattern);
    @Query("SELECT * FROM Product WHERE product_name  LIKE  '%' || :pattern || '%'")
    LiveData<List<Product>> getProductByName(String pattern);

//    @Query("SELECT * FROM PRODUCT ORDER BY ID DESC")//查询，返回所有的内容，降序，最新记录放在前边
//    public LiveData<Product> getAllProductsLive();
}
