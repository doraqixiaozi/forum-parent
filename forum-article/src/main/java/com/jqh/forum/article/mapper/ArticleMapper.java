package com.jqh.forum.article.mapper;

import com.jqh.forum.article.pojo.Article;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * @Auther: 几米
 * @Date: 2019/5/11 13:51
 * @Description: ArticleMapper
 */
public interface ArticleMapper extends Mapper<Article> {
    @Update("update tb_article set state=1 where id=#{id}")
    void updateState(String id);

    @Update("update tb_article set thumbup=thumbup+1 where id=#{id}")
    void addThumbup(String id);

    @Select("select * from tb_article where channelid=#{channelId}")
    List<Article> selectByChannelId(String channelId);

    @Select("select * from tb_article where columnid=#{columnId}")
    List<Article> selectByColumnId(String columnId);
    @Select("select count(*) from tb_article where state='1' and flag='0'")
    Integer getUnMoveArticleNum();
    @Update("update tb_article set state=#{state} where id=#{articleId}")
    void changeState(@Param("articleId") String articleId,@Param("state") String state);
}
