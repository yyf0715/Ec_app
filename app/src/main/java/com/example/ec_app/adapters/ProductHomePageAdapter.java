package com.example.ec_app.adapters;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ec_app.R;
import com.example.ec_app.ViewModels.ProductViewModel;
import com.example.ec_app.products.Product;

public class ProductHomePageAdapter extends ListAdapter<Product, ProductHomePageAdapter.ProductHomePageViewHolder> {
    private ProductViewModel productViewModel;

    public ProductHomePageAdapter(ProductViewModel productViewModel) {
        super(new DiffUtil.ItemCallback<Product>() {//处理两个列表差异化的回调
            @Override
            public boolean areItemsTheSame(@NonNull Product oldItem, @NonNull Product newItem) {//先比较列表中的元素
                return oldItem.getProtect_id() == newItem.getProtect_id();
            }

            @Override
            public boolean areContentsTheSame(@NonNull Product oldItem, @NonNull Product newItem) {//再比较内容
                return (oldItem.getProduct_name().equals(newItem.getProduct_name())
                        && oldItem.getProduct_repertory() == newItem.getProduct_repertory()
                        && oldItem.getProduct_price() == newItem.getProduct_price()
                        && oldItem.getProduct_res() == newItem.getProduct_res());
            }
        });
        this.productViewModel = productViewModel;
    }

    @NonNull
    @Override
    public ProductHomePageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //从Layout文件中加载View需要使用 LayoutInflater
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View itemView;
        itemView = layoutInflater.inflate(R.layout.recyclerview_item_home_page_product,parent,false);//加载的子控件
        //子控件传入ViewHolder
        final ProductHomePageViewHolder holder = new ProductHomePageViewHolder(itemView);//holder来自内部类，需要final修饰
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ProductHomePageViewHolder holder, int position) {
        final Product product = getItem(position);//得到数据
        holder.iv_product_img.setImageResource(product.getProduct_res());
        holder.iv_product_img.setScaleType(ImageView.ScaleType.FIT_XY);//填充全部
        holder.tv_product_name.setText(product.getProduct_name());
        holder.tv_product_response.setText("库存："+product.getProduct_repertory());
        holder.tv__product_price.setText("价格:"+product.getProduct_price());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putInt("product_id",product.getProtect_id());
                bundle.putString("product_name",product.getProduct_name());
                bundle.putInt("product_response",product.getProduct_repertory());
                bundle.putFloat("product_price",product.getProduct_price());
                bundle.putInt("product_res",product.getProduct_res());
                NavController controller = Navigation.findNavController(v);
                controller.navigate(R.id.productOnShowFragment,bundle);
            }
        });
    }


    class ProductHomePageViewHolder extends RecyclerView.ViewHolder {
        TextView tv_product_name, tv_product_response, tv__product_price;
        ImageView iv_product_img;

        public ProductHomePageViewHolder(@NonNull View itemView) {
            super(itemView);
            iv_product_img = itemView.findViewById(R.id.imageView_products);
            tv_product_name = itemView.findViewById(R.id.textView_product_name);
            tv_product_response = itemView.findViewById(R.id.textView_product_response);
            tv__product_price = itemView.findViewById(R.id.textView_product_price);
        }
    }
}
