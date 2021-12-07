package com.beta.interceptor;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.beta.util.HttpUtil;
import com.beta.util.SsoClientUtil;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author: gaolingfeng
 * @Date: 2021/11/24 23:43
 */
@Slf4j
public class SsoClientInterceptor implements HandlerInterceptor {

    public static final Map<String, String> SSO_USER_TOKEN_INFO = new HashMap<>();

    /**
     * 前置拦截，true放行,false拦截
     *
     * @param request  请求
     * @param response 响应
     * @param handler  处理器
     * @return 是否拦截
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        // 1.判断是否存在会话
        HttpSession httpSession = request.getSession();
        log.error("beta："+httpSession.getId());
        Boolean isLogin = (Boolean) httpSession.getAttribute("isLogin");
        if (isLogin != null && isLogin) {
            log.info("已登录,放行");
            return true;
        }

        // 判断token
        String ssoToken = request.getParameter("ssoToken");
        if (!StringUtils.isEmpty(ssoToken)) {
            log.info("获取到服务器的token:{}", ssoToken);
            // 去sso服务器验证token,当前系统是A,若A未登录,是B已登录,ssoToken也是有值的,所以A要去sso验证
            JSONObject requestParam = new JSONObject();
            requestParam.put("ssoToken", ssoToken);
            String result = HttpUtil.sendPostRequest(SsoClientUtil.SSO_SERVER_URL_PREFIX + "/verify", requestParam);
            if (!StringUtils.isEmpty(result)) {
                log.info("校验通过");
                SSO_USER_TOKEN_INFO.put(ssoToken, JSON.parseObject(result, SsoUser.class).getName());
                httpSession.setAttribute("isLogin", true);
            }
            return Boolean.parseBoolean(result);
        }

        // 不存在会话,跳转到统一认证中心,检测系统是否登录,参数要带上当前的地址,为了之后可以跳转回来
        // http:localhost:5000/sso/checkLogin?redirectUrl=http://localhost:5004
        SsoClientUtil.redirectToSsoUrl(request, response);
        return false;
    }


    @Data
    static class SsoUser {
        private String name;
    }
}
