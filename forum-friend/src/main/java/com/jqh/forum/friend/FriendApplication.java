package com.jqh.forum.friend;

import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import tk.mybatis.spring.annotation.MapperScan;
import util.JwtUtil;

/**
 * @Auther: 几米
 * @Date: 2019/5/30 22:53
 * @Description: FriendApplication
 */
@EnableFeignClients
@SpringCloudApplication
@MapperScan("com.jqh.forum.friend.mapper")
public class FriendApplication {
    public static void main(String[] args) {
        SpringApplication.run(FriendApplication.class);
    }
    @Bean
    public JwtUtil jwtUtil(){return new JwtUtil();}
}
