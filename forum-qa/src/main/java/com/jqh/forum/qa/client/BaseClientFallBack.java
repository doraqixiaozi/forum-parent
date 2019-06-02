package com.jqh.forum.qa.client;

import entity.Result;
import org.springframework.stereotype.Component;

/**
 * @Auther: 几米
 * @Date: 2019/5/30 19:34
 * @Description: feign调用失败后的方法
 *
 */
@Component
public class BaseClientFallBack implements BaseClient{
    public Result findById(String labelId){
        return null;
    }
    public Result findAll(){
        return null;
    }
}
