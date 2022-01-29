package com.beta.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @Author: golf
 * @Date: 2022/1/29 12:10
 */
@Configuration
@ConfigurationProperties(prefix = "sso")
@Data
public class SsoConfig {
    /**
     * 认证中心url
     */
    private String serverUrl;
    /**
     * 统一认证中心检查登录接口
     */
    private String serverCheckLogin;
    /**
     * 统一认证校验token接口
     */
    private String serverVerify;
    /**
     * 统一认证中心退出接口
     */
    private String serverLogout;
    /**
     * 当前客户端ip
     */
    private String clientUrl;
    /**
     * 客户端退出接口
     */
    private String clientLogoutUrl;

    /**
     * 获得客户端退出登陆全地址
     *
     * @return 客户端退出登陆地址
     */
    public String getClientLogoutUrl() {
        return clientUrl + clientLogoutUrl;
    }

    /**
     * 获得认证中心退出地址
     * ip:port/sso/logout
     *
     * @return 认证中心退出地址
     */
    public String getServerLogoutUrl() {
        return serverUrl + serverLogout;
    }

    /**
     * 获得认证中心检查是否已经登陆的地址
     * ip:port/sso/checkLogin
     *
     * @return 认证中心检查登陆地址
     */
    public String getServerCheckLoginUrl() {
        return serverUrl + serverCheckLogin;
    }

    /**
     * 获得认证中心校验token的地址
     * ip:port/sso/verify
     *
     * @return 认证中心校验token的地址
     */
    public String getServerVerifyUrl() {
        return serverUrl + serverVerify;
    }
}
