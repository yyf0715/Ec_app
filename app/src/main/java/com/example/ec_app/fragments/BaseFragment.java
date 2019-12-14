package com.example.ec_app.fragments;


import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.example.ec_app.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class BaseFragment extends Fragment {
    private RadioGroup radioGroup_base;


    public BaseFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_base, container, false);
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

        radioGroup_base.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                String TAG = "BaseFragment";
                Log.d(TAG, "onCheckedChanged: "+getArguments().getString("user_admin"));
                NavController controller = Navigation.findNavController(getActivity(), R.id.fragment_base);
                Bundle bundle = new Bundle();
                bundle.putString("admin",getArguments().getString("user_admin"));
                switch (checkedId) {
                    case R.id.radioButton_buy:
                        controller.navigate(R.id.homeFragment);
                        break;
                    case R.id.radioButton_buy_bus:
                        controller.navigate(R.id.buybusFragment,bundle);
                        break;
                    case R.id.radioButton_user_message:
                        controller.navigate(R.id.userMessageFragment,bundle);
                        break;
                }
            }
        });
    }

    private void initView() {
        radioGroup_base = getActivity().findViewById(R.id.radioGroup_base);

    }
    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_seach_product,menu);
        setHasOptionsMenu(true);
    }

}
