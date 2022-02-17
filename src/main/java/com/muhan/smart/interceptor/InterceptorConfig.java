package com.muhan.smart.interceptor;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @Author: Muhan.Zhou
 * @Description 拦截器配置
 * @Date 2022/1/29 11:31   这里要设置跨域
 */
@Configuration
public class InterceptorConfig implements WebMvcConfigurer {
    //TODO  跨域
    /*@Override
    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
        WebMvcConfigurer.super.configureDefaultServletHandling(configurer);
    }*/

    /**
     * 配置拦截器
     * @param registry
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new UserLoginInterceptor())  //把我们写的拦截器注册上去
                .addPathPatterns("/**")  //默认对所有拦截
                .excludePathPatterns("/user/login","/user/register","/categories","/products","/products/*","/error");   //不拦截的接口


    }
}
