package com.jqh.forum.article.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Id;
import javax.persistence.Table;

/**
 * w文章评论
 *
 * @author 862965251@qq.com
 * @date 2020-04-20 15:52
 */
@Data
@Table(name="tb_comment")
public class Comment {
    @Id
    private String id;//ID
    private String userid;//用户ID
    private String nickname;//用户名
    private String avatar;//用户头像
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss" )
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private java.util.Date createtime;//发表日期
    private String content;//评论正文
    private String articleid;//所属文章id

}
