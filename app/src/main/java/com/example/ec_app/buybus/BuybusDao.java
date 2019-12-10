package com.example.ec_app.buybus;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface BuybusDao {
    @Insert
    public void insertOrder(Buybus...buybuses);
    @Delete
    public void deleteOrder(Buybus...buybuses);
    @Query("SELECT * FROM Buybus")
    LiveData<List<Buybus>> getAllOrder();
    @Query("SELECT * FROM Buybus WHERE user_name LIKE :pattern")
    LiveData<List<Buybus>> userBuybus(String pattern);
}
