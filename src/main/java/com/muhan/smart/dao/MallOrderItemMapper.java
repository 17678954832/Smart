package com.muhan.smart.dao;

import com.muhan.smart.pojo.MallOrderItem;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

public interface MallOrderItemMapper {

    @Select("select * from mall_order_item where id = #{id}")
    MallOrderItem findById(@Param("id") Integer id);

    MallOrderItem queryById(Integer id);


}
