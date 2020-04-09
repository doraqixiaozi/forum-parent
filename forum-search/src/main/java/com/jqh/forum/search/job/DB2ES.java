package com.jqh.forum.search.job;

import com.jqh.forum.search.client.ArticleClient;
import com.jqh.forum.search.dao.ArticleDao;
import com.jqh.forum.search.pojo.Article;
import com.jqh.forum.search.pojo.ArticleES;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * DB2ES
 * 定时任务，将数据库数据同步到es中
 *
 * @author 862965251@qq.com
 * @date 2020-03-02 16:21
 */
@Component
@Slf4j
public class DB2ES {
    @Resource
    private RedisTemplate redisTemplate;
    @Resource
    private ArticleClient articleClient;
    @Resource
    private ArticleDao articleDao;
    /**
     * 每日最大同步数量
     */
    private static Integer MAX_NUM = 1000;
    private static Integer Batch_NUM = 100;
    /**
     * redis中用于计数的key前缀
     */
    private static String countKey = "count:::article:::";

    /**
     * 将文章数据从数据库同步到es
     */
    @Scheduled(cron = "0 0/30 * * * ?")
    public void moveArticle() {
        log.info("article迁移任务开始，时间{}",DateFormatUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss"));
        Integer unMoveArticleNum = articleClient.getUnMoveArticleNum().getData();
        int num = unMoveArticleNum <= MAX_NUM ? unMoveArticleNum / Batch_NUM + 1 : MAX_NUM / Batch_NUM;
        List<Article> articles = null;
        for (int i = 1; i <= num; i++) {
            articles = (List<Article>) articleClient.getUnMoveArticle(i, Batch_NUM).getData();
            if (CollectionUtils.isEmpty(articles)){
                break;
            }
            articles.forEach(System.out::println);
            List<ArticleES> collect = articles.stream().map(article -> {
                ArticleES articleES = new ArticleES();
                articleES.setContent(article.getContent());
                articleES.setId(article.getId());
                articleES.setImage(article.getImage());
                articleES.setTitle(article.getTitle());
                log.trace(articleES.toString());
                return articleES;
            }).collect(Collectors.toList());
            log.debug(collect.size()+"");
            collect.forEach(System.out::println);
            articleDao.saveAll(collect);
            articleClient.hasMove(articles);
        }
        log.info("article迁移任务结束，时间{}",DateFormatUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss"));
    }

    /**
     * 获取当前计数值
     *
     * @return
     */
    public int getRedisCount() {
        Double count = redisTemplate.opsForZSet().score(countKey + DateFormatUtils.format(new Date(), "yyyy-MM-dd"), "count");
        return count == null ? 0 : count.intValue();
    }


}
