package com.muhan.smart.form;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @Author: Muhan.Zhou
 * @Description 接收到的form表单数据
 * @Date 2022/1/28 15:58
 */
@Data
public class UserLoginForm {

    //@NotBlank  不能是空白，用于String，会判断空格
    //@NotNull
    //@NotEmpty  用于集合，list等
    @NotBlank
    private String username;

    @NotBlank
    private String password;

}
