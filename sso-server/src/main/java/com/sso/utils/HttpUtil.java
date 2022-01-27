package com.sso.utils;

import lombok.SneakyThrows;

import java.net.HttpURLConnection;
import java.net.URL;

/**
 * @Author: golf
 * @Date: 2021/11/24 23:54
 */
public class HttpUtil {
    private HttpUtil() {
    }

    /**
     * 模拟浏览器发起请求
     *
     * @param httpUrl   请求地址
     * @param sessionId sessionId
     */
    @SneakyThrows
    public static void sendHttpRequest(String httpUrl, String sessionId) {
        URL url = new URL(httpUrl);
        HttpURLConnection httpUrlConnection = (HttpURLConnection) url.openConnection();
        httpUrlConnection.setRequestMethod("POST");
        httpUrlConnection.setDoOutput(true);
        httpUrlConnection.addRequestProperty("Cookie", "JSESSIONID=" + sessionId);
        httpUrlConnection.connect();
        httpUrlConnection.getInputStream();
        httpUrlConnection.disconnect();
    }
}
