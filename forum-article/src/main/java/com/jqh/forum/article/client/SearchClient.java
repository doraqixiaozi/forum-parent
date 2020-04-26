package com.jqh.forum.article.client;

import com.jqh.forum.article.pojo.Article;
import entity.Result;
import entity.StatusCode;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * SearchClient
 *
 * @author 862965251@qq.com
 * @date 2020-04-13 19:30
 */
@FeignClient(value = "forum-search",fallback = SearchClientFallBack.class)
public interface SearchClient {
    @PostMapping("/articleES")
    public Result add(@RequestBody Article article) ;
    @DeleteMapping("/articleES/{id}")
    public Result deleteByKey(@PathVariable String id);
}
