package com.jqh.forum.search.service;

import com.jqh.forum.search.dao.ArticleDao;
import com.jqh.forum.search.pojo.ArticleES;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 * @Auther: 几米
 * @Date: 2019/5/22 18:43
 * @Description: ArticleService
 */
@Service
@Slf4j
public class ArticleService {
    @Autowired
    private ArticleDao articleDao;

    public void add(ArticleES article){
//        log.trace("add{}",article);
        articleDao.save(article);
    }

    public Page<ArticleES> searchByKey(String key, int page, int size) {
        Pageable pageable= PageRequest.of(page-1,size);
       return articleDao.findByTitleLikeOrContentLike(key,key,pageable);
//        return null;
    }

    public void deleteById(String id) {
//        log.trace("delete{}",id);
        articleDao.deleteById(id);
    }

}
