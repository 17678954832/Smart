package com.muhan.smart.view;

import lombok.Data;

import java.util.List;

/**
 * @Author: Muhan.Zhou
 * @Description 商品类目返回数据格式
 * @Date 2022/1/31 10:18
 */
@Data
public class CategoryView {

    private Integer id;

    private Integer parentId;

    private String name;

    private Integer sortOrder;    //排序

    private List<CategoryView> subCategories;    //子目录
}
