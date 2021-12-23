package com.muhan.smart.dao;

import com.muhan.smart.SmartApplicationTests;
import com.muhan.smart.pojo.MallOrderItem;
import org.junit.Test;

import javax.annotation.Resource;

public class MallOrderItemMapperTest extends SmartApplicationTests {

    @Resource
    private MallOrderItemMapper mallOrderItemMapper;

    @Test
    public void findById() {
        MallOrderItem mallOrderItem = mallOrderItemMapper.findById(1);
        System.out.println(mallOrderItem.toString());
    }

    @Test
    public void queryById(){
        MallOrderItem mallOrderItem = mallOrderItemMapper.queryById(1);
        System.out.println(mallOrderItem.toString());
    }
}