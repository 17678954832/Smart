package com.muhan.smart.dao;

import com.muhan.smart.pojo.MallUser;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

public interface MallUserMapper {
    @Select("select * from mall_user where role=#{role}")
    MallUser findByRole(@Param("role") Integer role);
}
