package com.jqh.forum.sms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.CrossOrigin;

/**
 * @Auther: 几米
 * @Date: 2019/5/25 23:08
 * @Description: 负责监听用户注册服务发来的验证码信息并且将验证码发送到用户手机
 */
@SpringBootApplication
public class SMSApplication {
    public static void main(String[] args) {
        SpringApplication.run(SMSApplication.class);
    }
}
