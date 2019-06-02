package com.jqh.forum.article.service;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.jqh.forum.article.mapper.ArticleMapper;
import entity.PageResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;
import util.IdWorker;

import com.jqh.forum.article.pojo.Article;

/**
 * 服务层
 *
 * @author Administrator
 */
@Service
@Transactional
@Slf4j
public class ArticleService {

    @Resource
    private ArticleMapper articleMapper;

    @Autowired
    private IdWorker idWorker;
    @Autowired
    RedisTemplate redisTemplate;

    /**
     * 查询全部列表
     *
     * @return
     */
    public List<Article> findAll() {
        return articleMapper.selectAll();
    }


    /**
     * 条件查询+分页
     *
     * @param whereMap
     * @param page
     * @param size
     * @return
     */
    public PageResult<Article> findSearch(Map whereMap, int page, int size) {
        Page<Article> problems = PageHelper.startPage(page, size);
        List<Article> search = this.findSearch(whereMap);
        PageResult<Article> problemPageResult = new PageResult<>(problems.getTotal(), search);
        return problemPageResult;
    }


    /**
     * 条件查询
     *
     * @param whereMap
     * @return
     */
    public List<Article> findSearch(Map whereMap) {
        Example example = new Example(Article.class);
        // ID
        if (whereMap.get("id") != null && !"".equals(whereMap.get("id"))) {
            example.createCriteria().andLike("id", "%" + (String) whereMap.get("id") + "%");
        }
        // 专栏ID
        if (whereMap.get("columnid") != null && !"".equals(whereMap.get("columnid"))) {
            example.createCriteria().andLike("columnid", "%" + (String) whereMap.get("columnid") + "%");
        }
        // 用户ID
        if (whereMap.get("userid") != null && !"".equals(whereMap.get("userid"))) {
            example.createCriteria().andLike("userid", "%" + (String) whereMap.get("userid") + "%");
        }
        // 标题
        if (whereMap.get("title") != null && !"".equals(whereMap.get("title"))) {
            example.createCriteria().andLike("title", "%" + (String) whereMap.get("title") + "%");
        }
        // 文章正文
        if (whereMap.get("content") != null && !"".equals(whereMap.get("content"))) {
            example.createCriteria().andLike("content", "%" + (String) whereMap.get("content") + "%");
        }
        // 文章封面
        if (whereMap.get("image") != null && !"".equals(whereMap.get("image"))) {
            example.createCriteria().andLike("image", "%" + (String) whereMap.get("image") + "%");
        }
        // 是否公开
        if (whereMap.get("ispublic") != null && !"".equals(whereMap.get("ispublic"))) {
            example.createCriteria().andLike("ispublic", "%" + (String) whereMap.get("ispublic") + "%");
        }
        // 是否置顶
        if (whereMap.get("istop") != null && !"".equals(whereMap.get("istop"))) {
            example.createCriteria().andLike("istop", "%" + (String) whereMap.get("istop") + "%");
        }
        // 审核状态（这个原来叫condition）
        if (whereMap.get("state") != null && !"".equals(whereMap.get("state"))) {
            example.createCriteria().andLike("state", "%" + (String) whereMap.get("state") + "%");
        }
        // 所属频道
        if (whereMap.get("channelid") != null && !"".equals(whereMap.get("channelid"))) {
            example.createCriteria().andLike("channelid", "%" + (String) whereMap.get("channelid") + "%");
        }
        // URL
        if (whereMap.get("url") != null && !"".equals(whereMap.get("url"))) {
            example.createCriteria().andLike("url", "%" + (String) whereMap.get("url") + "%");
        }
        // 类型
        if (whereMap.get("type") != null && !"".equals(whereMap.get("type"))) {
            example.createCriteria().andLike("type", "%" + (String) whereMap.get("type") + "%");
        }
        return articleMapper.selectByExample(example);
    }

    /**
     * 根据ID查询实体
     *
     * @param id
     * @return
     */
    public Article findById(String id) {
        Article article=(Article)redisTemplate.opsForValue().get("article_" + id);
        if (article==null){
            article=articleMapper.selectByPrimaryKey(id);
            log.trace("article not in it");
            redisTemplate.opsForValue().set("article_"+id,article);
        }
        return article;
    }

    /**
     * 增加
     *
     * @param article
     */
    public void add(Article article) {
        article.setId(idWorker.nextId() + "");
        articleMapper.insert(article);
    }

    /**
     * 修改
     *
     * @param article
     */
    public void update(Article article) {
        articleMapper.updateByPrimaryKey(article);
        redisTemplate.delete("article_"+article.getId());
    }

    /**
     * 删除
     *
     * @param id
     */
    public void deleteById(String id) {
        articleMapper.deleteByPrimaryKey(id);
        redisTemplate.delete("article_"+id);
    }

    //审核通过
    public void updateState(String id) {
        articleMapper.updateState(id);
    }

    //点赞数加1
    public void addThumbup(String id) {
        articleMapper.addThumbup(id);
    }

    public PageResult<Article> channel(String channelId, int page, int size) {
        Page<Article> pages = PageHelper.startPage(page, size);
        List<Article> articles = articleMapper.selectByChannelId(channelId);
        return new PageResult<>(pages.getTotal(),articles);
    }


    public PageResult<Article> column(String columnId, int page, int size) {
        Page<Article> pages = PageHelper.startPage(page, size);
        List<Article> articles = articleMapper.selectByColumnId(columnId);
        return new PageResult<>(pages.getTotal(),articles);
    }
}
