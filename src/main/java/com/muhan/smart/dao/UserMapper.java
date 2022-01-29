package com.muhan.smart.dao;

import com.muhan.smart.pojo.User;

public interface UserMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);

    //统计用户名
    int countByUsername(String username);

    //统计email
    int countByEmail (String email);

    //登录
    User selectByUsername(String username);

}