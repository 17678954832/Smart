package com.muhan.smart.pojo;

import lombok.Data;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;

/**
 * @author: Muhan.Zhou
 * @description 订单表
 * @date 2021/12/21 18:27
 */
@Data
public class MallOrder {
    private Integer id;
    private BigInteger orderNo;
    private Integer userId;
    private Integer shippingId;
    private BigDecimal payment;
    private Integer payType;
    private Integer postAge;
    private Integer status;
    private Date payTime;
    private Date sendTime;
    private Date endTime;
    private Date closeTime;
    private Date createTime;
    private Date updateTime;


}
