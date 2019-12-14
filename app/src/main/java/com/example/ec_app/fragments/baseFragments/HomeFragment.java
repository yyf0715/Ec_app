package com.example.ec_app.fragments.baseFragments;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;

import com.example.ec_app.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {
    private RadioGroup radioGroup_products;

    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        init();
    }

    private void init() {

        initView();
        initEvent();
    }

    private void initEvent() {
        radioGroup_products.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                NavController controller = Navigation.findNavController(getActivity(),R.id.fragment_products_page);
                switch (checkedId){
                    case R.id.radioButton_homepage:
                        controller.navigate(R.id.homePageFragment);
                        break;
                    case R.id.radioButton_snackspage:
                        controller.navigate(R.id.snacksPageFragment);
                        break;
                    case R.id.radioButton_electronicpage:
                        controller.navigate(R.id.electronicPageFragment);
                        break;
                }
            }
        });
    }

    private void initView() {
        radioGroup_products = getActivity().findViewById(R.id.radioGroup_products);
    }


}
