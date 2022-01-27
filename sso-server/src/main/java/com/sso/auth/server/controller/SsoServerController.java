package com.sso.auth.server.controller;

import com.sso.auth.server.pojo.ClientInfoVo;
import com.sso.auth.server.service.SsoServerService;
import com.sso.user.pojo.User;
import com.sso.user.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @Author: golf
 * @Date: 2021/11/28 20:32
 */
@Slf4j
@Controller
@RequestMapping("sso")
public class SsoServerController {

    private final UserService userService;
    private static final String SSO_TOKEN = "ssoToken";
    private static final String REDIRECT = "redirect:";
    private static final String REDIRECT_URL = "redirectUrl";
    private static final String LOGIN = "login";
    private final SsoServerService ssoServerService;

    public SsoServerController(UserService userService, SsoServerService ssoServerService) {
        this.userService = userService;
        this.ssoServerService = ssoServerService;
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
            httpSession.setAttribute(SSO_TOKEN, token);
            // 将token返回给客户端
            redirectAttributes.addAttribute(SSO_TOKEN, token);
            // 跳转到来的地方
            return REDIRECT + redirectUrl;
        }
        // 登录不成功
        log.info("用户名:{},密码不正确", userName);
        model.addAttribute(REDIRECT_URL, redirectUrl);
        return LOGIN;
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
    public String checkLogin(String redirectUrl, HttpSession httpSession, RedirectAttributes redirectAttributes, Model model) {
        // 判断用户是否登录了,是否拥有全局的会话,即token
        String token = (String) httpSession.getAttribute(SSO_TOKEN);
        log.error("checkLogin的session" + httpSession.getId());
        if (StringUtils.isEmpty(token)) {
            // 没有全局会话,去登录页面,从哪里来不能丢
            model.addAttribute(REDIRECT_URL, redirectUrl);
            // 跳转到登录页面
            return LOGIN;
        } else {
            // 存在全局会话,返回到原来的地方
            redirectAttributes.addAttribute(SSO_TOKEN, token);
            return REDIRECT + redirectUrl;
        }
    }

    @PostMapping("/verify")
    @ResponseBody
    public User verifyToken(String ssoToken, String clientUrl, String sessionId) {
        // 通过token查找用户信息
        User user = userService.getUserInfoByToken(ssoToken);
        if (user != null) {
            log.info("认证中心校验通过,已存在当前会话");
            // 保存用户的登录地址信息
            List<ClientInfoVo> clientInfoVoList = ssoServerService.getClient(ssoToken);
            if (CollectionUtils.isEmpty(clientInfoVoList)) {
                clientInfoVoList = new ArrayList<>();
            }
            ClientInfoVo clientInfoVo = new ClientInfoVo();
            clientInfoVo.setClientUrl(clientUrl);
            clientInfoVo.setSessionId(sessionId);
            clientInfoVoList.add(clientInfoVo);
            ssoServerService.addClient(ssoToken, clientInfoVoList);
        }
        return user;
    }

    @GetMapping("logout")
    public String logout(HttpSession httpSession) {
        httpSession.invalidate();
        // 通知用户登录过的系统全部下线,使用监听器实现,因为session会存在自动过期的问题
        return LOGIN;
    }
}
