package com.example.ec_app.users;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.ec_app.R;

@Entity
public class User {
    @NonNull
    @PrimaryKey
    private String user_admin;
    @ColumnInfo
    private String user_password;
    @ColumnInfo
    private float user_money;

    public User(String user_admin, String user_password, float user_money) {
        this.user_admin = user_admin;
        this.user_password = user_password;
        this.user_money = user_money;
    }

    public String getUser_admin() {
        return user_admin;
    }

    public void setUser_admin(String user_admin) {
        this.user_admin = user_admin;
    }

    public String getUser_password() {
        return user_password;
    }

    public void setUser_password(String user_password) {
        this.user_password = user_password;
    }

    public float getUser_money() {
        return user_money;
    }

    public void setUser_money(float user_money) {
        this.user_money = user_money;
    }
}
