package com.muhan.smart.exception;

import com.muhan.smart.enums.ResponseEnum;
import com.muhan.smart.view.ResponseView;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Objects;

/**
 * @Author: Muhan.Zhou
 * @Description 异常处理
 * @Date 2022/1/28 16:54
 */
@ControllerAdvice
public class RuntimeExecptionHandler {

    @ExceptionHandler(RuntimeException.class)
    @ResponseBody
    public ResponseView handle(RuntimeException e){
        return ResponseView.error(ResponseEnum.ERROR,e.getMessage());
    }

    /**
     * 捕获异常并进行处理
     * @return
     */
    @ExceptionHandler(UserLoginException.class)
    @ResponseBody
    public ResponseView userLoginHandle(){
        return ResponseView.error(ResponseEnum.NEED_LOGIN);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    public ResponseView methodArgumentNotValidException(MethodArgumentNotValidException e){

        BindingResult bindingResult = e.getBindingResult();
        Objects.requireNonNull(bindingResult.getFieldError());
        return ResponseView.error(ResponseEnum.PARAM_ERROR,
                bindingResult.getFieldError().getField() + "  " + bindingResult.getFieldError().getDefaultMessage());

    }
}
