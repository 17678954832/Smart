package com.muhan.smart.controller;

import com.github.pagehelper.PageInfo;
import com.muhan.smart.service.IProductService;
import com.muhan.smart.view.ProductDetailView;
import com.muhan.smart.view.ResponseView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: Muhan.Zhou
 * @Description 商品控制层
 * @Date 2022/2/8 14:16
 */
@RestController
public class ProductController {

    @Autowired
    private IProductService productService;

    /**
     * 查询商品列表
     * 使用的get，参数是带在连接后面的，需要使用@RequestParam
     * (required = false) 允许为空
     * @param categoryId
     * @param pageNum
     * @param pageSize
     * @return
     */
    @GetMapping("/products")
    public ResponseView<PageInfo> list(@RequestParam(required = false) Integer categoryId,
                                       @RequestParam(required = false , defaultValue = "1") Integer pageNum,
                                       @RequestParam(required = false , defaultValue = "10")  Integer pageSize){

        return productService.list(categoryId, pageNum, pageSize);
    }

    /**
     * 查询商品详细信息
     * 在url里面需要使用@PathVariable
     * @param productId
     * @return
     */
    @GetMapping("/products/{productId}")
    public ResponseView<ProductDetailView> detail(@PathVariable Integer productId){
        return productService.detail(productId);
    }
}
