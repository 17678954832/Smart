package com.muhan.smart.vo;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @Author: Muhan.Zhou
 * @Description
 * @Date 2022/2/9 20:34
 */
@Data
public class CartProductVo {

    private Integer productId;

    private Integer quantity;  //购买的数量

    private String productName;

    private String productSubtitle;  //子标题

    private String productMainImage;  //主图

    private BigDecimal productPrice;  //价格

    private Integer productStatus;  //商品状态

    private BigDecimal productTotalPrice; //商品总价=单价*数量

    private Integer productStock;  //库存

    private Boolean productSelected;  //是否选中该商品

    public CartProductVo(Integer productId, Integer quantity, String productName, String productSubtitle, String productMainImage, BigDecimal productPrice, Integer productStatus, BigDecimal productTotalPrice, Integer productStock, Boolean productSelected) {
        this.productId = productId;
        this.quantity = quantity;
        this.productName = productName;
        this.productSubtitle = productSubtitle;
        this.productMainImage = productMainImage;
        this.productPrice = productPrice;
        this.productStatus = productStatus;
        this.productTotalPrice = productTotalPrice;
        this.productStock = productStock;
        this.productSelected = productSelected;
    }
}
