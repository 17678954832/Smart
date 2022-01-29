package com.muhan.smart.service;

import com.muhan.smart.pojo.User;
import com.muhan.smart.view.ResponseView;

/**
 * @Author: Muhan.Zhou
 * @Description
 * @Date 2022/1/28 14:38
 */
public interface IUserService {

    /**
     * 注册
     */
    ResponseView<User> register(User user);


    /**
     * 登录
     */
    ResponseView<User> login(String username,String password);
}
