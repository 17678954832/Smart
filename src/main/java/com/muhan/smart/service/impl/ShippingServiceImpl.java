package com.muhan.smart.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.muhan.smart.dao.ShippingMapper;
import com.muhan.smart.enums.ResponseEnum;
import com.muhan.smart.form.ShippingForm;
import com.muhan.smart.pojo.Shipping;
import com.muhan.smart.service.IShippingService;
import com.muhan.smart.view.ResponseView;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: Muhan.Zhou
 * @Description
 * @Date 2022/2/12 18:20
 */
@Service
public class ShippingServiceImpl implements IShippingService {

    @Autowired
    private ShippingMapper shippingMapper;


    /**
     * 新增地址
     * @param uid          用户id
     * @param shippingForm 入参对象
     * @return  地址id
     */
    @Override
    public ResponseView<Map<String, Integer>> add(Integer uid, ShippingForm shippingForm) {
        Shipping shipping = new Shipping();
        BeanUtils.copyProperties(shippingForm,shipping);
        shipping.setUserId(uid);
        int row = shippingMapper.insertSelective(shipping);
        if (row == 0){
            return ResponseView.error(ResponseEnum.ERROR);
        }

        //字段少，可以直接用map，没必要新建vo对象
        Map<String, Integer> map = new HashMap<>();
        map.put("shippingId",shipping.getId());

        return ResponseView.success(map);
    }

    /**
     * 删除地址
     * @param uid        用户id
     * @param shippingId 地址id
     * @return
     */
    @Override
    public ResponseView delete(Integer uid, Integer shippingId) {
        int row = shippingMapper.deleteByIdAndUid(uid, shippingId);

        if (row == 0){
            return ResponseView.error(ResponseEnum.DELETE_SHIPPING_FAIL);
        }
        return ResponseView.success();
    }

    /**
     * 修改地址
     * @param uid         用户id
     * @param shippingId  地址id
     * @param shippingForm 入参对象
     * @return
     */
    @Override
    public ResponseView update(Integer uid, Integer shippingId, ShippingForm shippingForm) {

        Shipping shipping = new Shipping();
        BeanUtils.copyProperties(shippingForm,shipping);
        shipping.setUserId(uid);
        shipping.setId(shippingId);

        //判断
        int row = shippingMapper.updateByPrimaryKeySelective(shipping);
        if (row == 0){
            return ResponseView.error(ResponseEnum.ERROR);
        }
        return ResponseView.success();
    }

    /**
     * 地址列表
     * @param uid      用户id
     * @param pageNum  页码
     * @param pageSize 页面条数
     * @return
     */
    @Override
    public ResponseView<PageInfo> list(Integer uid, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        List<Shipping> shippings = shippingMapper.selectByUid(uid);
        
        PageInfo pageInfo = new PageInfo(shippings);
        return ResponseView.success(pageInfo);
    }
}
