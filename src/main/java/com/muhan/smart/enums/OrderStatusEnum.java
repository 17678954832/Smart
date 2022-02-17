package com.muhan.smart.enums;

import lombok.Getter;

/**
 * 订单状态枚举
 */
@Getter
public enum OrderStatusEnum {
    CANCELED(0,"已取消"),

    NO_PAY(10,"未付款"),

    PAID(20,"已付款"),

    SHIPPED(40,"已发货"),

    TARDE_SUCCESS(50,"交易成功"),

    TRADE_CLOSED(60,"交易关闭"),
    ;
    Integer code;
    String desc;

    OrderStatusEnum(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }
}
