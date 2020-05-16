package com.jqh.forum.recruit.mapper;

import com.jqh.forum.recruit.pojo.Enterprise;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;
import tk.mybatis.mapper.common.Mapper;

/**
 * @Auther: 几米
 * @Date: 2019/5/7 21:19
 * @Description: EnterpriseMapper
 */
public interface EnterpriseMapper extends Mapper<Enterprise> {
    @Update("update tb_enterprise set jobcount=jobcount+1 where id=#{id}")
    void addJobCount(@Param("id") String id);
    @Update("update tb_enterprise set jobcount=jobcount-1 where id=#{id}")
    void cutJobCount(@Param("id") String id);
}
