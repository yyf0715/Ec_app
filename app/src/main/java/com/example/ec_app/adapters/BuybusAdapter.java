package com.example.ec_app.adapters;

import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ec_app.R;
import com.example.ec_app.ViewModels.BuybusViewModel;
import com.example.ec_app.ViewModels.ProductViewModel;
import com.example.ec_app.ViewModels.UserViewModel;
import com.example.ec_app.buybus.Buybus;
import com.example.ec_app.products.Product;
import com.example.ec_app.users.User;

public class BuybusAdapter extends ListAdapter<Buybus, BuybusAdapter.BuybusViewHolder> {
    private BuybusViewModel buybusViewModel;
    private UserViewModel userViewModel;
    private ProductViewModel productViewModel;

    public BuybusAdapter(BuybusViewModel buybusViewModel,UserViewModel userViewModel,ProductViewModel productViewModel) {
        super(new DiffUtil.ItemCallback<Buybus>() {
            @Override
            public boolean areItemsTheSame(@NonNull Buybus oldItem, @NonNull Buybus newItem) {
                return oldItem.getOrder_id() == newItem.getOrder_id();
            }

            @Override
            public boolean areContentsTheSame(@NonNull Buybus oldItem, @NonNull Buybus newItem) {
                return (oldItem.getProduct_id() == newItem.getProduct_id()
                        && oldItem.getUser_name().equals(newItem.getUser_name()));
            }
        });
        this.buybusViewModel = buybusViewModel;
        this.userViewModel = userViewModel;
        this.productViewModel = productViewModel;

    }

    @NonNull
    @Override
    public BuybusViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View itemview;
        itemview = layoutInflater.inflate(R.layout.recyclerview_item_buybusorder, parent, false);
        final BuybusViewHolder holder = new BuybusViewHolder(itemview);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull BuybusViewHolder holder, int position) {
        final Buybus buybus = getItem(position);
        final String user_admin = buybus.getUser_name();
        final int product_id = buybus.getProduct_id();
        final int product_num = buybus.getProduct_num();

        /*holder.btn_buybusToPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String TAG = "BuybusAdapter";
                Log.d(TAG, "onBindViewHolder: "+buybusViewModel+ userViewModel + productViewModel);

            }
        });*/

        final User user = userViewModel.findUserWithPattern(user_admin).get(0);
        final Product product = productViewModel.getProductById(product_id);

        holder.iv_buybusProductImg.setImageResource(product.getProduct_res());
        holder.iv_buybusProductImg.setScaleType(ImageView.ScaleType.FIT_XY);
        holder.tv_buybusProductName.setText("商品名称："+product.getProduct_name());
        holder.tv_buybusProductPrice.setText("价格"+String.valueOf(product.getProduct_price()));
        holder.tv_buybusProductNum.setText("商品数量"+String.valueOf(product_num));
        holder.btn_buybusToPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {

                AlertDialog.Builder dialog = new AlertDialog.Builder(v.getContext());
                //获取AlertDialog对象
                dialog.setTitle("支付");//设置标题
                dialog.setMessage("确认购买？");//设置信息具体内容
                dialog.setCancelable(false);//设置是否可取消
                dialog.setPositiveButton("确认", new DialogInterface.OnClickListener() {
                    @Override//设置ok的事件
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //在此处写入ok的逻辑
                        if (user.getUser_money() < product.getProduct_price() && product.getProduct_repertory() > product_num) {
                            Toast.makeText(v.getContext(), "余额不足", Toast.LENGTH_SHORT).show();
                            new Thread() {
                                @Override
                                public void run() {
                                    super.run();
                                    try {
                                        Thread.sleep(3000);

                                    } catch (InterruptedException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }.start();
                        } else {
                            Toast.makeText(v.getContext(), "购买成功", Toast.LENGTH_SHORT).show();
                            user.setUser_money(user.getUser_money() - productViewModel.getProductById(product_id).getProduct_price());
                            userViewModel.updateUser(user);
                            product.setProduct_repertory(product.getProduct_repertory() - product_num);
                            productViewModel.updateProduct(product);
                            buybusViewModel.deleteOrder(buybus);
                        }
                    }
                });
                dialog.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override//设置取消事件
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //在此写入取消的事件
                    }
                });
                dialog.show();
            }
        });
    }

    class BuybusViewHolder extends RecyclerView.ViewHolder {
        private ImageView iv_buybusProductImg;
        private TextView tv_buybusProductName, tv_buybusProductPrice,tv_buybusProductNum;
        private Button btn_buybusToPay;

        public BuybusViewHolder(@NonNull View itemView) {
            super(itemView);
            iv_buybusProductImg = itemView.findViewById(R.id.imageView_buybus_img);
            tv_buybusProductName = itemView.findViewById(R.id.textView_buybus_product_name);
            tv_buybusProductPrice = itemView.findViewById(R.id.textView_buybus_product_price);
            tv_buybusProductNum = itemView.findViewById(R.id.textView_buybus_products_num);
            btn_buybusToPay = itemView.findViewById(R.id.button_okToPay);
        }
    }
}
