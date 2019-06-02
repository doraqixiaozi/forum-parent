package com.jqh.forum.base;

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
@MapperScan("com.jqh.forum.base.mapper")
public class BaseApplication {
    public static void main(String[] args) {
        SpringApplication.run(BaseApplication.class);
    }

    @Bean
    public IdWorker idWorker() {
        return new IdWorker();
    }
}
