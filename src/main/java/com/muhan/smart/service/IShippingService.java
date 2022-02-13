package com.muhan.smart.service;

import com.github.pagehelper.PageInfo;
import com.muhan.smart.form.ShippingForm;
import com.muhan.smart.view.ResponseView;

import java.util.Map;

/**
 * @Author: Muhan.Zhou
 * @Description 收货地址
 * @Date 2022/2/12 18:11
 */
public interface IShippingService {

    /**
     * 新增地址
     * @param uid  用户id
     * @param shippingForm  入参对象
     * @return
     */
    ResponseView<Map<String, Integer>> add(Integer uid, ShippingForm shippingForm);

    /**
     * 删除地址
     * @param uid  用户id
     * @param shippingId  地址id
     * @return
     */
    ResponseView delete(Integer uid, Integer shippingId);

    /**
     * 修改地址
     * @param uid  用户id
     * @param shippingId  地址id
     * @param shippingForm 入参对象
     * @return
     */
    ResponseView update(Integer uid, Integer shippingId,ShippingForm shippingForm);

    /**
     * 地址列表
     * @param uid  用户id
     * @param pageNum  页码
     * @param pageSize  页面条数
     * @return
     */
    ResponseView<PageInfo> list(Integer uid, Integer pageNum,Integer pageSize);
}
