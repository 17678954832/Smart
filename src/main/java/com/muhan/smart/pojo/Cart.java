package com.muhan.smart.pojo;

import lombok.Data;

/**
 * @Author: Muhan.Zhou
 * @Description 存到redis的对象
 * @Date 2022/2/9 22:04
 */
@Data
public class Cart {

    private Integer productId;

    private Integer quantity;  //购买的数量

    private Boolean productSelected;  //是否选中


    public Cart() {
    }

    public Cart(Integer productId, Integer quantity, boolean productSelected) {
        this.productId = productId;
        this.quantity = quantity;
        this.productSelected = productSelected;
    }
}
