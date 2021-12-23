package com.muhan.smart.pojo;

import lombok.Data;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;

/**
 * @Author: Muhan.Zhou
 * @Description 订单详情表
 * @Date 2021/12/22 9:19
 */
@Data
public class MallOrderItem {
    private Integer id;
    private Integer userId;
    private BigInteger orderNo;
    private Integer productId;
    private String productName;
    private String productImage;
    private BigDecimal currentUnitPrice;
    private Integer quantity;
    private BigDecimal totalPrice;
    private Date createTime;
    private Date updateTime;
}
