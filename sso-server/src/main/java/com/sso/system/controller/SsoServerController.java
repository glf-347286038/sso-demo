package com.sso.system.controller;

import com.sso.system.constant.SsoConstant;
import com.sso.user.pojo.User;
import com.sso.user.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.util.UUID;

/**
 * @Author: gaolingfeng
 * @Date: 2021/11/28 20:32
 */
@Slf4j
@Controller
@RequestMapping("sso")
public class SsoServerController {

    private final UserService userService;

    public SsoServerController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("login")
    public String login(String userName, String password, String redirectUrl, HttpSession httpSession, RedirectAttributes redirectAttributes, Model model) {
        // 模拟校验用户名密码是否正确
        if (userService.verifyUserNameAndPassword(userName, password)) {
            // 给用户创建一个token令牌
            String token = UUID.randomUUID().toString();
            log.info("用户:{}生成token成功,token:{}", userName, token);
            // mock保存到缓存中
            User user = new User();
            user.setName(userName);
            userService.add(token, user);
            log.error("登录请求的session" + httpSession.getId());
            // 在服务器中存储全局会话信息
            httpSession.setAttribute(SsoConstant.SSO_TOKEN, token);
            // 将token返回给客户端
            redirectAttributes.addAttribute(SsoConstant.SSO_TOKEN, token);
            // 跳转到来的地方
            return SsoConstant.REDIRECT + redirectUrl;
        }
        // 登录不成功
        log.info("用户名:{},密码不正确", userName);
        model.addAttribute(SsoConstant.REDIRECT_URL, redirectUrl);
        return SsoConstant.LOGIN;
    }

    /**
     * 检查token
     *
     * @param redirectUrl 源地址
     * @param httpSession session
     * @param model       数据
     * @return 地址
     */
    @GetMapping("/checkLogin")
    public String checkLogin(String redirectUrl, HttpSession httpSession, RedirectAttributes redirectAttributes,Model model) {
        // 判断用户是否登录了,是否拥有全局的会话,即token
        String token = (String) httpSession.getAttribute(SsoConstant.SSO_TOKEN);
        log.error("checkLogin的session" + httpSession.getId());
        if (StringUtils.isEmpty(token)) {
            // 没有全局会话,去登录页面,从哪里来不能丢
            model.addAttribute(SsoConstant.REDIRECT_URL, redirectUrl);
            // 跳转到登录页面
            return SsoConstant.LOGIN;
        } else {
            // 存在全局会话,返回到原来的地方
            redirectAttributes.addAttribute(SsoConstant.SSO_TOKEN, token);
            return SsoConstant.REDIRECT + redirectUrl;
        }
    }

    @PostMapping("/verify")
    @ResponseBody
    public User verifyToken(String ssoToken) {
        // 通过token查找用户信息
        return userService.getUserInfoByToken(ssoToken);
    }
}
