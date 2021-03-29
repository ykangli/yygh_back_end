package com.atguigu.yygh.common.exception;

import com.atguigu.yygh.common.result.Result;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 全局异常处理类
 * 注解 @ResponseBody，结果以json方式输出
 * @author Li
 */
@ControllerAdvice
@ResponseBody
public class GlobalExceptionHandler {

    /**
     * 注解 @ExceptionHandler表示，设置以什么异常执行
     * 注解@ExceptionHandler(Exception.class)表示，如果程序执行出现异常，该方法就会执行
     *
     * 全局异常处理
     *
     * @param e
     * @return
     */
    @ExceptionHandler(Exception.class)
    public Result error(Exception e){
        e.printStackTrace();
        return Result.fail();
    }

    /**
     * 有时候系统自带的异常不能满足，需要自定义异常
     * 自定义异常处理
     * @param e
     * @return
     */
    @ExceptionHandler(YyghException.class)
    @ResponseBody
    public Result error(YyghException e){
        return Result.build(e.getCode(), e.getMessage());
    }


}
