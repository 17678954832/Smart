package com.muhan.smart.dao;

import com.muhan.smart.SmartApplicationTests;
import com.muhan.smart.pojo.MallCategory;
import org.junit.Test;

import javax.annotation.Resource;

/*@RunWith(SpringRunner.class)
@SpringBootTest*/
public class MallCategoryMapperTest extends SmartApplicationTests {

    @Resource
    private MallCategoryMapper mallCategoryMapper;

    @Test
    public void findById() {
        MallCategory mallCategory = mallCategoryMapper.findById(100001);
        System.out.println(mallCategory.toString());
    }

    @Test
    public void queryById() {
        MallCategory mallCategory = mallCategoryMapper.queryById(100001);
        System.out.println(mallCategory.toString());
    }
}