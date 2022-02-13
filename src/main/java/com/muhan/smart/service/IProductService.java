package com.muhan.smart.service;

import com.github.pagehelper.PageInfo;
import com.muhan.smart.view.ProductDetailView;
import com.muhan.smart.view.ResponseView;

/**
 * @Author: Muhan.Zhou
 * @Description 商品接口
 * @Date 2022/2/7 21:21
 */
public interface IProductService {
    /**
     * 查询商品列表
     * @param categoryId  商品类目id
     * @param pageNum   分页序号
     * @param pageSize  分页大小
     * @return
     */
    ResponseView<PageInfo> list(Integer categoryId, Integer pageNum, Integer pageSize);


    /**
     * 查询商品详细信息
     * @param productId
     * @return
     */
    ResponseView<ProductDetailView> detail(Integer productId);
}
