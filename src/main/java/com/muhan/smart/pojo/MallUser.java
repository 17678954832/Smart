package com.muhan.smart.pojo;

import lombok.Data;

import java.util.Date;

/**
 * @author: Muhan.Zhou
 * @description 用户表
 * @date 2021/12/21 11:39
 */
@Data
public class MallUser {
    private Integer Id;
    private String userName;
    private String passWord;
    private String email;
    private String phone;
    private String question;
    private String answer;
    private Integer role;
    private Date createTime;
    private Date updateTime;


}
