package com.muhan.smart.service.impl;

import com.muhan.smart.SmartApplicationTests;
import com.muhan.smart.enums.ResponseEnum;
import com.muhan.smart.enums.RoleEnum;
import com.muhan.smart.pojo.User;
import com.muhan.smart.service.IUserService;
import com.muhan.smart.view.ResponseView;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
@Transactional //加注解可以实现功能，不污染数据库
public class UserServiceImplTest extends SmartApplicationTests {

    public static final String USERNAME = "admin";
    public static final String PASSWORD = "123456";
    public static final String EMAIL = "1905240752@qq.com";

    @Autowired
    private IUserService userService;

    @Before  //这个注解会在先执行这个方法
    public void register() {
       User user = new User(USERNAME,PASSWORD,EMAIL, RoleEnum.ADMIN.getCode());
       userService.register(user);
    }

    @Test
    public void login(){
        //先注册
        //register();

        //登录
        ResponseView<User> userResponseView = userService.login(USERNAME, PASSWORD);

        //判断登录状态是否为0  前面是我们期待的，后面是实际的
        Assert.assertEquals(ResponseEnum.SUCCESS.getCode(),userResponseView.getStatus());
    }
}