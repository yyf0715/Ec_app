package com.example.ec_app.ViewModels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.ec_app.buybus.Buybus;
import com.example.ec_app.data_base.BuybusRepository;
import com.example.ec_app.data_base.ProductsRepository;

import java.util.List;

public class BuybusViewModel extends AndroidViewModel {
    private BuybusRepository buybusRepository;

    public BuybusViewModel(@NonNull Application application) {
        super(application);
        buybusRepository = new BuybusRepository(application);
    }
    public LiveData<List<Buybus>> getAllOrder(){
        return buybusRepository.getGetAllOrder();
    }
    public LiveData<List<Buybus>> getUserOrder(String pattern){
        return buybusRepository.getUserBuybus(pattern);
    }
    public void insertOrder(Buybus...buybuses){
        buybusRepository.insertOrder(buybuses);
    }
    public void deleteOrder(Buybus...buybuses){
        buybusRepository.deleteOrder(buybuses);
    }

}
