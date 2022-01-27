package com.sso.user.service.impl;

import com.sso.user.pojo.User;
import com.sso.user.service.UserService;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * @Author: golf
 * @Date: 2021/12/1 0:21
 */
@Service
public class UserServiceImpl implements UserService {

    /**
     * 用户和token信息 key为token,value为用户信息
     */
    private static final Map<String, User> USER_TOKEN_INFO = new HashMap<>();

    /**
     * 用户信息
     */
    private static final Set<User> USER_MAP = new HashSet<>();

    static {
        User user1 = new User();
        user1.setName("glf");
        user1.setPassword("123");
        User user2 = new User();
        user2.setName("dfb");
        user2.setPassword("123");
        USER_MAP.add(user1);
        USER_MAP.add(user2);
    }

    @Override
    public void add(String ssoToken, User user) {
        USER_TOKEN_INFO.put(ssoToken, user);
    }

    @Override
    public void remove(String ssoToken) {
        USER_TOKEN_INFO.remove(ssoToken);
    }

    @Override
    public User getUserInfoByToken(String token) {
        return USER_TOKEN_INFO.get(token);
    }

    @Override
    public boolean verifyUserNameAndPassword(String userName, String password) {
        for (User user : USER_MAP) {
            if (user.getName().equals(userName) && user.getPassword().equals(password)) {
                return true;
            }
        }
        return false;
    }

}
