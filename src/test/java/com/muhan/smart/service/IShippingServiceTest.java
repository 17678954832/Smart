package com.muhan.smart.service;

import com.github.pagehelper.PageInfo;
import com.muhan.smart.SmartApplicationTests;
import com.muhan.smart.enums.ResponseEnum;
import com.muhan.smart.form.ShippingForm;
import com.muhan.smart.view.ResponseView;
import lombok.extern.slf4j.Slf4j;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Map;

import static org.junit.Assert.*;
@Slf4j
public class IShippingServiceTest extends SmartApplicationTests {

    @Autowired
    private IShippingService shippingService;

    private Integer uid = 1;

    private Integer shippingId;

    private ShippingForm shippingForm;

    @Before
    public void before(){
        ShippingForm shippingForm = new ShippingForm();
        shippingForm.setReceiverName("张三");
        shippingForm.setReceiverAddress("中国北京");
        shippingForm.setReceiverPhone("17678954832");
        shippingForm.setReceiverZip("563001");
        shippingForm.setReceiverProvince("贵州");
        shippingForm.setReceiverCity("遵义");
        shippingForm.setReceiverDistrict("汇川区");
        this.shippingForm  = shippingForm;

        add();
    }

    public void add() {
        ResponseView<Map<String, Integer>> responseView = shippingService.add(uid, shippingForm);
        log.info("resp={}" + responseView);
        this.shippingId = responseView.getData().get("shippingId");
        Assert.assertEquals(ResponseEnum.SUCCESS.getCode(),responseView.getStatus());
    }

    @After
    public void delete() {
        ResponseView responseView = shippingService.delete(uid, shippingId);
        log.info("resp={}" , responseView);
        Assert.assertEquals(ResponseEnum.SUCCESS.getCode(),responseView.getStatus());
    }

    @Test
    public void update() {

        shippingForm.setReceiverMobile("110111101111");
        shippingForm.setReceiverName("李四");
        shippingForm.setReceiverCity("贵阳");
        ResponseView responseView = shippingService.update(uid, shippingId, shippingForm);
        log.info("resp={}" , responseView);
        Assert.assertEquals(ResponseEnum.SUCCESS.getCode(),responseView.getStatus());
    }

    @Test
    public void list() {
        ResponseView<PageInfo> list = shippingService.list(uid,1,2);
        log.info("list={}" , list);
        Assert.assertEquals(ResponseEnum.SUCCESS.getCode(),list.getStatus());
    }
}