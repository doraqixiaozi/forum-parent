package com.jqh.forum.gathering.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
/**
 * 实体类
 * @author Administrator
 *
 */
@Data
@Table(name="tb_gathering")
public class Gathering implements Serializable{

	@Id
	private String id;//编号
	private String name;//活动名称
	private String summary;//大会简介
	private String detail;//详细说明
	private String sponsor;//主办方
	private String image;//活动图片
	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss" )
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
	private java.util.Date starttime;//开始时间
	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss" )
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
	private java.util.Date endtime;//截止时间
	private String address;//举办地点
	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss" )
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
	private java.util.Date enrolltime;//报名截止
	private String state;//是否可见
	private String city;//城市

}
