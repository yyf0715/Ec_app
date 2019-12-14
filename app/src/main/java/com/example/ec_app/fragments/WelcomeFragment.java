package com.example.ec_app.fragments;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ec_app.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class WelcomeFragment extends Fragment {
    public static final String SHP = "login";
    public static final String ISLOGIN = "islogin";
    public static final String ADMIN = "login_admin";
    public static final String PASSWORD = "login_password";

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
        new Thread() {
            @Override
            public void run() {
                super.run();
                try {
                    Thread.sleep(3000);
                    //判断是否登陆过
                    SharedPreferences shp = requireActivity().getSharedPreferences(SHP, Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = shp.edit();
                    int islogin = shp.getInt(ISLOGIN,0);
                    if (islogin == 0){
                        controller.navigate(R.id.signInFragment);
                    }else{
                        Bundle bundle = new Bundle();
                        String admin = shp.getString(ADMIN,"");
//                        String password = shp.getString(PASSWORD,"");
                        bundle.putString("user_admin",admin);
                        SignInFragment.User_admin = admin;
                        controller.navigate(R.id.baseFragment,bundle);
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }
}
