package com.beta.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

/**
 * @author lfgao
 */
@Controller
public class LogoutController {
    /**
     * 退出请求只有在点击按钮时才会调用,而session是存在自动过期的问题的，
     *
     * @param httpSession 会话
     */
    @RequestMapping("logout")
    public void logout(HttpSession httpSession) {
        System.out.println("开始退出");
        httpSession.invalidate();
    }
}
