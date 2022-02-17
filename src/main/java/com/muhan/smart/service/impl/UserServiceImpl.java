package com.muhan.smart.service.impl;

import com.muhan.smart.dao.UserMapper;
import com.muhan.smart.enums.ResponseEnum;
import com.muhan.smart.enums.RoleEnum;
import com.muhan.smart.pojo.User;
import com.muhan.smart.service.IUserService;
import com.muhan.smart.vo.ResponseVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.nio.charset.StandardCharsets;

/**
 * @Author: Muhan.Zhou
 * @Description UserService实现类
 * @Date 2022/1/28 14:43
 */
@Service
public class UserServiceImpl implements IUserService {

    @Autowired
    private UserMapper userMapper;

    /**
     * 注册
     *
     * @param user
     */
    @Override
    public ResponseVo<User> register(User user) {

        //校验用户名、邮箱不能重复
        int countByUsername = userMapper.countByUsername(user.getUsername());
        if (countByUsername > 0){
            return ResponseVo.error(ResponseEnum.USERNAME_EXIST);
        }
        int countByEmail = userMapper.countByEmail(user.getEmail());
        if (countByEmail > 0){
            return ResponseVo.error(ResponseEnum.EMAIL_EXIST);
        }

        //设置角色,默认都为普通用户
        user.setRole(RoleEnum.CUDTOMER.getCode());

        //MD5摘要算法
        String md5DigestAsHexPassword = DigestUtils.md5DigestAsHex(user.getPassword().getBytes(StandardCharsets.UTF_8));
        user.setPassword(md5DigestAsHexPassword);

        //写入数据库
        int insertSelective = userMapper.insertSelective(user);
        if (insertSelective == 0){
            return ResponseVo.error(ResponseEnum.ERROR);
        }

        return ResponseVo.success();
    }

    /**
     * 登录
     * @param username
     * @param password
     * @return
     */
    //session保存在内存里，容易丢失，服务器、项目重启就丢失了，改进版：token+redis
    @Override
    public ResponseVo<User> login(String username, String password) {
        User user = userMapper.selectByUsername(username);
        //判断用户是否存在
        if (user == null){
            //用户不存在(返回用户名或密码错误)
            return ResponseVo.error(ResponseEnum.USERNAME_OR_PASSWORD_ERROR);
        }

        //校验密码,忽略大小写
        if (!user.getPassword().equalsIgnoreCase(DigestUtils.md5DigestAsHex(password.getBytes(StandardCharsets.UTF_8)))) {
            //密码错误(返回用户名或密码错误)
            return ResponseVo.error(ResponseEnum.USERNAME_OR_PASSWORD_ERROR);
        }

        //将返回数据里面的密码置空
        user.setPassword("");
        return ResponseVo.success(user);
    }

}
