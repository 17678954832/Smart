package com.muhan.smart.enums;

import lombok.Getter;

/**
 * 商品状态： 1-在售，2-下架，3-删除
 */
@Getter
public enum ProductStatusEnum {
    ON_SALE(1,"在售"),

    OFF_SALE(2,"下架"),

    DELETE(3,"删除"),
    ;

    Integer code;
    String msg;

    ProductStatusEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

}
