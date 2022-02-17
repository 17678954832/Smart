package com.muhan.smart.vo;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @Author: Muhan.Zhou
 * @Description 商品返回数据格式
 * @Date 2022/2/4 19:47
 */
@Data
public class ProductVo {
    private Integer id;

    private Integer categoryId;

    private String name;

    private String subtitle;

    private String mainImage;

    private BigDecimal price;

    private Integer status;
}
