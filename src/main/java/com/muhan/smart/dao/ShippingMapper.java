package com.muhan.smart.dao;

import com.muhan.smart.pojo.Shipping;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ShippingMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Shipping record);

    int insertSelective(Shipping record);

    Shipping selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Shipping record);

    int updateByPrimaryKey(Shipping record);

    /**
     * @param uid  用户id
     * @param shippingId 地址id
     * @return
     */
    int deleteByIdAndUid(@Param("uid") Integer uid, @Param("shippingId") Integer shippingId);

    /**
     * 通过uid查询地址列表
     * @param uid  用户id
     * @return
     */
    List<Shipping> selectByUid(@Param("uid") Integer uid);

}