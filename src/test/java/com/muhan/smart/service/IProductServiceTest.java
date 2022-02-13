package com.muhan.smart.service;

import com.github.pagehelper.PageInfo;
import com.muhan.smart.SmartApplicationTests;
import com.muhan.smart.enums.ResponseEnum;
import com.muhan.smart.view.ProductDetailView;
import com.muhan.smart.view.ResponseView;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 商品查询测试类
 */
public class IProductServiceTest extends SmartApplicationTests {

    @Autowired
    private IProductService productService;

    @Test
    public void list() {
        ResponseView<PageInfo> responseView = productService.list(null, 1, 1);
        Assert.assertEquals(ResponseEnum.SUCCESS.getCode(),responseView.getStatus());
    }

    @Test
    public void detail(){
        ResponseView<ProductDetailView> detail = productService.detail(26);
        Assert.assertEquals(ResponseEnum.SUCCESS.getCode(),detail.getStatus());
    }
}