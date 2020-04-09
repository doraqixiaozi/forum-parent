package com.jqh.forum.base.pojo;

import lombok.Data;

import javax.persistence.Id;
import javax.persistence.Table;

/**
 * City
 *
 * @author 862965251@qq.com
 * @date 2020-04-07 18:35
 */
@Data
@Table(name = "tb_city")
public class City {
    @Id
    private String id;//
    /**
     * 城市名称
     */
    private String name;
    /**
     * 是否热门
     */
    private String ishot;

}
