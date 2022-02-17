package com.muhan.smart.dao;

import com.muhan.smart.pojo.Shipping;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Set;

public interface ShippingMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Shipping record);

    int insertSelective(Shipping record);

    Shipping selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Shipping record);

    int updateByPrimaryKey(Shipping record);

    /**
     * 根据用户id和地址id删除地址
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

    /**
     * 查询当前用户的收货地址
     * @param uid    用户id
     * @param shippingId  收货地址id
     * @return  地址list
     */
    Shipping selectByUidAndShippingId(@Param("uid") Integer uid,@Param("shippingId") Integer shippingId);

    /**
     * 通过地址id集合查询地址
     * @param idSet
     * @return
     */
    List<Shipping> selectByIdSet(@Param("idSet") Set idSet);



}