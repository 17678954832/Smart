package com.muhan.smart.controller;

import com.github.pagehelper.PageInfo;
import com.muhan.smart.form.OrderCreateForm;
import com.muhan.smart.pojo.User;
import com.muhan.smart.service.IOrderService;
import com.muhan.smart.vo.OrderVo;
import com.muhan.smart.vo.ResponseVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import static com.muhan.smart.consts.SmartConst.CURRENT_USER;

/**
 * @Author: Muhan.Zhou
 * @Description 订单
 * @Date 2022/2/16 11:43
 */
@RestController
public class OrderController {

    @Autowired
    private IOrderService orderService;

    /**
     * 创建订单
     * @param orderCreateForm
     * @param session
     * @return
     */
    @PostMapping("/orders")
    public ResponseVo<OrderVo> createOrder(@Valid @RequestBody OrderCreateForm orderCreateForm,
                                           HttpSession session){
        //获取用户id
        User user = (User)session.getAttribute(CURRENT_USER);
        ResponseVo<OrderVo> orderVo = orderService.createOrder(user.getId(), orderCreateForm.getShippingId());
        return  orderVo;
    }


    /**
     * 获取订单列表
     * @param pageNum
     * @param pageSize
     * @param session
     * @return
     */
    @GetMapping("/orders")
    public ResponseVo<PageInfo> orderList(@RequestParam Integer pageNum,
                                          @RequestParam Integer pageSize,
                                          HttpSession session){
        User user = (User)session.getAttribute(CURRENT_USER);
        ResponseVo<PageInfo> pageInfoResponseVo = orderService.orderList(user.getId(), pageNum, pageSize);
        return pageInfoResponseVo;
    }

    /**
     * 获取订单详情
     * @param orderNo
     * @param session
     * @return
     */
    @GetMapping("/orders/{orderNo}")
    public ResponseVo<OrderVo> orderDetails(@PathVariable Long orderNo,
                                            HttpSession session){
        User user = (User)session.getAttribute(CURRENT_USER);
        ResponseVo<OrderVo> responseVo = orderService.orderDetails(user.getId(), orderNo);
        return responseVo;
    }

    /**
     * 取消订单
     * @param orderNo
     * @param session
     * @return
     */
    @PutMapping("/orders/{orderNo}")
    public ResponseVo cancelOrder(@PathVariable Long orderNo,
                                  HttpSession session){
        User user = (User)session.getAttribute(CURRENT_USER);
        ResponseVo responseVo = orderService.cancelOrder(user.getId(), orderNo);
        return responseVo;
    }


}
