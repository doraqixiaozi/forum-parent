package com.jqh.forum.qa.controller;

import entity.Result;
import entity.StatusCode;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @Auther: 几米
 * @Date: 2019/5/5 21:32
 * @Description: BaseExceptionHandler
 */
@RestControllerAdvice
public class ProblemExceptionHandler {
    @ExceptionHandler(value = Exception.class)
    public Result processException(Exception e) {
        return new Result(false, StatusCode.ERROR, e.getMessage());
    }

    @ExceptionHandler(value = RuntimeException.class)
    public Result processArithmeticException(ArithmeticException e) {
        return new Result(false, StatusCode.ACCESSERROR,  e.getMessage());
    }
}
