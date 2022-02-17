package com.muhan.smart.controller;

import com.github.pagehelper.PageInfo;
import com.muhan.smart.consts.SmartConst;
import com.muhan.smart.form.ShippingForm;
import com.muhan.smart.pojo.User;
import com.muhan.smart.service.IShippingService;
import com.muhan.smart.vo.ResponseVo;
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
    public ResponseVo add(@Valid @RequestBody ShippingForm shippingForm, HttpSession session){
        User user = (User) session.getAttribute(SmartConst.CURRENT_USER);

        ResponseVo<Map<String, Integer>> responseVo = shippingService.add(user.getId(), shippingForm);
        return responseVo;
    }

    /**
     * 删除地址
     * @param shippingId  地址id
     * @param session
     * @return
     */
    @DeleteMapping("/shippings/{shippingId}")
    public ResponseVo delete(@PathVariable Integer shippingId, HttpSession session){
        User user = (User) session.getAttribute(SmartConst.CURRENT_USER);

        ResponseVo responseVo = shippingService.delete(user.getId(), shippingId);
        return responseVo;
    }

    /**
     * 更新地址
     * @param shippingId  地址id
     * @param session
     * @param shippingForm
     * @return
     */
    @PutMapping("/shippings/{shippingId}")
    public ResponseVo update(@PathVariable Integer shippingId, HttpSession session,
                             @Valid @RequestBody ShippingForm shippingForm){
        User user = (User) session.getAttribute(SmartConst.CURRENT_USER);
        ResponseVo responseVo = shippingService.update(user.getId(), shippingId, shippingForm);
        return responseVo;
    }

    /**
     * 获取用户地址列表
     * @param pageNum  默认第一页
     * @param pageSize  默认每页10条
     * @param session
     * @return
     */
    @GetMapping("/shippings")
    public ResponseVo list(@RequestParam(required = false,defaultValue = "1") Integer pageNum,
                           @RequestParam(required = false,defaultValue = "10") Integer pageSize,
                           HttpSession session){
        User user = (User) session.getAttribute(SmartConst.CURRENT_USER);

        ResponseVo<PageInfo> responseVo = shippingService.list(user.getId(), pageNum, pageSize);
        return responseVo;


    }

}
