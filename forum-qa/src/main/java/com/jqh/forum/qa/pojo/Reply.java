package com.jqh.forum.qa.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * 实体类
 *
 * @author Administrator
 */
@Data
@Table(name = "tb_reply")
public class Reply implements Serializable {

    @Id
    private String id;//编号


    private String problemid;//问题ID
    private String content;//回答内容
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss" )
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private java.util.Date createtime;//创建日期
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss" )
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private java.util.Date updatetime;//更新日期
    private String userid;//回答人ID
    private String nickname;//回答人昵称
}
