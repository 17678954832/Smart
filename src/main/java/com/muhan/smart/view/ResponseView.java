package com.muhan.smart.view;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.muhan.smart.enums.ResponseEnum;
import lombok.Data;
import org.springframework.validation.BindingResult;

/**
 * @Author: Muhan.Zhou
 * @Description 返回给前端的对象
 * @Date 2022/1/28 15:22
 */
@Data
@JsonInclude(value = JsonInclude.Include.NON_NULL)
public class ResponseView<T> {
    private Integer status;  //状态

    private String msg;    //信息

    private T data;  //泛型

    //构造方法
    private ResponseView(Integer status, String msg) {
        this.status = status;
        this.msg = msg;
    }

    private ResponseView(Integer status, T data) {
        this.status = status;
        this.data = data;
    }

    public static <T> ResponseView<T> successByMsg(String msg){
      return new ResponseView<>(ResponseEnum.SUCCESS.getCode(),msg);
    }

    public static <T> ResponseView<T> success(T data){
        return new ResponseView<>(ResponseEnum.SUCCESS.getCode(),data);
    }

    public static <T> ResponseView<T> success(){
        return new ResponseView<>(ResponseEnum.SUCCESS.getCode(),ResponseEnum.SUCCESS.getDesc());
    }

    public static <T> ResponseView<T>  error(ResponseEnum responseEnum){
        return new ResponseView<>(responseEnum.getCode(),responseEnum.getDesc());
    }

    public static <T> ResponseView<T>  error(ResponseEnum responseEnum, String msg){
        return new ResponseView<>(responseEnum.getCode(),msg);
    }

    public static <T> ResponseView<T>  error(ResponseEnum responseEnum, BindingResult bindingResult){
        return new ResponseView<>(responseEnum.getCode(),
                bindingResult.getFieldError().getField() + "  " + bindingResult.getFieldError().getDefaultMessage());
    }
}
