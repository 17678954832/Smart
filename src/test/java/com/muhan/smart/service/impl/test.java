package com.muhan.smart.service.impl;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.muhan.smart.SmartApplicationTests;
import com.muhan.smart.service.ICategoryService;
import com.muhan.smart.vo.CategoryVo;
import com.muhan.smart.vo.ResponseVo;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @Author: Muhan.Zhou
 * @Description
 * @Date 2022/2/19 19:49
 */
@Slf4j
public class test extends SmartApplicationTests {
    @Autowired
    private ICategoryService categoryService;

    private Gson gson = new GsonBuilder().setPrettyPrinting().create();


    @Test
    public void test(){
        ResponseVo<List<CategoryVo>> listResponseVo = categoryService.selectAll();
        log.info("category = {}" , gson.toJson(listResponseVo));
    }
}
