package com.jqh.forum.base.controller;

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
public class BaseExceptionHandler {
    @ExceptionHandler(value = Exception.class)
    public Result processException(Exception e) {
        return new Result(false, StatusCode.ERROR, e.getMessage());
    }
}
