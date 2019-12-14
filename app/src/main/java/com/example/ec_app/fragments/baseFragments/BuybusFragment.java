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
import android.widget.Button;

import com.example.ec_app.R;
import com.example.ec_app.ViewModels.BuybusViewModel;
import com.example.ec_app.ViewModels.ProductViewModel;
import com.example.ec_app.ViewModels.UserViewModel;
import com.example.ec_app.adapters.BuybusAdapter;
import com.example.ec_app.buybus.Buybus;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class BuybusFragment extends Fragment {
    private BuybusViewModel buybusViewModel;
    private UserViewModel userViewModel;
    private ProductViewModel productViewModel;
    private RecyclerView recyclerView_buybus;
    private Button button_buy_product, button_buy_allProducts;
    private LiveData<List<Buybus>> orderByUser;



    public BuybusFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_buybus, container, false);
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

    }

    private void initView() {

        buybusViewModel = ViewModelProviders.of(requireActivity()).get(BuybusViewModel.class);
        userViewModel = ViewModelProviders.of(requireActivity()).get(UserViewModel.class);
        productViewModel = ViewModelProviders.of(requireActivity()).get(ProductViewModel.class);

        final BuybusAdapter buybusAdapter = new BuybusAdapter(buybusViewModel,userViewModel,productViewModel);


        recyclerView_buybus = getActivity().findViewById(R.id.recyclerView_buybus_order);
        recyclerView_buybus.setLayoutManager(new LinearLayoutManager(requireActivity()));
        recyclerView_buybus.setAdapter(buybusAdapter);

        orderByUser = buybusViewModel.getUserOrder(getArguments().getString("admin"));
        orderByUser.observe(getViewLifecycleOwner(), new Observer<List<Buybus>>() {
            @Override
            public void onChanged(List<Buybus> buybuses) {
                buybusAdapter.submitList(buybuses);
            }
        });
        //滑动删除,需要借助工具ItemTouchHelper 辅助工具
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.START |ItemTouchHelper.END) {
            //允许从左往右滑动，也支持从右往左滑动
            @Override//拖动
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override//滑动
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                final Buybus buybus = orderByUser.getValue().get(viewHolder.getAdapterPosition());
                buybusViewModel.deleteOrder(buybus);
                //撤销
                Snackbar.make(getView(),"移除一个订单",Snackbar.LENGTH_SHORT)
                        .setAction("撤销", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                buybusViewModel.insertOrder(buybus);
                            }
                        })//设置动作
                        .show();
            }
        }).attachToRecyclerView(recyclerView_buybus);//生效
    }
}
