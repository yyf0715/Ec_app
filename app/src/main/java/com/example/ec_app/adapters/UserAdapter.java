package com.example.ec_app.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;

import com.example.ec_app.R;
import com.example.ec_app.ViewModels.UserViewModel;
import com.example.ec_app.users.User;

//ListAdapter能够自己监控数据的不同
//改为ListAdapter，还需要添加数据类型,父类中拥有Llst
public class UserAdapter extends ListAdapter<User, UserAdapter.UserViewHolder> {
    private UserViewModel userViewModel;

    public UserAdapter(UserViewModel userViewModel) {
        super(new DiffUtil.ItemCallback<User>() {//处理两个列表差异化的回调
            @Override
            public boolean areItemsTheSame(@NonNull User oldItem, @NonNull User newItem) {//先比较列表中的元素
                return oldItem.getUser_admin().equals(newItem.getUser_admin());
            }

            @Override
            public boolean areContentsTheSame(@NonNull User oldItem, @NonNull User newItem) {//再比较内容
                return (oldItem.getUser_money() == newItem.getUser_money()
                        && oldItem.getUser_password().equals(newItem.getUser_password()));
            }
        });
        this.userViewModel = userViewModel;

    }


    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {//创建ViewHolder时呼叫
        //从Layout文件中加载View需要使用 LayoutInflater
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View itemView;
        itemView = layoutInflater.inflate(R.layout.recyclerview_item_user, parent, false);//加载的子控件
        //子控件传入ViewHolder
        final UserViewHolder holder = new UserViewHolder(itemView);//holder来自内部类，需要final修饰

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {
        final User user = getItem(position);
        holder.tv_user_admin.setText(user.getUser_admin());
        holder.tv_user_password.setText(user.getUser_password());
        holder.tv_user_money.setText(String.valueOf(user.getUser_money()));
    }


    //ViewHolder  适配器，相当于内容管理器
    class UserViewHolder extends RecyclerView.ViewHolder {
        TextView tv_user_admin, tv_user_password, tv_user_money;

        public UserViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_user_admin = itemView.findViewById(R.id.textView_user_admin);
            tv_user_password = itemView.findViewById(R.id.textView_user_password);
            tv_user_money = itemView.findViewById(R.id.textView_user_money);

        }
    }
}
