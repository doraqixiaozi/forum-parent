package com.jqh.forum.user.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * 消息类
 *
 * @author 862965251@qq.com
 * @date 2020-05-15 17:31
 */
@Data
@Table(name="tb_message")
public class Message {

    /**
     * 主键
     */
    @Id
    private String id;
    /**
     * 发送方
     */
    private String fromid;
    /**
     * 接收方
     */
    private String toid;
    /**
     * 主要内容
     */
    private String content;
    /**
     * 创建时间
     */
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date createtime;
    /**
     * 是否已读
     */
    private String flag;
}
