package com.jqh.forum.article.mapper;

import com.jqh.forum.article.pojo.Comment;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;
import tk.mybatis.mapper.common.Mapper;

/**
 * CommentMapper
 *
 * @author 862965251@qq.com
 * @date 2020-04-20 16:12
 */
public interface CommentMapper extends Mapper<Comment> {
    @Update("update tb_comment set nickname=#{nickname},avatar=#{avatar} where userid=#{userid}")
    void updateUserData(@Param("nickname") String nickName, @Param("avatar") String avatar, @Param("userid") String userId);
}
