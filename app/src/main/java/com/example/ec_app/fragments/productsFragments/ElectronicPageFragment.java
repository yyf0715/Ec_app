package com.example.ec_app.fragments.productsFragments;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ec_app.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class ElectronicPageFragment extends Fragment {


    public ElectronicPageFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_electronic_page, container, false);
    }

}
