package com.blog.service;

import com.blog.pojo.User;

import java.util.List;
import java.util.Map;

public interface UserService {
    /**
     * @param user 待注册的用胡
     * @Description: 注册用户
     */
    void register(User user);

    /**
     * @param user 查看是否存在相同账号、用户名、手机号的用户
     * @Description: 注册用户
     * @return
     */
    Map<String, List<String>> existsUserInfo(User user);
}
