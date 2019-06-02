package com.jqh.forum.base.pojo;

import lombok.Data;

import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * @Auther: 几米
 * @Date: 2019/5/5 19:16
 * @Description: Label
 */
@Table(name = "tb_label")
@Data
public class Label implements Serializable {
    @Id
    private String id;//
    private String labelname;//标签名称
    private String state;//状态
    private Long count;//使用数量
    private Long fans;//关注数
    private String recommend;//是否推荐
}
