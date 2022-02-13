package com.muhan.smart.form;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @Author: Muhan.Zhou
 * @Description 添加商品统一验证
 * @Date 2022/2/9 20:46
 */
@Data
public class CartAddForm {

    @NotNull
    private Integer productId;  //商品id

    private Boolean selected = true;  //是否选中
}
