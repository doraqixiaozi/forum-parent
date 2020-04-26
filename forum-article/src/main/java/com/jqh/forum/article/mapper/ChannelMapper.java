package com.jqh.forum.article.mapper;

import com.jqh.forum.article.pojo.Channel;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * @Auther: 几米
 * @Date: 2019/5/11 13:51
 * @Description: ChannelMapper
 */
public interface ChannelMapper extends Mapper<Channel> {
    @Select("select * from tb_channel where state='1'")
    List<Channel> getAll();
}
