package com.jqh.forum.email;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
/**
 * @Auther: 几米
 * @Date: 2019/5/26 14:55
 * @Description: 监听邮件队列消息并向用户发送邮件
 */
@SpringBootApplication
public class EmailApplication {
    public static void main(String[] args) {
        SpringApplication.run(EmailApplication.class);
    }

}
