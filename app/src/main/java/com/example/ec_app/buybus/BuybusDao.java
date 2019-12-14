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
     void insertOrder(Buybus...buybuses);
    @Delete
     void deleteOrder(Buybus...buybuses);
    @Query("SELECT * FROM Buybus")
    LiveData<List<Buybus>> getAllOrder();
    @Query("SELECT * FROM Buybus WHERE user_name = :pattern")
    LiveData<List<Buybus>> userBuybusOrder(String pattern);
}
