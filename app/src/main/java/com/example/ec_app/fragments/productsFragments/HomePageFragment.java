package com.example.ec_app.fragments.productsFragments;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.Toast;

import com.example.ec_app.R;
import com.example.ec_app.ViewModels.ProductViewModel;
import com.example.ec_app.adapters.ProductHomePageAdapter;
import com.example.ec_app.fragments.productsFragments.looperpage.Views.PagerItem;
import com.example.ec_app.fragments.productsFragments.looperpage.Views.SobLooperPager;
import com.example.ec_app.products.Product;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomePageFragment extends Fragment {
    private SobLooperPager mLooperPager;
    private List<PagerItem> mData = new ArrayList<>();//存储需要加载的图片
    private SobLooperPager.InnerAdapter mInnerAdapter = new SobLooperPager.InnerAdapter() {
        @Override
        protected int getDataSize() {
            return mData.size();
        }

        @Override
        protected View getSubView(ViewGroup container, int position) {//将图片加载到view上
            ImageView iv = new ImageView(container.getContext());
            iv.setImageResource(mData.get(position).getPicResId());
            iv.setScaleType(ImageView.ScaleType.FIT_XY);
            return iv;
        }
    };
    private ProductViewModel productViewModel;
    private RecyclerView recyclerView_home_page;
    private LiveData<List<Product>> allProducts;

    public HomePageFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home_page, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        init();
    }

    private void init() {

        initData();
        initView();
        initEvent();

    }

    private void initEvent() {
        if (mLooperPager != null) {
            mLooperPager.setOnItemClickListener(new SobLooperPager.OnItemClickListener() {
                @Override
                public void onItemClick(int position) {
//                    Toast.makeText(getContext(), "点击了第" + (position + 1) + "个item", Toast.LENGTH_SHORT).show();
                    //todo:根据交互业务去实现具体逻辑
                    NavController controller = Navigation.findNavController(getView());
                    Bundle bundle = new Bundle();//传值
                    Product product = productViewModel.getProductById(mData.get(position).getProduct_id());
                    bundle.putInt("product_res",product.getProduct_res());
                    bundle.putString("product_name",product.getProduct_name());
                    bundle.putInt("product_response",product.getProduct_repertory());
                    bundle.putFloat("product_price",product.getProduct_price());
                    controller.navigate(R.id.productOnShowFragment,bundle);
                }
            });
        }
    }

    private void initData() {//添加轮播图的图片和标题
        mData.add(new PagerItem("小吃一", R.mipmap.xiaochi1,2));
        mData.add(new PagerItem("小吃二", R.mipmap.xiaochi2,3));
        mData.add(new PagerItem("小吃三", R.mipmap.xiaochi3,4));


    }

    private void initView() {
        setHasOptionsMenu(true);//加载菜单项
        mLooperPager = getActivity().findViewById(R.id.sob_looper_pager);//找到轮播图的容器
        mLooperPager.setData(mInnerAdapter, new SobLooperPager.BindTitleListener() {
            @Override
            public String getTitle(int position) {
                return mData.get(position).getTitle();
            }
        });
        //ViewModel
        productViewModel = ViewModelProviders.of(requireActivity()).get(ProductViewModel.class);//绑定ViewModel
//        productViewModel.insertProduct(new Product("小米6",1999,20,R.mipmap.xiaomi6));
//        addProducts();
        //适配器
        final ProductHomePageAdapter productHomePageAdapter = new ProductHomePageAdapter(productViewModel);
        //recyclerView
        recyclerView_home_page = getActivity().findViewById(R.id.recyclerView_home_page);//绑定recyclerView
        recyclerView_home_page.setLayoutManager(new LinearLayoutManager(requireActivity()));//为线性布局
        recyclerView_home_page.setAdapter(productHomePageAdapter);//设置适配器

        //获取Product数据库资料
        allProducts = productViewModel.getAllProduct();
        //监控数据变化
        allProducts.observe(getViewLifecycleOwner(), new Observer<List<Product>>() {//监控数据变化
            @Override
            public void onChanged(List<Product> products) {//数据发生改变，通知适配器
                //int temp = userAdapter.getItemCount();//获取适配器里的item数量
                productHomePageAdapter.submitList(products);
            }
        });

    }
    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_seach_product,menu);
        productViewModel = ViewModelProviders.of(requireActivity()).get(ProductViewModel.class);//绑定ViewModel

        SearchView searchView = (SearchView) menu.findItem(R.id.app_bar_search_product).getActionView();
        searchView.setMaxWidth(1000);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override// 当点击搜索按钮时触发该方法
            public boolean onQueryTextSubmit(String query) {
//            Toast.makeText(getContext(),"点击搜索框"+query,Toast.LENGTH_SHORT).show();
            Bundle bundle = new Bundle();
            bundle.putString("query",query);
            String TAG = "HomePageFragment";
//            LiveData<List<Product>> list = productViewModel.getProductByName(query);
                Log.d(TAG, "onQueryTextSubmit: ");
            NavController controller = Navigation.findNavController(getView());
            controller.navigate(R.id.searchProductByNameFragment,bundle);

                return false;
            }

            @Override// 当搜索内容改变时触发该方法
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
    }

    public void addProducts() {
            Product product_xiaomi = new Product("小米6", 2999, 20, R.mipmap.xiaomi6,1);
            Product product_xiaochi1 = new Product("小吃一", 10, 200, R.mipmap.xiaochi1,0);
            Product product_xiaochi2 = new Product("小吃二", 9, 200, R.mipmap.xiaochi2,0);
            Product product_xiaochi3 = new Product("小吃三", 9.8f, 200, R.mipmap.xiaochi3,0);
            productViewModel.insertProduct(product_xiaomi, product_xiaochi1, product_xiaochi2, product_xiaochi3);
    }
}
