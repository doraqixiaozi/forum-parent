package com.jqh.forum.gathering.controller;

import entity.Result;
import entity.StatusCode;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * @Auther: 几米
 * @Date: 2019/5/12 17:51
 * @Description: LogController
 */
@CrossOrigin
@RestController
@RequestMapping("/user")
public class LogController {
    @PostMapping("/login")
    public Result login() {
        HashMap<String, String> objectObjectHashMap = new HashMap<>();
        objectObjectHashMap.put("token","admin");
        return new  Result(true, StatusCode.OK,"登录成功",objectObjectHashMap);
    }
    @PostMapping("/logout")
    public Result logout() {
        HashMap<String, String> objectObjectHashMap = new HashMap<>();
        objectObjectHashMap.put("token","admin");
        return new  Result(true, StatusCode.OK,"退出成功",objectObjectHashMap);
    }
    @GetMapping("/info")
    public Result getInfo() {
        HashMap<String, Object> objectObjectHashMap = new HashMap<>();
        objectObjectHashMap.put("avatar","https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif");
        objectObjectHashMap.put("name","admin");
        ArrayList<Object> objects = new ArrayList<>();
        objects.add("admin");
        objectObjectHashMap.put("roles",objects);
        return new  Result(true, StatusCode.OK,"验证成功成功",objectObjectHashMap);
    }
}
