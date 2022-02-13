package com.muhan.smart.form;

import lombok.Data;

/**
 * @Author: Muhan.Zhou
 * @Description 购物车更新
 * @Date 2022/2/12 16:19
 */
@Data
public class CartUpdataForm {

    private Integer quantity;

    private Boolean selected;
}
