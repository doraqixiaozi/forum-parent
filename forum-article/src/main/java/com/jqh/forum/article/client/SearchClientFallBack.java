package com.jqh.forum.article.client;

import com.jqh.forum.article.pojo.Article;
import entity.Result;

/**
 * SearchClientFallBack
 *
 * @author 862965251@qq.com
 * @date 2020-04-13 19:32
 */
public class SearchClientFallBack implements SearchClient{
    @Override
    public Result add(Article article) {
        return null;
    }

    @Override
    public Result deleteByKey(String id) {
        return null;
    }

}
