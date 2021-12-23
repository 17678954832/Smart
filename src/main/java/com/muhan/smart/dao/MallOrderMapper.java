package com.muhan.smart.dao;

import com.muhan.smart.pojo.MallOrder;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

public interface MallOrderMapper {

    //使用注解的方式
    @Select("select * from mall_order where id = #{id}")
    MallOrder findById(@Param("id") Integer id);

    //使用xml的方式
    MallOrder queryById(Integer id);
}
