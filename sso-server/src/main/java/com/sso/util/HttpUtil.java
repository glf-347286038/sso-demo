package com.sso.util;

import com.alibaba.fastjson.JSONObject;
import lombok.SneakyThrows;
import org.springframework.util.StreamUtils;

import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

/**
 * @Author: gaolingfeng
 * @Date: 2021/11/24 23:54
 */
public class HttpUtil {
    private HttpUtil() {
    }

    /**
     * 发送post请求
     *
     * @return 响应结果
     */
    @SneakyThrows
    public static String sendPostRequest(String requestUrl, JSONObject jsonObject) {
        // 1.访问地址
        URL url = new URL(requestUrl);
        // 2.开启连接
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        // 3.设置请求方式
        urlConnection.setRequestMethod("POST");
        // 4.输出参数
        urlConnection.setDoOutput(true);
        urlConnection.setDoInput(true);
        // 5.拼接参数信息,name=gao&age=18
        if (jsonObject != null && !jsonObject.isEmpty()) {
            StringBuilder stringBuilder = new StringBuilder();
            for (String key : jsonObject.keySet()) {
                stringBuilder.append("&").append(key).append("=").append(jsonObject.getString(key));
            }
            // 6.去除最前面的&输出参数
            urlConnection.getOutputStream().write(stringBuilder.substring(1).getBytes(StandardCharsets.UTF_8));

        }
        // 7.发起请求
        urlConnection.connect();
        // 8.将响应流转为String
        return StreamUtils.copyToString(urlConnection.getInputStream(), StandardCharsets.UTF_8);
    }

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
