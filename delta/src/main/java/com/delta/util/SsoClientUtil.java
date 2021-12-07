package com.delta.util;

import lombok.SneakyThrows;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Properties;

/**
 * @Author: gaolingfeng
 * @Date: 2021/11/28 18:23
 */
public class SsoClientUtil {

    private SsoClientUtil() {
    }

    private static final Properties SSO_PROPERTIES = new Properties();
    /**
     * 统一认证中心地址
     */
    public static final String SSO_SERVER_URL_PREFIX;
    /**
     * 当前客户端地址
     */
    public static final String CLIENT_HOST_URL;

    static {
        try {
            SSO_PROPERTIES.load(SsoClientUtil.class.getClassLoader().getResourceAsStream("application.yaml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        SSO_SERVER_URL_PREFIX = SSO_PROPERTIES.getProperty("sso-server-url-prefix");
        CLIENT_HOST_URL = SSO_PROPERTIES.getProperty("client-host-url");
    }

    /**
     * 当客户端请求被拦截,跳到统一认证中心，需要带redirectUrl的参数,统一认证中心登陆后回调的地址
     *
     * @param httpServletRequest 请求
     * @return 统一认证中心回调的地址
     */
    public static String getRedirectUrl(HttpServletRequest httpServletRequest) {
        // 获取请求URL
        return CLIENT_HOST_URL + httpServletRequest.getServletPath();
    }

    /**
     * 根据httpServletRequest获取跳转到统一认证中心的地址,通过httpServletResponse跳转到指定的地址
     *
     * @param httpServletRequest  请求
     * @param httpServletResponse 响应
     */
    @SneakyThrows
    public static void redirectToSsoUrl(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        String redirectUrl = getRedirectUrl(httpServletRequest);
        String url = SSO_SERVER_URL_PREFIX +
                "/checkLogin?redirectUrl=" +
                redirectUrl;
        httpServletResponse.sendRedirect(url);
    }
}
