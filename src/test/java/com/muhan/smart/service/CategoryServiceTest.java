package com.muhan.smart.service;

import com.muhan.smart.SmartApplicationTests;
import com.muhan.smart.enums.ResponseEnum;
import com.muhan.smart.view.CategoryView;
import com.muhan.smart.view.ResponseView;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class CategoryServiceTest extends SmartApplicationTests {

    @Autowired
    private ICategoryService categoryService;

    @Test
    public void selectAll() {
        ResponseView<List<CategoryView>> listResponseView = categoryService.selectAll();

        //判断登录状态是否为0  前面是我们期待的，后面是实际的
        Assert.assertEquals(ResponseEnum.SUCCESS.getCode(),listResponseView.getStatus());
    }
}