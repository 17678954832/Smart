package com.muhan.smart.form;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @Author: Muhan.Zhou
 * @Description 订单入参校验
 * @Date 2022/2/16 11:44
 */
@Data
public class OrderCreateForm {

    @NotNull
    private Integer shippingId;


}
