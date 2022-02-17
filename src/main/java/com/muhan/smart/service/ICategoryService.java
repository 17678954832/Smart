package com.muhan.smart.service;

import com.muhan.smart.vo.CategoryVo;
import com.muhan.smart.vo.ResponseVo;

import java.util.List;
import java.util.Set;

/**
 * @Author: Muhan.Zhou
 * @Description 商品类目接口
 * @Date 2022/1/31 10:22
 */
public interface ICategoryService {

    /**
     * 查询所有分类
     * @return
     */
    ResponseVo<List<CategoryVo>> selectAll();


    /**
     * 查找所有子类目的id
     * @param id   子类的id
     * @param resultSet   结果集
     */
    void findSubCategoryId(Integer id, Set<Integer> resultSet);

}
