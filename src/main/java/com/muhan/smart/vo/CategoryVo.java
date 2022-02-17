package com.muhan.smart.vo;

import lombok.Data;

import java.util.List;

/**
 * @Author: Muhan.Zhou
 * @Description 商品类目返回数据格式
 * @Date 2022/1/31 10:18
 */
@Data
public class CategoryVo {

    private Integer id;

    private Integer parentId;

    private String name;

    private Integer sortOrder;    //排序

    private List<CategoryVo> subCategories;    //子目录
}
