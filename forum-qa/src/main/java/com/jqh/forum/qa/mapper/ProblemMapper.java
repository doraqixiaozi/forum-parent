package com.jqh.forum.qa.mapper;

import com.jqh.forum.qa.pojo.Problem;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * 数据访问接口
 *
 * @author Administrator
 */
@org.apache.ibatis.annotations.Mapper
public interface ProblemMapper extends Mapper<Problem> {
    @Select("SELECT * from tb_problem join (select * from tb_pl where labelid=#{lableid}) pb on(id=problemid) order by replytime desc")
    public List<Problem> newlist(String lableid);

    @Select("SELECT * from tb_problem join (select * from tb_pl where labelid=#{lableid}) pb on(id=problemid) order by reply desc")
    public List<Problem> hotlist(String lableid);
    @Select("SELECT * from tb_problem join (select * from tb_pl where labelid=#{lableid}) pb on(id=problemid) where reply=0 order by createtime desc")
    public List<Problem> waitlist(String lableid);
}
