package com.muhan.smart.dao;

import com.muhan.smart.SmartApplicationTests;
import com.muhan.smart.pojo.MallUser;
import org.junit.Test;

import javax.annotation.Resource;

public class MallUserMapperTest extends SmartApplicationTests {

    @Resource
    private MallUserMapper mallUserMapper;

    @Test
    public void findByRole() {
        MallUser mallUser = mallUserMapper.findByRole(0);
        System.out.println(mallUser.toString());
    }

    @Test
    public void queryById(){
        MallUser mallUser = mallUserMapper.queryById(1);
        System.out.println(mallUser.toString());
    }
}