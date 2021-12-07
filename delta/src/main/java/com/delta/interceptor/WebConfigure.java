package com.delta.interceptor;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 拦截器配置类
 *
 * @Author: gaolingfeng
 * @Date: 2021/11/28 20:23
 */
@Configuration
public class WebConfigure implements WebMvcConfigurer {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(ssoClientInterceptor()).addPathPatterns("/**")
                // 排除路径
                .excludePathPatterns("/login/");
    }

    /**
     * 配置跨域
     * @param registry
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("*")
                .allowedMethods("GET", "HEAD", "POST", "PUT", "PATCH", "DELETE", "OPTIONS", "TRACE")
                // registry.allowCredentials(true)设置是否允许客户端发送cookie信息。默认是false
                .allowCredentials(true);
    }
    @Bean
    public SsoClientInterceptor ssoClientInterceptor() {
        return new SsoClientInterceptor();
    }

}
