package com.muhan.smart.interceptor;

import com.muhan.smart.consts.SmartConst;
import com.muhan.smart.exception.UserLoginException;
import com.muhan.smart.pojo.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Author: Muhan.Zhou
 * @Description 登录拦截器
 * @Date 2022/1/29 11:21
 */
@Slf4j
public class UserLoginInterceptor implements HandlerInterceptor {

    /**
     * 在请求之前做登录拦截
     * true表示继续流程，false表示中断流程
     * @param request
     * @param response
     * @param handler
     * @return
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        log.info("handle.....");
        User user =  (User) request.getSession().getAttribute(SmartConst.CURRENT_USER);
        //判断是否登录
        if (user == null){
            log.info("user = null");

            //抛一个自定义异常，交给统一异常处理，返回错误json给前端
            throw new UserLoginException();

            //return false;
            //return ResponseView.error(ResponseEnum.NEED_LOGIN);
        }
        return true;
    }
}
