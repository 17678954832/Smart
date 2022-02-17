package com.muhan.smart.controller;

import com.muhan.smart.service.ICategoryService;
import com.muhan.smart.vo.CategoryVo;
import com.muhan.smart.vo.ResponseVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Author: Muhan.Zhou
 * @Description 商品类目表controller
 * @Date 2022/1/31 10:53
 */
@RestController
public class CategoryController {

    @Autowired
    private ICategoryService iCategoryService;

    @GetMapping("/categories")
    public ResponseVo<List<CategoryVo>> selectAll(){

        return iCategoryService.selectAll();
    }
}
