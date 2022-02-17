package com.muhan.smart.listener;

import com.google.gson.Gson;
import com.muhan.smart.pojo.PayInfo;
import com.muhan.smart.service.IOrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @Author: Muhan.Zhou
 * @Description 支付消息
 * @Date 2022/2/16 14:17
 */
@Component
@RabbitListener(queues = "payNotify")
@Slf4j
public class PayMsgListener {

    @Autowired
    private IOrderService orderService;

    /**
     * 用此方法来监听接收到的消息
     */
    @RabbitHandler
    public void process(String msg){
        log.info("【接收到消息】 => {}  " , msg);

        PayInfo payInfo = new Gson().fromJson(msg, PayInfo.class);

        //TODO  支付状态获取优化，“SUCCESS”不应该写死，通过通信方式获取，
        if (payInfo.getPlatformStatus().equals("SUCCESS")){
            //修改订单里的状态
            orderService.paid(payInfo.getOrderNo());

        }
    }

}
