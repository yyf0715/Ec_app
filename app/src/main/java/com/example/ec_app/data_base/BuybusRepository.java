package com.example.ec_app.data_base;

import android.content.Context;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.example.ec_app.buybus.Buybus;
import com.example.ec_app.buybus.BuybusDao;

import java.util.List;

public class BuybusRepository {
    private LiveData<List<Buybus>> getAllOrder;
    private BuybusDao buybusDao;

    public LiveData<List<Buybus>> getGetAllOrder() {
        return getAllOrder;
    }

    public LiveData<List<Buybus>> userBuybusOrder(String pattern) {
        return buybusDao.userBuybusOrder(pattern);
    }

    public BuybusRepository(Context context) {
        Ec_Database ec_database = Ec_Database.ec_database(context.getApplicationContext());
        this.buybusDao = ec_database.getBuybusDao();
        this.getAllOrder = buybusDao.getAllOrder();
    }

    public void insertOrder(Buybus... buybuses) {
        new InsertAsyncTask(buybusDao).execute(buybuses);
    }

    public void deleteOrder(Buybus... buybuses) {
        new DeleteAsyncTask(buybusDao).execute(buybuses);
    }


    static class InsertAsyncTask extends AsyncTask<Buybus, Void, Void> {
        private BuybusDao buybusDao;

        InsertAsyncTask(BuybusDao buybusDao) {
            this.buybusDao = buybusDao;
        }

        @Override
        protected Void doInBackground(Buybus... buybuses) {
            buybusDao.insertOrder(buybuses);
            return null;
        }
    }

    static class DeleteAsyncTask extends AsyncTask<Buybus, Void, Void> {
        private BuybusDao buybusDao;

        DeleteAsyncTask(BuybusDao buybusDao) {
            this.buybusDao = buybusDao;
        }

        @Override
        protected Void doInBackground(Buybus... buybuses) {
            buybusDao.deleteOrder(buybuses);
            return null;
        }
    }
}
