package com.muhan.smart.pojo;

import lombok.Data;

import java.util.Date;

/**
 * 产品分类表
 * @author: Muhan.Zhou
 * @description
 * @date 2021/12/20 22:15
 */
@Data
public class MallCategory {
    private Integer id;
    private Integer parentId;
    private String name;
    private Integer status;
    private Integer sortOrder;
    private Date createTime;
    private Date updateTime;

}
