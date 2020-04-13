package com.jqh.forum.search.client;

import com.jqh.forum.search.pojo.Article;
import entity.Result;
import entity.StatusCode;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;

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
     * 获取未同步到es中的文章
     * @param page
     * @param size
     * @return
     */
    @GetMapping("/article/move/{page}/{size}")
    Result<List<Article>> getUnMoveArticle(@PathVariable int page, @PathVariable int size);

    /**
     * 批量设置文章已被同步
     */
    @PutMapping("/article/move")
    Result hasMove(List<Article> articles);

    /**
     * 获取未同步到es中的文章数量
     * @return
     */
    @GetMapping("/article/move/num")
    Result<Integer> getUnMoveArticleNum();
}
