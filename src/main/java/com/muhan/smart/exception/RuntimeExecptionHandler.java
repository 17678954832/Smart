package com.muhan.smart.exception;

import com.muhan.smart.enums.ResponseEnum;
import com.muhan.smart.view.ResponseView;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

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
}
