package com.jqh.forum.recruit.client;

import entity.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @Auther: 几米
 * @Date: 2019/5/30 19:28
 * @Description: BaseClient
 */
@FeignClient(value = "forum-base",fallback = BaseClientFallBack.class)
public interface BaseClient {
    @GetMapping("/label/{labelId}")
    public Result findById(@PathVariable("labelId") String labelId);
    @GetMapping("/label")
    public Result findAll();
    @GetMapping("/label/labelMap")
    public Result getLabelMap();
}
