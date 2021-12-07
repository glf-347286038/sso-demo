package com.delta.controller;

import com.delta.interceptor.SsoClientInterceptor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: gaolingfeng
 * @Date: 2021/11/25 0:47
 */
@Slf4j
@RestController
@RequestMapping("login")
public class LoginController {

    @GetMapping("/index")
    public Object index(String ssoToken) {
        log.info("姓名：" + SsoClientInterceptor.SSO_USER_TOKEN_INFO.get(ssoToken));
        return "你好!" + SsoClientInterceptor.SSO_USER_TOKEN_INFO.get(ssoToken) + " 欢迎登录delta系统";
    }
}
