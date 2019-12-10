package com.example.ec_app.fragments.baseFragments;


import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.room.PrimaryKey;

import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ec_app.MainActivity;
import com.example.ec_app.R;
import com.example.ec_app.ViewModels.UserViewModel;
import com.example.ec_app.fragments.BaseFragment;
import com.example.ec_app.fragments.SignInFragment;
import com.example.ec_app.users.User;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class UserMessageFragment extends Fragment {
    private static final String TAG = "UserMessageFragment";
    private UserViewModel userViewModel;
    private TextView tv_user_message_admin, tv_user_message_money;
    private EditText et_old_password, et_new_password;
    private Button button_user_rechargemoney, button_change_password, button_user_logout, button_user_exit;


    public UserMessageFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_user_message, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        init();
    }

    private void init() {
        initView();
        initEvent();
        initDate();
    }

    private void initEvent() {
        String admin = getArguments().getString("admin");
        final List<User> list = userViewModel.findUserWithPattern(admin);
        Log.d(TAG, "initEvent: "+ userViewModel);
        TextWatcher watcher = new TextWatcher() {//监控文本变化
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                button_change_password.setEnabled((et_old_password.getText().toString().equals(list.get(0).getUser_password())) &&
                        (!et_new_password.getText().toString().isEmpty()));
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        };
        et_old_password.addTextChangedListener(watcher);
        et_new_password.addTextChangedListener(watcher);

        button_user_rechargemoney.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final EditText input = new EditText(getContext());
                input.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
//                number 和 numberDecimal  属性要同时设置    这个是可以输入float类型的
                input.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        //s     输入框中改变前的字符串信息
                        //start 输入框中改变前的字符串的起始位置
                        //count 输入框中改变前后的字符串改变数量一般为0
                        //after 输入框中改变后的字符串与起始位置的偏移量
                    }

                    @Override
                    public void afterTextChanged(Editable s) {
                        if (s.length() > 10) input.setError("输入超长");
                    }
                });
                AlertDialog.Builder dialog = new AlertDialog.Builder(getContext());
                //获取AlertDialog对象
                dialog.setView(input);
                dialog.setTitle("充值");//设置标题
                dialog.setMessage("请输入充值的金额");//设置信息具体内容
                dialog.setCancelable(false);//设置是否可取消
                dialog.setPositiveButton("确认", new DialogInterface.OnClickListener() {
                    @Override//设置ok的事件
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //在此处写入ok的逻辑
                        User user = list.get(0);
                        user.setUser_money(user.getUser_money() + Float.valueOf(input.getText().toString()));
                        userViewModel.updateUser(user);
                        tv_user_message_money.setText(String.valueOf(user.getUser_money()));
                        Toast.makeText(getContext(), "充值成功", Toast.LENGTH_SHORT).show();
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

        button_change_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                User user = list.get(0);
                user.setUser_password(et_new_password.getText().toString().trim());
                userViewModel.updateUser(user);
                et_old_password.setText("");
                et_new_password.setText("");
                Toast.makeText(getContext(), "修改密码成功", Toast.LENGTH_SHORT).show();
            }
        });
        button_user_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                User user = list.get(0);
                userViewModel.deleteUser(user);
                Toast.makeText(getContext(), "注销成功，3秒后返回登录页面", Toast.LENGTH_SHORT).show();
                new Thread() {
                    @Override
                    public void run() {
                        super.run();
                        try {
                            Thread.sleep(3000);
                            NavController controller = Navigation.findNavController(getView().getRootView().findViewById(R.id.fragment));
                            controller.navigate(R.id.signInFragment);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                    }
                }.start();
            }
        });
        button_user_exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "退出登录成功，3秒后返回登录页面", Toast.LENGTH_SHORT).show();
                new Thread() {
                    @Override
                    public void run() {
                        super.run();
                        try {
                            Thread.sleep(3000);
                            NavController controller = Navigation.findNavController(getView().getRootView().findViewById(R.id.fragment));
                            controller.navigate(R.id.signInFragment);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }.start();
            }
        });


    }

    private void initDate() {
        String admin = getArguments().getString("admin");
        List<User> list = userViewModel.findUserWithPattern(admin);
        User user = list.get(0);

//        String TAG = "UserMessageFragment";

//        Log.d(TAG, "initDate: "+user.getUser_admin());
        tv_user_message_admin.setText(String.valueOf(user.getUser_admin()));
        tv_user_message_money.setText(String.valueOf(user.getUser_money()));

    }

    private void initView() {

        userViewModel = ViewModelProviders.of(requireActivity()).get(UserViewModel.class);
        tv_user_message_admin = getActivity().findViewById(R.id.textView_user_message_admin);
        tv_user_message_money = getActivity().findViewById(R.id.textView_user_message_money);
        et_old_password = getActivity().findViewById(R.id.editText_user_old_password);
        et_new_password = getActivity().findViewById(R.id.editText_user_new_password);
        button_user_rechargemoney = getActivity().findViewById(R.id.button_user_rechargemoney);
        button_change_password = getActivity().findViewById(R.id.button_change_password);
        button_change_password.setEnabled(false);
        button_user_logout = getActivity().findViewById(R.id.button_user_logout);
        button_user_exit = getActivity().findViewById(R.id.button_user_exit);
    }
}
