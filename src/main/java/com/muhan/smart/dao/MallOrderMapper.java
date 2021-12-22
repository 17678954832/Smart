package com.muhan.smart.dao;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

public interface MallOrderMapper {

    @Select("select * from mall_order")
    MallOrderMapper findById(@Param("id") Integer id);
}
