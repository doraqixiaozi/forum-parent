package com.jqh.forum.qa.mapper;

import com.jqh.forum.qa.pojo.Problem;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
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
    List<Problem> newlist(String lableid);

    @Select("SELECT * from tb_problem join (select * from tb_pl where labelid=#{lableid}) pb on(id=problemid) order by reply desc")
    List<Problem> hotlist(String lableid);

    @Select("SELECT * from tb_problem join (select * from tb_pl where labelid=#{lableid}) pb on(id=problemid) where reply=0 order by createtime desc")
    List<Problem> waitlist(String lableid);

    @Update("update tb_problem set thumbup=thumbup+1 where id=#{problemId}")
    void addThumbup(String problemId);

    @Update("update tb_problem set visits=visits+1 where id=#{problemId}")
    void addVisits(String problemId);
}
