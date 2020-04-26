package com.jqh.forum.user.client;

import entity.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;

import java.util.HashMap;
import java.util.List;

/**
 * ArticleClient
 *
 * @author 862965251@qq.com
 * @date 2020-03-02 16:29
 */
@FeignClient(value = "forum-article",fallback = ArticleClientFallBack.class)
public interface ArticleClient {
    /**
     * 批量更新文章中的用户信息
     */
    @PutMapping(value="/article/user")
    Result updateUserData( @PathVariable HashMap userData);


}
