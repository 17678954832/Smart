package com.muhan.smart.dao;

import com.muhan.smart.pojo.Category;
import com.muhan.smart.pojo.MallUser;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

//@Mapper  可以不用写，在主类里面加MapperScan也可以
public interface CategoryMapper {

    @Select("select * from mall_category where id = #{id}")
    Category findById(@Param("id") Integer id);



}
