package com.jqh.forum.user.controller;
import entity.Result;
import entity.StatusCode;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
/**
 * 统一异常处理类
 */
@ControllerAdvice
public class BaseExceptionHandler {
	
    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public Result error(Exception e){
        e.printStackTrace();        
        return new Result(false, StatusCode.ERROR, e.getMessage());
    }
//处理重复注册问题
    @ExceptionHandler(value = DuplicateKeyException.class)
    @ResponseBody
    public Result duplicateKeyExceptionError(DuplicateKeyException e){
        e.printStackTrace();
        return new Result(false, StatusCode.ERROR, "账号已被注册");
    }
}
