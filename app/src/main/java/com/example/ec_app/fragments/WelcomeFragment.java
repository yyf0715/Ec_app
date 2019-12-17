package com.example.ec_app.fragments;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ec_app.R;
import com.example.ec_app.ViewModels.ProductViewModel;
import com.example.ec_app.products.Product;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class WelcomeFragment extends Fragment {
    public static final String SHP = "login";
    public static final String ISLOGIN = "islogin";
    public static final String ADMIN = "login_admin";
    public static final String PASSWORD = "login_password";
    private ProductViewModel productViewModel;

    public WelcomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_welcome, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        final NavController controller = Navigation.findNavController(getView());
        initView();
        new Thread() {
            @Override
            public void run() {
                super.run();
                try {
                    Thread.sleep(3000);
                    //判断是否登陆过
                    SharedPreferences shp = requireActivity().getSharedPreferences(SHP, Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = shp.edit();
                    int islogin = shp.getInt(ISLOGIN, 0);
                    if (islogin == 0) {
                        controller.navigate(R.id.signInFragment);
                    } else {
                        Bundle bundle = new Bundle();
                        String admin = shp.getString(ADMIN, "");
//                        String password = shp.getString(PASSWORD,"");
                        bundle.putString("user_admin", admin);
                        SignInFragment.User_admin = admin;
                        controller.navigate(R.id.baseFragment, bundle);
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

    private void initView() {
        productViewModel = ViewModelProviders.of(requireActivity()).get(ProductViewModel.class);
        LiveData<List<Product>> getAllProduct = productViewModel.getAllProduct();

        getAllProduct.observe(getViewLifecycleOwner(), new Observer<List<Product>>() {
            @Override
            public void onChanged(List<Product> products) {
                String TAG = "WelcomeFragment";
                Log.d(TAG, "initView: "+products.size());
                if (products.size() < 7 ){
                    addProducts();
                }
            }
        });



    }

    public void addProducts() {
        Product product_xiaomi = new Product("小米6", 2999, 20, R.mipmap.xiaomi6, 1);
        Product product_xiaochi1 = new Product("小吃一", 10, 200, R.mipmap.xiaochi1, 0);
        Product product_xiaochi2 = new Product("小吃二", 9, 200, R.mipmap.xiaochi2, 0);
        Product product_xiaochi3 = new Product("小吃三", 9.8f, 200, R.mipmap.xiaochi3, 0);
        productViewModel.insertProduct(product_xiaomi, product_xiaochi1, product_xiaochi2, product_xiaochi3);
    }
}
