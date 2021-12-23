package com.muhan.smart.dao;

import com.muhan.smart.pojo.MallCategory;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

//@Mapper  可以不用写，在主类里面加MapperScan也可以
public interface MallCategoryMapper {

    //mybits使用注解的方式
    @Select("select * from mall_category where id = #{id}")
    MallCategory findById(@Param("id") Integer id);

    //mtbatis使用xml的方式
    MallCategory queryById(Integer id);



}
