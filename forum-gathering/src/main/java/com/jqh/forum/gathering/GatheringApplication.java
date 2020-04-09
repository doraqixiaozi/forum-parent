package com.jqh.forum.gathering;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import tk.mybatis.spring.annotation.MapperScan;
import util.IdWorker;

/**
 * @Auther: 几米
 * @Date: 2019/5/11 18:58
 * @Description: 活动模块儿
 */

@EnableCaching//允许spring缓存
@SpringCloudApplication
@EnableFeignClients
@MapperScan("com.jqh.forum.gathering.mapper")
public class GatheringApplication {

    public static void main(String[] args) {
        SpringApplication.run(GatheringApplication.class, args);
    }

    @Bean
    public IdWorker idWorker(){
        return new IdWorker(1, 1);
    }

}