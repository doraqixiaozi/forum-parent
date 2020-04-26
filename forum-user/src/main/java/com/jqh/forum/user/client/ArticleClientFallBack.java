package com.jqh.forum.user.client;

import entity.Result;
import org.springframework.stereotype.Component;

import java.util.HashMap;
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
     * 批量文章中的用户信息
     *
     * @param userData
     */
    @Override
    public Result updateUserData(HashMap userData) {
        return null;
    }
}
