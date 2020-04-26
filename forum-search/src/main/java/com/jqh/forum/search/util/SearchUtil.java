package com.jqh.forum.search.util;

import com.jqh.forum.search.pojo.Article;
import com.jqh.forum.search.pojo.ArticleES;

/**
 * SearchUtil
 *
 * @author 862965251@qq.com
 * @date 2020-04-13 19:24
 */
public class SearchUtil {
    public static ArticleES article2ES(Article article){
        ArticleES articleES = new ArticleES();
        articleES.setContent(article.getContent());
        articleES.setId(article.getId());
        articleES.setImage(article.getImage());
        articleES.setTitle(article.getTitle());
        articleES.setUserid(article.getUserid());
        articleES.setNickname(article.getNickname());
        articleES.setAvatar(article.getAvatar());
        return articleES;
    }
}
