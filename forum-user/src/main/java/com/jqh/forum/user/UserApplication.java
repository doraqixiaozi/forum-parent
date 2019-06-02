package com.jqh.forum.user;

import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import tk.mybatis.spring.annotation.MapperScan;
import util.IdWorker;
import util.JwtUtil;

/**
 * @Auther: 几米
 * @Date: 2019/5/25 16:55
 * @Description: UserApplication
 */
@SpringCloudApplication
@EnableFeignClients
@MapperScan("com.jqh.forum.user.mapper")
public class UserApplication {
    public static void main(String[] args) {
        SpringApplication.run(UserApplication.class);
    }

    @Bean
    public IdWorker idWorker() {
        return new IdWorker(1, 1);
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
    @Bean
    public JwtUtil jwtUtil(){return new JwtUtil();}

}
