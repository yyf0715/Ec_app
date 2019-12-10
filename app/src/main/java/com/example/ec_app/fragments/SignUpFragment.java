package com.example.ec_app.fragments;


import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.renderscript.Sampler;
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

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class SignUpFragment extends Fragment {
    private EditText et_admin, et_password, et_password_2;
    private Button button_ok, button_canle;
    private UserViewModel userViewModel;
    private List<User> list_find_user;

    public SignUpFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_sign_up, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        initView();//初始化控件
    }

    private void initView() {
        userViewModel = ViewModelProviders.of(requireActivity()).get(UserViewModel.class);//绑定ViewModel

        et_admin = getActivity().findViewById(R.id.et_sign_up_admin);
        et_password = getActivity().findViewById(R.id.et_sign_up_password);
        et_password_2 = getActivity().findViewById(R.id.et_sign_up_password2);
        button_ok = getActivity().findViewById(R.id.button_ok);
        button_canle = getActivity().findViewById(R.id.button_canle);

        button_ok.setEnabled(false);//进入是无法使用按键

        TextWatcher watcher = new TextWatcher() {//当文本框内容发生改变的时候的监听器
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //当editText的内容发生过改变时，呼叫该内容
                String admin = et_admin.getText().toString().trim();
                String password = et_password.getText().toString().trim();
                String password2 = et_password_2.getText().toString().trim();

                button_ok.setEnabled(!(admin.isEmpty() || password.isEmpty() || password2.isEmpty())
                        && (password.equals(password2)));

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        };

        et_admin.addTextChangedListener(watcher);
        et_password.addTextChangedListener(watcher);
        et_password_2.addTextChangedListener(watcher);

        button_canle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavController controller = Navigation.findNavController(v);
                controller.navigate(R.id.action_signUpFragment_to_signInFragment);
            }
        });
        button_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String admin = et_admin.getText().toString().trim();
                String password = et_password.getText().toString().trim();
                User user = new User(admin, password, 0);
                list_find_user = userViewModel.findUserWithPattern(admin);
                boolean repet = false;
                for (int i = 0; i < list_find_user.size(); i++) {
                    if (list_find_user.get(i).getUser_admin().equals(admin)) {
                        repet = true;
                    }
                }
                if (repet) {
                    Toast.makeText(getActivity(), "该用户名已被注册", Toast.LENGTH_SHORT).show();
                } else {
                    userViewModel.insertUser(user);
                    Toast.makeText(getActivity(), "注册成功", Toast.LENGTH_SHORT).show();
                    new Thread() {
                        @Override
                        public void run() {
                            super.run();
                            try {
                                Thread.sleep(3000);
                                NavController controller = Navigation.findNavController(getView());
                                controller.navigate(R.id.action_signUpFragment_to_signInFragment);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    }.start();


                }

            }
        });
    }
}
