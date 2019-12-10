package com.example.ec_app.data_base;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.ec_app.buybus.Buybus;
import com.example.ec_app.buybus.BuybusDao;
import com.example.ec_app.products.Product;
import com.example.ec_app.products.ProductDao;
import com.example.ec_app.users.User;
import com.example.ec_app.users.UserDao;

@Database(entities = {User.class, Product.class, Buybus.class}, version = 1, exportSchema = false)
public abstract class Ec_Database extends RoomDatabase {
    //抽象方法，不需要我们实现

    //singleton 只允许生一个实例
    //完成singleton设计模式

    private static Ec_Database INSTANCE;

    static synchronized Ec_Database ec_database(Context context) {
        if (INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(context, Ec_Database.class, "Database")
                    .allowMainThreadQueries()//强制在主线程使用,一般不建议使用
                    .build();
            //获取DataBase一个对象,需要用到databaseBuilder（）;静态函数获取  呼叫build()；来创建
//        //该数据库不能在主线程使用
        }
        return INSTANCE;
    }

    public abstract UserDao getUserDao();
    public abstract ProductDao getProductDao();
    public abstract BuybusDao getBuybusDao();
}
