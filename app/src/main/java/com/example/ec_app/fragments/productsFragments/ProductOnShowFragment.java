package com.example.ec_app.fragments.productsFragments;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ec_app.R;
import com.example.ec_app.ViewModels.BuybusViewModel;
import com.example.ec_app.ViewModels.ProductViewModel;
import com.example.ec_app.buybus.Buybus;
import com.example.ec_app.fragments.SignInFragment;
import com.example.ec_app.fragments.baseFragments.BuybusFragment;
import com.example.ec_app.products.Product;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProductOnShowFragment extends Fragment {
    private ImageView iv_product_img;
    private TextView tv_product_name,tv_product_response,tv_product_price;
    private Button button_buy_submit;
    private BuybusViewModel buybusViewModel;
    private ProductViewModel productViewModel;

    public ProductOnShowFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_product_on_show, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        init();
    }

    private void init() {
        initView();
        initData();
        initEvent();
    }

    private void initData() {
        iv_product_img.setImageResource(getArguments().getInt("product_res"));
        iv_product_img.setScaleType(ImageView.ScaleType.FIT_XY);
        tv_product_name.setText("商品名称："+getArguments().getString("product_name"));
        tv_product_response.setText("剩余库存："+String.valueOf(getArguments().getInt("product_response")));
        tv_product_price.setText("价格："+String.valueOf(getArguments().getFloat("product_price")));
    }

    private void initEvent() {
        button_buy_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final EditText input = new EditText(getContext());
                input.setInputType(InputType.TYPE_CLASS_NUMBER );
//                number 和 numberDecimal  属性要同时设置    这个是可以输入float类型的
                AlertDialog.Builder dialog = new AlertDialog.Builder(getContext());
                dialog.setTitle("加入购物车")
                        .setMessage("请输入数量")
                        .setView(input)
                        .setCancelable(false)
                        .setPositiveButton("确认", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                String TAG = "ProductOnShowFragment";
                                Log.d(TAG, "onClick: "+productViewModel.getProductById(getArguments().getInt("product_id")).getProduct_name());
                                final Product product = productViewModel.getProductById(getArguments().getInt("product_id"));

                                if (Integer.valueOf(input.getText().toString())<product.getProduct_repertory()){
                                    Toast.makeText(getContext(),"添加购物车成功，请到购物车页面查看",Toast.LENGTH_SHORT).show();
                                    new Thread(){
                                        @Override
                                        public void run() {
                                            super.run();
                                            Buybus buybus = new Buybus(product.getProtect_id(), SignInFragment.User_admin,Integer.valueOf(input.getText().toString()));
                                            buybusViewModel.insertOrder(buybus);
                                        }
                                    }.start();
                                }else{
                                    Toast.makeText(getContext(),"超出库存量",Toast.LENGTH_SHORT).show();
                                }
                            }
                        })
                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        })
                        .show();









            }
        });
    }

    private void initView() {
        productViewModel = ViewModelProviders.of(requireActivity()).get(ProductViewModel.class);
        buybusViewModel = ViewModelProviders.of(requireActivity()).get(BuybusViewModel.class);
        iv_product_img = getActivity().findViewById(R.id.imageView_product_show_img);
        tv_product_name = getActivity().findViewById(R.id.textView_product_show_name);
        tv_product_response = getActivity().findViewById(R.id.textView_product_show_response);
        tv_product_price = getActivity().findViewById(R.id.textView_product_show_price);
        button_buy_submit = getActivity().findViewById(R.id.button_show_submit);
    }
}
