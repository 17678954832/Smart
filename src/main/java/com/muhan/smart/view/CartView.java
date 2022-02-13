package com.muhan.smart.view;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * @Author: Muhan.Zhou
 * @Description 购物车返回对象
 * @Date 2022/2/9 20:29
 */
@Data
public class CartView {
    private List<CartProductView> cartProductViewList;

    private Boolean selectAll;  //是否全选

    private BigDecimal cartTotalPrice;  //购物车中所有商品的总价

    private Integer cartTotalQuantity;  //购物车中所有商品的总数量


}
