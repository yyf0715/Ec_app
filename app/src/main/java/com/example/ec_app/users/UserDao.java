package com.example.ec_app.users;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface UserDao {
    @Insert
    public void inserUser(User...users);
    @Delete
    public void deleteUser(User...users);
    @Update
    public void updateUser(User...users);
    @Query("SELECT * FROM User")
    public LiveData<List<User>> getAllUser();
    @Query("SELECT * FROM User WHERE user_admin LIKE :pattern ")
    List<User> findUserWithPattern(String pattern);

}
