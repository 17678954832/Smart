package com.muhan.smart.service;

import com.github.pagehelper.PageInfo;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.muhan.smart.SmartApplicationTests;
import com.muhan.smart.enums.ResponseEnum;
import com.muhan.smart.form.CartAddForm;
import com.muhan.smart.vo.CartVo;
import com.muhan.smart.vo.OrderVo;
import com.muhan.smart.vo.ResponseVo;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Transactional
public class IOrderServiceTest extends SmartApplicationTests {

    @Autowired
    private IOrderService orderService;

    @Autowired
    private ICartService cartService;

    private Integer uid = 1;

    private Integer shippingId = 5;

    private Integer productId = 26;

    private Gson gson = new GsonBuilder().setPrettyPrinting().create();

    @Before
    public void before(){
        log.info("新增购物车");
        CartAddForm cartAddForm = new CartAddForm();
        cartAddForm.setProductId(productId);
        cartAddForm.setSelected(true);

        ResponseVo<CartVo> responseVo = cartService.add(uid, cartAddForm);
        log.info("添加购物车 = {}",gson.toJson(responseVo));
        Assert.assertEquals(ResponseEnum.SUCCESS.getCode(),responseVo.getStatus());
    }

    @Test
    public void createTest(){
        ResponseVo<OrderVo> responseVo = createOrder();
        Assert.assertEquals(ResponseEnum.SUCCESS.getCode(),responseVo.getStatus());
    }


    private ResponseVo<OrderVo> createOrder() {
        ResponseVo<OrderVo> responseVo = orderService.createOrder(uid, shippingId);

        log.info("创建订单 = {}" , gson.toJson(responseVo));
        Assert.assertEquals(ResponseEnum.SUCCESS.getCode(),responseVo.getStatus());
        return responseVo;
    }

    @Test
    public void orderList(){
        ResponseVo<PageInfo> responseVo = orderService.orderList(uid, 1,2);

        log.info("orderList = {}" , gson.toJson(responseVo));
        Assert.assertEquals(ResponseEnum.SUCCESS.getCode(),responseVo.getStatus());
    }

    @Test
    public void orderDetails(){
        //获取订单号
        ResponseVo<OrderVo> order = createOrder();
        ResponseVo<OrderVo> responseVo = orderService.orderDetails(uid, order.getData().getOrderNo());

        log.info("订单详情 = {}" , gson.toJson(responseVo));
        Assert.assertEquals(ResponseEnum.SUCCESS.getCode(),responseVo.getStatus());
    }

    @Test
    public void cancelOrder(){
        //先创建订单获取订单号
        ResponseVo<OrderVo> order = createOrder();
        //取消订单
        ResponseVo responseVo = orderService.cancelOrder(uid, order.getData().getOrderNo());

        log.info("取消订单 = {}" , gson.toJson(responseVo));
        Assert.assertEquals(ResponseEnum.SUCCESS.getCode(),responseVo.getStatus());
    }
}