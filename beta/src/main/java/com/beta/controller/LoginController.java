package com.beta.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: gaolingfeng
 * @Date: 2021/11/25 0:47
 */
@Slf4j
@Controller
@RequestMapping("login")
public class LoginController {

    @GetMapping("/index")
    public String index(String ssoToken) {
        return "/index";
    }
}
