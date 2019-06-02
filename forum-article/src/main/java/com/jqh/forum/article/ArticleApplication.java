package com.jqh.forum.article;

import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import tk.mybatis.spring.annotation.MapperScan;
import util.IdWorker;

/**
 * @Auther: 几米
 * @Date: 2019/5/11 13:39
 * @Description: 文章部分模块儿
 */
@SpringCloudApplication
@EnableFeignClients
@MapperScan("com.jqh.forum.article.mapper")
public class ArticleApplication {
    public static void main(String[] args) {
        SpringApplication.run(ArticleApplication.class);
    }

    @Bean
    public IdWorker idWorker() {
        return new IdWorker();
    }
}
