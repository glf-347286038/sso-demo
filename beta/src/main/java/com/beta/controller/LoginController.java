package com.beta.controller;

import com.beta.config.SsoConfig;
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
    private final SsoConfig ssoConfig;

    public LoginController(SsoConfig ssoConfig) {
        this.ssoConfig = ssoConfig;
    }

    @GetMapping("/index")
    public String index(Model model) {
        model.addAttribute("logoutUrl", ssoConfig.getServerLogoutUrl());
        return "/index";
    }
}
