package com.muhan.smart;

import com.muhan.smart.dao.CategoryMapper;
import com.muhan.smart.dao.MallOrderMapper;
import com.muhan.smart.dao.MallUserMapper;
import com.muhan.smart.pojo.Category;
import com.muhan.smart.pojo.MallUser;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SmartApplicationTests {

    @Resource
    private CategoryMapper categoryMapper;

    @Resource
    private MallUserMapper mallUserMapper;

    @Resource
    private MallOrderMapper mallOrder;

    @Test
    public void contextLoads() {
        Category category = categoryMapper.findById(100001);
        System.out.println(category.toString());

        MallUser byRole = mallUserMapper.findByRole(0);
        System.out.println(byRole.toString());

        MallOrderMapper byId = mallOrder.findById(1);
        System.out.println(byId);

    }

}
