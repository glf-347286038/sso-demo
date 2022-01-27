package com.delta.controller;

import com.delta.util.SsoClientUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @Author: gaolingfeng
 * @Date: 2021/11/25 0:47
 */
@Slf4j
@Controller
@RequestMapping("login")
public class LoginController {

    @GetMapping("/index")
    public Object index(Model model) {
        model.addAttribute("logoutUrl", SsoClientUtil.SSO_SERVER_LOGOUT_URL);
        return "/index";
    }
}
