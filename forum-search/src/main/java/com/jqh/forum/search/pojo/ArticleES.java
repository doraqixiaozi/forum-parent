package com.jqh.forum.search.pojo;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import util.IdWorker;

import java.io.Serializable;

/**
 * @Auther: 几米
 * @Date: 2019/5/22 18:10
 * @Description: Article
 */
@Data
@Document(indexName = "forum_article",type = "article")
public class ArticleES implements Serializable {
    @Id
    @Field(index = false,store = true,type = FieldType.Text)
    private String id;//ID
    @Field(index = true,store = true,analyzer = "ik_max_word",searchAnalyzer = "ik_max_word",type = FieldType.Text)
    private String title;//标题
    @Field(index = true,store = true,analyzer = "ik_max_word",searchAnalyzer = "ik_max_word",type = FieldType.Text)
    private String content;//文章正文
    @Field(index = false,store = true,type = FieldType.Text)
    private String image;//文章封面
    @Field(index = false,store = true,type = FieldType.Text)
    private String userid;//用户ID
    @Field(index = false,store = true,type = FieldType.Text)
    private String nickname;//用户名
    @Field(index = false,store = true,type = FieldType.Text)
    private String avatar;//用户头像
//    @Field(index = false,store = true,type = FieldType.Date)
//    private java.util.Date createtime;//发表日期
//    @Field(index = false,store = true,type = FieldType.Integer)
//    private Integer visits;//浏览量
//    @Field(index = false,store = true,type = FieldType.Integer)
//    private Integer thumbup;//点赞数
//    @Field(index = false,store = true,type = FieldType.Integer)
//    private Integer comment;//评论数
//    @Field(index = false,store = true,type = FieldType.Text)
//    private String state;//审核状态
}
