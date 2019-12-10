package com.example.ec_app.data_base;

import android.content.Context;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;
import androidx.room.Update;

import com.example.ec_app.products.Product;
import com.example.ec_app.products.ProductDao;

import java.util.List;

public class ProductsRepository {
    //创建一个库，用来存储和读取数据
    private ProductDao productDao;
    private LiveData<List<Product>> allProducts;

    public LiveData<List<Product>> getAllProducts() {
        return allProducts;
    }

    public Product getProductById(int pattern) {
        return productDao.getProductById(pattern);
    }


    public ProductsRepository(Context context) {
        Ec_Database ec_database = Ec_Database.ec_database(context.getApplicationContext());
        productDao = ec_database.getProductDao();
        allProducts = productDao.getAllProducts();
    }

    public void insertProduct(Product... products) {
        new InserAsyncTask(productDao).execute(products);
    }

    public void deleteProduct(Product... products) {
        new DeleteAsyncTask(productDao).execute(products);
    }

    public void updateProduct(Product... products) {
        new UpdateAsyncTask(productDao).execute(products);
    }

    static class InserAsyncTask extends AsyncTask<Product, Void, Void> {
        private ProductDao productDao;

        InserAsyncTask(ProductDao productDao) {
            this.productDao = productDao;
        }

        @Override
        protected Void doInBackground(Product... products) {
            productDao.inserProduct(products);
            return null;
        }
    }

    static class DeleteAsyncTask extends AsyncTask<Product, Void, Void> {
        private ProductDao productDao;

        DeleteAsyncTask(ProductDao productDao) {
            this.productDao = productDao;
        }

        @Override
        protected Void doInBackground(Product... products) {
            productDao.deleteProduct(products);
            return null;
        }
    }

    static class UpdateAsyncTask extends AsyncTask<Product, Void, Void> {
        private ProductDao productDao;

        UpdateAsyncTask(ProductDao productDao) {
            this.productDao = productDao;
        }

        @Override
        protected Void doInBackground(Product... products) {
            productDao.updateProduct(products);
            return null;
        }
    }
}
