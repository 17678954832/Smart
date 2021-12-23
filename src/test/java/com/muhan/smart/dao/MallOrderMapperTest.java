package com.muhan.smart.dao;

import com.muhan.smart.SmartApplicationTests;
import com.muhan.smart.pojo.MallOrder;
import org.junit.Test;

import javax.annotation.Resource;

public class MallOrderMapperTest extends SmartApplicationTests {

    @Resource
    private MallOrderMapper mallOrderMapper;

    @Test
    public void findById() {

        MallOrder mallOrder = mallOrderMapper.findById(1);
        System.out.println(mallOrder.toString());
    }

    @Test
    public void queryById(){
        MallOrder mallOrder = mallOrderMapper.queryById(1);
        System.out.println(mallOrder.toString());
    }
}