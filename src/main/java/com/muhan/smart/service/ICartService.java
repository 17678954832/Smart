package com.muhan.smart.service;

import com.muhan.smart.form.CartAddForm;
import com.muhan.smart.form.CartUpdataForm;
import com.muhan.smart.pojo.Cart;
import com.muhan.smart.vo.CartVo;
import com.muhan.smart.vo.ResponseVo;

import java.util.List;

/**
 * @Author: Muhan.Zhou
 * @Description 购物车接口
 * @Date 2022/2/9 21:11
 */
public interface ICartService {

    /**
     * 添加购物车
     * @param cartAddForm
     * @return
     */
    ResponseVo<CartVo> add(Integer uid, CartAddForm cartAddForm);


    /**
     * 获取购物车列表
     * @param uid
     * @return
     */
    ResponseVo<CartVo> list(Integer uid);

    /**
     * 更新购物车
     * @param uid
     * @param productId  商品id
     * @param form  参数
     * @return
     */
    ResponseVo<CartVo> update(Integer uid, Integer productId , CartUpdataForm form);


    /**
     * 删除购物车商品
     * @param uid
     * @param productId
     * @return
     */
    ResponseVo<CartVo> delete(Integer uid, Integer productId);

    /**
     * 全选购物车
     * @param uid
     * @return
     */
    ResponseVo<CartVo> selectAll(Integer uid);

    /**
     * 全不选购物车
     * @param uid
     * @return
     */
    ResponseVo<CartVo> unSelectAll(Integer uid);

    /**
     * 商品的总和
     * @param uid
     * @return
     */
    ResponseVo<Integer> sum(Integer uid);

    /**
     * 获取购物车
     * @param uid
     * @return
     */
    List<Cart> listForCart(Integer uid);

}
