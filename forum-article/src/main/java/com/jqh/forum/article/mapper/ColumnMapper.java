package com.jqh.forum.article.mapper;

import com.jqh.forum.article.pojo.Column;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;
import tk.mybatis.mapper.common.Mapper;

/**
 * @Auther: 几米
 * @Date: 2019/5/11 13:51
 * @Description: ColumnMapper
 */
public interface ColumnMapper extends Mapper<Column> {
    @Update("update tb_column set state=#{state} where id=#{columnId}")
    void changeState(@Param("columnId") String columnId, @Param("state")String state);
}
