package com.example.ec_app.fragments.productsFragments;


import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ec_app.R;
import com.example.ec_app.ViewModels.ProductViewModel;
import com.example.ec_app.adapters.ProductHomePageAdapter;
import com.example.ec_app.products.Product;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class ElectronicPageFragment extends Fragment {
    private ProductViewModel productViewModel;
    private RecyclerView recyclerView_electronic;
    private LiveData<List<Product>> getAllElectronic;

    public ElectronicPageFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_electronic_page, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        init();
    }

    private void init() {
        initView();
    }

    private void initView() {
        productViewModel = ViewModelProviders.of(requireActivity()).get(ProductViewModel.class);

        recyclerView_electronic = getActivity().findViewById(R.id.recyclerView_electronic);
        recyclerView_electronic.setLayoutManager(new LinearLayoutManager(requireActivity()));

        final ProductHomePageAdapter productHomePageAdapter = new ProductHomePageAdapter(productViewModel);
        recyclerView_electronic.setAdapter(productHomePageAdapter);

        getAllElectronic = productViewModel.getElectronicProducts();
        getAllElectronic.observe(getViewLifecycleOwner(), new Observer<List<Product>>() {
            @Override
            public void onChanged(List<Product> products) {
                productHomePageAdapter.submitList(products);
            }
        });
    }
}
