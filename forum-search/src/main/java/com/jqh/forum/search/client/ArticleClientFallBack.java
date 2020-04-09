package com.jqh.forum.search.client;

import com.jqh.forum.search.pojo.Article;
import entity.Result;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * ArticleClientFallBack
 *
 * @author 862965251@qq.com
 * @date 2020-03-02 16:30
 */
@Component
public class ArticleClientFallBack implements ArticleClient{
    /**
     * 获取未同步到es中的文章
     *
     * @param page
     * @param size
     * @return
     */
    @Override
    public Result getUnMoveArticle(int page, int size) {
        return null;
    }

    /**
     * 批量设置文章已被同步
     *
     * @param articles
     */
    @Override
    public Result hasMove(List<Article> articles) {
        return null;
    }


    /**
     * 获取未同步到es中的文章数量
     *
     * @return
     */
    @Override
    public Result getUnMoveArticleNum() {
        return null;
    }
}
