package com.jqh.forum.manager.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @Auther: 几米
 * @Date: 2019/6/2 21:56
 * @Description: ManagerController
 */
@CrossOrigin
@Controller
public class ManagerController {
    @GetMapping("/")
    public String index(){
        return "/index.html";
    }
}
