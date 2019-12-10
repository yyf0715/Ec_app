package com.example.ec_app.data_base;

import android.content.Context;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.example.ec_app.users.User;
import com.example.ec_app.users.UserDao;

import java.util.List;

public class UserRepository {
    //创建一个库，用来存储和读取数据
    private LiveData<List<User>> allUserLive;
    private UserDao userDao;

    public LiveData<List<User>> getUserAllLive() {
        return allUserLive;
    }
    public List<User> findUserWithPattern(String pattern){
        return userDao.findUserWithPattern(pattern);
    }
    public UserRepository(Context context) {
        Ec_Database ec_database = Ec_Database.ec_database(context.getApplicationContext());
        userDao = ec_database.getUserDao();
        allUserLive = userDao.getAllUser();//获取数据
    }

    public void insertUser(User...users){//插入
        new InserAsyncTask(userDao).execute(users);//execute()执行
    }
    public void deleteUser(User...users){//删除
        new DeleteAsyncTask(userDao).execute(users);//execute()执行
    }
    public void updateUser(User...users){//更改
        new UpdateAsyncTask(userDao).execute(users);//execute()执行
    }

    //在使用数据库时操作使用
    //    必须为static，否者会报警告
    static class InserAsyncTask extends AsyncTask<User, Void, Void> {//Void不声明,不使用
        private UserDao userDao;

        public InserAsyncTask(UserDao userDao) {
            this.userDao = userDao;
        }

        @Override
        protected Void doInBackground(User... users) {//插入操作放到后台去执行
            userDao.inserUser(users);
            return null;
        }
    }

    static class UpdateAsyncTask extends AsyncTask<User, Void, Void> {
        private UserDao userDao;

        UpdateAsyncTask(UserDao userDao) {
            this.userDao = userDao;
        }

        @Override
        protected Void doInBackground(User... users) {
            userDao.updateUser(users);
            return null;
        }
    }

    static class DeleteAsyncTask extends AsyncTask<User, Void, Void> {
        private UserDao userDao;

        DeleteAsyncTask(UserDao userDao) {
            this.userDao = userDao;
        }

        @Override
        protected Void doInBackground(User... users) {
            userDao.deleteUser(users);
            return null;
        }
    }
}
