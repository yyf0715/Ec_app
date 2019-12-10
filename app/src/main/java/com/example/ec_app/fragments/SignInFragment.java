package com.example.ec_app.fragments;


import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.ec_app.R;
import com.example.ec_app.ViewModels.UserViewModel;
import com.example.ec_app.users.User;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class SignInFragment extends Fragment {
    private UserViewModel userViewModel;
    private EditText et_admin,et_password;
    private Button btn_in,btn_up;
    private Bundle bundle = new Bundle();
    public static String User_admin="";


    public SignInFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_sign_in, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        init();
    }

    private void init() {
        userViewModel = ViewModelProviders.of(requireActivity()).get(UserViewModel.class);
        et_admin = getActivity().findViewById(R.id.et_sign_in_admin);
        et_password = getActivity().findViewById(R.id.et_sign_in_password);
        btn_in = getActivity().findViewById(R.id.button_sign_in);
        btn_up = getActivity().findViewById(R.id.button_sign_up);

        btn_in.setEnabled(false);
        TextWatcher watcher = new TextWatcher() {//当文本框内容发生改变的时候的监听器
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String admin = et_admin.getText().toString().trim();
                String password = et_password.getText().toString().trim();
                btn_in.setEnabled(!(admin.isEmpty()||password.isEmpty()));
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        };

        et_admin.addTextChangedListener(watcher);
        et_password.addTextChangedListener(watcher);

        btn_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavController controller = Navigation.findNavController(v);
                controller.navigate(R.id.action_signInFragment_to_signUpFragment);
            }
        });
        btn_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (login()){
                    NavController controller = Navigation.findNavController(v);
                    controller.navigate(R.id.action_signInFragment_to_baseFragment,bundle);
                }

            }
        });

    }

    private boolean login() {
        String admin = et_admin.getText().toString().trim();
        String password = et_password.getText().toString().trim();
        List<User> list = userViewModel.findUserWithPattern(admin);

        if (list.isEmpty()){
            Toast.makeText(requireContext(),"没有你输入的账号",Toast.LENGTH_SHORT).show();
            et_admin.setText("");
            return false;
        }else if (!password.equals(list.get(0).getUser_password())){
            Toast.makeText(requireContext(),"密码错误，请重新输入密码",Toast.LENGTH_SHORT).show();
            et_password.setText("");
            return false;
        }else{
            bundle.putString("user_admin",admin);
            User_admin = admin;
            return true;
        }

    }

}
