package com.example.ec_app.ViewModels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.ec_app.data_base.UserRepository;
import com.example.ec_app.users.User;

import java.util.List;

public class UserViewModel extends AndroidViewModel {//AndroidViewModel带有参数
    //通过仓库类来获取和存储
    private UserRepository userRepository;

    public UserViewModel(@NonNull Application application) {
        super(application);
        userRepository = new UserRepository(application);
    }

    public LiveData<List<User>> getAllUserLive() {

        return userRepository.getUserAllLive();
    }
    public List<User> findUserWithPattern(String pattern){
        return userRepository.findUserWithPattern(pattern);
    }

    public void insertUser(User... users) {//插入
        userRepository.insertUser(users);
    }

    public void deleteUser(User... users) {//删除
        userRepository.deleteUser(users);
    }

    public void updateUser(User... users) {//更改
        userRepository.updateUser(users);
    }
}
