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
@Table(name="tb_column")
public class Column implements Serializable{

	@Id
	private String id;//ID
	private String name;//专栏名称
	private String summary;//专栏简介
	private String userid;//用户ID
	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss" )
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
	private java.util.Date createtime;//申请日期
	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss" )
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
	private java.util.Date checktime;//审核日期
	private String state;//状态

}
