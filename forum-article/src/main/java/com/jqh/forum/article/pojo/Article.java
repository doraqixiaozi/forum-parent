package com.jqh.forum.article.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
/**
 * 实体类
 * @author Administrator
 *
 */
@Data
@Table(name="tb_article")
public class Article implements Serializable{

	@Id
	private String id;//ID
	private String columnid;//专栏ID
	private String userid;//用户ID
	private String title;//标题
	private String content;//文章正文
	private String image;//文章封面
	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss" )
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
	private java.util.Date createtime;//发表日期
	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss" )
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
	private java.util.Date updatetime;//修改日期
	private String ispublic;//是否公开
	private String istop;//是否置顶
	private Integer visits;//浏览量
	private Integer thumbup;//点赞数
	private Integer comment;//评论数
	private String state;//审核状态
	private String channelid;//所属频道
	private String url;//URL
	private String type;//类型
	private String flag;//是否已同步至es

}
