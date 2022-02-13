package com.muhan.smart.form;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @Author: Muhan.Zhou
 * @Description 收货地址
 * @Date 2022/2/12 18:09
 */
@Data
public class ShippingForm {
    @NotBlank
    private String receiverName;

    @NotBlank
    private String receiverPhone;

    @NotBlank
    private String receiverMobile;

    @NotBlank
    private String receiverProvince;

    @NotBlank
    private String receiverCity;

    @NotBlank
    private String receiverDistrict;

    @NotBlank
    private String receiverAddress;

    @NotBlank
    private String receiverZip;
}
