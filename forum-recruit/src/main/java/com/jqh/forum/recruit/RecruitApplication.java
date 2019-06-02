package com.jqh.forum.recruit;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import tk.mybatis.spring.annotation.MapperScan;
import util.IdWorker;

/**
 * @Auther: 几米
 * @Date: 2019/5/5 18:13
 * @Description: BaseApplication
 */
@SpringCloudApplication
@EnableFeignClients
@MapperScan("com.jqh.forum.recruit.mapper")
public class RecruitApplication {
    public static void main(String[] args) {
        SpringApplication.run(RecruitApplication.class);
    }
    @Bean
    public IdWorker idWorker(){
        return new IdWorker();
    }
}
