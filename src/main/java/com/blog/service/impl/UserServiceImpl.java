package com.blog.service.impl;

import com.blog.mapper.UserMapper;
import com.blog.pojo.User;
import com.blog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Classname UserServiceImpl
 * @Description TODO
 * @Date 2020/11/18 15:12
 * @Created by wlnsss
 */
@Service("userService")
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;

    @Override
    public void register(User user) {
        userMapper.registerUser(user);
    }

    @Override
    public Map<String, List<String>> existsUserInfo(User user) {
        Map result = new HashMap<String,List<String>>();
        List <String> errorInfo = new ArrayList<String>();
        if(userMapper.existsUserName(user) > 0){
            errorInfo.add("存在相同的用户名！");
        }
        if(userMapper.existsUserAccount(user) > 0){
            errorInfo.add("存在相同的账号！");
        }
        if(userMapper.existsUserPhone(user) > 0){
            errorInfo.add("存在相同的手机号！");
        }
        result.put("errorInfo",errorInfo);
        return result;
    }
}
