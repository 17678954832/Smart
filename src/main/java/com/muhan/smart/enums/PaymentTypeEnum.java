package com.muhan.smart.enums;

import lombok.Getter;

/**
 * 支付方式枚举  1-在线支付
 */
@Getter
public enum PaymentTypeEnum {

    PAY_ONLINE(1),
    ;
    Integer code;

    PaymentTypeEnum(Integer code) {
        this.code = code;
    }
}
