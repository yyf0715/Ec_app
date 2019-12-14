package com.example.ec_app.fragments.baseFragments;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ec_app.R;
import com.example.ec_app.ViewModels.ProductViewModel;
import com.example.ec_app.adapters.ProductHomePageAdapter;
import com.example.ec_app.buybus.Buybus;
import com.example.ec_app.products.Product;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class SearchProductByNameFragment extends Fragment {
    private ProductViewModel productViewModel;
    private RecyclerView recyclerView_searchProduct_byName;
    private LiveData<List<Product>> searchProduct_byName;

    public SearchProductByNameFragment() {
        // Required empty public constructor
    }
    //滑动删除,需要借助工具ItemTouchHelper 辅助工具



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_search_product_by_name, container, false);
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
        productViewModel = ViewModelProviders.of(requireActivity()).get(ProductViewModel.class);//绑定ViewModel
        final ProductHomePageAdapter productHomePageAdapter = new ProductHomePageAdapter(productViewModel);
        recyclerView_searchProduct_byName = getActivity().findViewById(R.id.recyclerView_searchProduct_by_name);//绑定recyclerView
        recyclerView_searchProduct_byName.setLayoutManager(new LinearLayoutManager(requireActivity()));//为线性布局
        recyclerView_searchProduct_byName.setAdapter(productHomePageAdapter);//设置适配器

        searchProduct_byName = productViewModel.getProductByName(getArguments().getString("query"));

        searchProduct_byName.observe(getViewLifecycleOwner(), new Observer<List<Product>>() {
            @Override
            public void onChanged(List<Product> products) {
                productHomePageAdapter.submitList(products);
            }
        });

    }
}
