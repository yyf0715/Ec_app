package com.example.ec_app.ViewModels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.ec_app.data_base.ProductsRepository;
import com.example.ec_app.products.Product;

import java.util.List;

public class ProductViewModel extends AndroidViewModel {
    private ProductsRepository productsRepository;
    public ProductViewModel(@NonNull Application application) {
        super(application);
        productsRepository = new ProductsRepository(application);
    }
    public LiveData<List<Product>> getAllProduct(){
        return productsRepository.getAllProducts();
    };
    public Product getProductById(int pattern){
        return productsRepository.getProductById(pattern);
    }
    public LiveData<List<Product>> getProductByName(String pattern){
        return productsRepository.getProductByName(pattern);
    }
    public void insertProduct(Product...products){
        productsRepository.insertProduct(products);
    }
    public void deleteProduct(Product...products){
        productsRepository.deleteProduct(products);
    }
    public void updateProduct(Product...products){
        productsRepository.updateProduct(products);
    }
}
