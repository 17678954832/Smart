package com.muhan.smart.service;

import com.github.pagehelper.PageInfo;
import com.muhan.smart.vo.OrderVo;
import com.muhan.smart.vo.ResponseVo;

/**
 * @Author: Muhan.Zhou
 * @Description 订单接口
 * @Date 2022/2/13 13:17
 */
public interface IOrderService {

    /**
     * 创建订单
     * @param uid  用户id
     * @param shippingId  收货地址id
     * @return
     */
    ResponseVo<OrderVo> createOrder(Integer uid,Integer shippingId);


    /**
     * 查询订单列表
     * @param uid  用户id
     * @param pageNum  分页
     * @param pageSize
     * @return  分页数据
     */
    ResponseVo<PageInfo> orderList(Integer uid, Integer pageNum,Integer pageSize);


    /**
     * 订单详情
     * @param uid
     * @param orderNo
     * @return
     */
    ResponseVo<OrderVo> orderDetails(Integer uid,Long orderNo);

    /**
     * 通过uid和orderNo取消订单
     * @param uid
     * @param orderNo
     * @return
     */
    ResponseVo cancelOrder(Integer uid , Long orderNo);

    /**
     * 修改订单支付状态
     * @param orderNo
     * @return
     */
    void paid(Long orderNo);


}
