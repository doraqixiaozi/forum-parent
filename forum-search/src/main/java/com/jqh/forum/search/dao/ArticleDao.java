package com.jqh.forum.search.dao;

import com.jqh.forum.search.pojo.ArticleES;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * @Auther: 几米
 * @Date: 2019/5/22 18:39
 * @Description: ArticleDao
 */
public interface ArticleDao extends ElasticsearchRepository<ArticleES,String> {
    Page<ArticleES> findByTitleLikeOrContentLike(String title, String content, Pageable pageable);
}
