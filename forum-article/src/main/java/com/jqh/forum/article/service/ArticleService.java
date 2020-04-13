package com.jqh.forum.article.service;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.jqh.forum.article.client.SearchClient;
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
    @Resource
    SearchClient searchClient;

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
        Example.Criteria criteria = example.createCriteria();
        // ID
        if (whereMap.get("id") != null && !"".equals(whereMap.get("id"))) {
            criteria.andLike("id", "%" + (String) whereMap.get("id") + "%");
        }
        // 专栏ID
        if (whereMap.get("columnid") != null && !"".equals(whereMap.get("columnid"))) {
            criteria.andLike("columnid", "%" + (String) whereMap.get("columnid") + "%");
        }
        // 用户ID
        if (whereMap.get("userid") != null && !"".equals(whereMap.get("userid"))) {
            criteria.andLike("userid", "%" + (String) whereMap.get("userid") + "%");
        }
        // 标题
        if (whereMap.get("title") != null && !"".equals(whereMap.get("title"))) {
            criteria.andLike("title", "%" + (String) whereMap.get("title") + "%");
        }
        // 文章正文
        if (whereMap.get("content") != null && !"".equals(whereMap.get("content"))) {
            criteria.andLike("content", "%" + (String) whereMap.get("content") + "%");
        }
        // 文章封面
        if (whereMap.get("image") != null && !"".equals(whereMap.get("image"))) {
            criteria.andLike("image", "%" + (String) whereMap.get("image") + "%");
        }
        // 是否公开
        if (whereMap.get("ispublic") != null && !"".equals(whereMap.get("ispublic"))) {
            criteria.andLike("ispublic", "%" + (String) whereMap.get("ispublic") + "%");
        }
        // 是否置顶
        if (whereMap.get("istop") != null && !"".equals(whereMap.get("istop"))) {
            criteria.andLike("istop", "%" + (String) whereMap.get("istop") + "%");
        }
        // 审核状态（这个原来叫condition）
        if (whereMap.get("state") != null && !"".equals(whereMap.get("state"))) {
            criteria.andLike("state", "%" + (String) whereMap.get("state") + "%");
        }
        // 所属频道
        if (whereMap.get("channelid") != null && !"".equals(whereMap.get("channelid"))) {
            criteria.andLike("channelid", "%" + (String) whereMap.get("channelid") + "%");
        }
        // URL
        if (whereMap.get("url") != null && !"".equals(whereMap.get("url"))) {
            criteria.andLike("url", "%" + (String) whereMap.get("url") + "%");
        }
        // 类型
        if (whereMap.get("type") != null && !"".equals(whereMap.get("type"))) {
            criteria.andLike("type", "%" + (String) whereMap.get("type") + "%");
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
        Article article=(Article)redisTemplate.opsForValue().get("article:::" + id);
        if (article==null){
            article=articleMapper.selectByPrimaryKey(id);
            redisTemplate.opsForValue().set("article:::"+id,article);
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
        redisTemplate.delete("article:::"+article.getId());
    }

    /**
     * 删除
     *
     * @param id
     */
    public void deleteById(String id) {
        articleMapper.deleteByPrimaryKey(id);
        redisTemplate.delete("article:::"+id);
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

    /**
     * 获取未同步且已审核的文章列表(按照创建时间升序)
     * @param page
     * @param size
     * @return
     */
    public List<Article> getUnMoveArticle(int page, int size) {
        Example example = new Example(Article.class);
        example.createCriteria().andEqualTo("state","1").andEqualTo("flag","0");
        PageHelper.startPage(page,size,"createtime desc");
        List<Article> articles = articleMapper.selectByExample(example);
        return articles;
    }
    /**
     * 批量设置文章已被同步
     * @return
     */
    public void hasMove(List<Article> articles) {
        for (Article article : articles) {
            article.setFlag("1");
            articleMapper.updateByPrimaryKeySelective(article);
        }
    }

    public Object getUnMoveArticleNum() {
        return articleMapper.getUnMoveArticleNum();
    }

    /**
     * 审核/拉黑文章，审核后自动同步到es库,如果是拉黑则从库中删除
     * @param articleId
     * @param state
     */
    public void changeState(String articleId, String state) {
        articleMapper.changeState(articleId,state);
        if ("1".equals(state)){
            this.move(articleId);
        }
        if ("0".equals(state)){
             searchClient.deleteByKey(articleId);
        }
    }
    /**
     * 手动同步文章到es库
     * @param articleId
     */
    public void move(String articleId) {
        searchClient.add(this.findById(articleId));
    }
}
