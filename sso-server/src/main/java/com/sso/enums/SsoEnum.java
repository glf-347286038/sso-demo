package com.sso.enums;

/**
 * @Author: golf
 * @Date: 2022/1/28 20:21
 */
public enum SsoEnum {
    /**
     * 重定向
     */
    REDIRECT("redirect:"),
    /**
     * 回调标识
     */
    REDIRECT_URL("redirectUrl"),
    /**
     * 登录接口
     */
    LOGIN("login"),
    /**
     * token
     */
    TOKEN("token");

    private final String value;

    SsoEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
