package com.example.ec_app.fragments;


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
import android.widget.Button;
import android.widget.EditText;

import com.example.ec_app.R;
import com.example.ec_app.ViewModels.UserViewModel;
import com.example.ec_app.adapters.UserAdapter;
import com.example.ec_app.users.User;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class UserFragment extends Fragment {
    private UserViewModel userViewModel;
    private RecyclerView recyclerView_user;
    private EditText et_admin, et_password, et_money;
    private Button button_insert, button_update, button_delete;
    private LiveData<List<User>> allUserLive;

    public UserFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_user, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        final UserAdapter userAdapter = new UserAdapter(userViewModel);

        userViewModel = ViewModelProviders.of(requireActivity()).get(UserViewModel.class);//绑定ViewModel
        recyclerView_user = getActivity().findViewById(R.id.recyclerView_User);//绑定recyclerView
        recyclerView_user.setLayoutManager(new LinearLayoutManager(requireActivity()));//内部为线性布局
        recyclerView_user.setAdapter(userAdapter);

        allUserLive = userViewModel.getAllUserLive();
        allUserLive.observe(getViewLifecycleOwner(), new Observer<List<User>>() {//监控数据变化
            @Override
            public void onChanged(List<User> users) {//数据发生改变，通知适配器
//                int temp = userAdapter.getItemCount();//获取适配器里的item数量
                userAdapter.submitList(users);
            }
        });
        et_admin = getActivity().findViewById(R.id.editTextadmin);
        et_password = getActivity().findViewById(R.id.editTextpassword);
        et_money = getActivity().findViewById(R.id.editTextmoney);
        button_insert = getActivity().findViewById(R.id.button_insert);
        button_update = getActivity().findViewById(R.id.buttonupdate);
        button_delete = getActivity().findViewById(R.id.buttondelete);

        button_insert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                float money = Float.valueOf(et_money.getText().toString());
                User user = new User(et_admin.getText().toString(), et_password.getText().toString(), money);
                userViewModel.insertUser(user);
            }
        });
        button_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                float money = Float.valueOf(et_money.getText().toString());
                User user = new User(et_admin.getText().toString(), et_password.getText().toString(), money);
                userViewModel.updateUser(user);


            }
        });
        button_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                float money = Float.valueOf(et_money.getText().toString());
                User user = new User(et_admin.getText().toString(), et_password.getText().toString(), money);
                userViewModel.deleteUser(user);
            }
        });
    }
}
