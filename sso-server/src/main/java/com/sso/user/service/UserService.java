package com.sso.user.service;

import com.sso.user.pojo.User;

/**
 * @author gaolingfeng
 */
public interface UserService {
    /**
     * 新增用户信息
     *
     * @param ssoToken sso生成的token
     * @param user     用户信息
     */
    void add(String ssoToken, User user);

    /**
     * 删除token
     *
     * @param ssoToken token
     */
    void remove(String ssoToken);

    /**
     * 根据token获取用户信息
     *
     * @param token token
     * @return 用户信息
     */
    User getUserInfoByToken(String token);

    /**
     * 校验用户名密码
     *
     * @param userName 用户名
     * @param password 密码
     * @return 校验结果
     */
    boolean verifyUserNameAndPassword(String userName, String password);
}
