package com.muhan.smart.dao;

import com.muhan.smart.pojo.Order;

import java.util.List;

public interface OrderMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Order record);

    int insertSelective(Order record);

    Order selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Order record);

    int updateByPrimaryKey(Order record);

    /**
     * 通过用户id查订单
     * @param uid
     * @return
     */
    List<Order> selectByUid(Integer uid);

    /**
     * 通过orderNo查询数据
     * @param orderNo
     * @return
     */
    Order selectByOrderNo(Long orderNo);
}