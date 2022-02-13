package com.muhan.smart.controller;

import com.github.pagehelper.PageInfo;
import com.muhan.smart.consts.SmartConst;
import com.muhan.smart.form.ShippingForm;
import com.muhan.smart.pojo.User;
import com.muhan.smart.service.IShippingService;
import com.muhan.smart.view.ResponseView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.Map;

/**
 * @Author: Muhan.Zhou
 * @Description 收货地址
 * @Date 2022/2/13 11:32
 */
@RestController
public class ShippingController {

    @Autowired
    private IShippingService shippingService;

    /**
     * 添加地址
     * @param shippingForm  参数对象
     * @param session
     * @return
     */
    @PostMapping("/shippings")
    public ResponseView add(@Valid @RequestBody ShippingForm shippingForm, HttpSession session){
        User user = (User) session.getAttribute(SmartConst.CURRENT_USER);

        ResponseView<Map<String, Integer>> responseView = shippingService.add(user.getId(), shippingForm);
        return responseView;
    }

    /**
     * 删除地址
     * @param shippingId  地址id
     * @param session
     * @return
     */
    @DeleteMapping("/shippings/{shippingId}")
    public ResponseView delete(@PathVariable Integer shippingId,HttpSession session){
        User user = (User) session.getAttribute(SmartConst.CURRENT_USER);

        ResponseView responseView = shippingService.delete(user.getId(), shippingId);
        return responseView;
    }

    /**
     * 更新地址
     * @param shippingId  地址id
     * @param session
     * @param shippingForm
     * @return
     */
    @PutMapping("/shippings/{shippingId}")
    public ResponseView update(@PathVariable Integer shippingId,HttpSession session,
                               @Valid @RequestBody ShippingForm shippingForm){
        User user = (User) session.getAttribute(SmartConst.CURRENT_USER);
        ResponseView responseView = shippingService.update(user.getId(), shippingId, shippingForm);
        return responseView;
    }

    /**
     * 获取用户地址列表
     * @param pageNum  默认第一页
     * @param pageSize  默认每页10条
     * @param session
     * @return
     */
    @GetMapping("/shippings")
    public ResponseView list(@RequestParam(required = false,defaultValue = "1") Integer pageNum,
                             @RequestParam(required = false,defaultValue = "10") Integer pageSize,
                             HttpSession session){
        User user = (User) session.getAttribute(SmartConst.CURRENT_USER);

        ResponseView<PageInfo> responseView = shippingService.list(user.getId(), pageNum, pageSize);
        return responseView;


    }

}
