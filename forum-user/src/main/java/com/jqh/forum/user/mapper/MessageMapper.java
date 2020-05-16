package com.jqh.forum.user.mapper;

import com.jqh.forum.user.dto.MessageDTO;
import com.jqh.forum.user.pojo.Message;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * MessageMapper
 *
 * @author 862965251@qq.com
 * @date 2020-05-15 17:39
 */
public interface MessageMapper extends Mapper<Message> {
    @Select("SELECT t4.avatar,t4.nickname,t3.fromid,t3.content,t3.createtime,t3.num,t3.flag  from tb_user t4 JOIN(SELECT t1.fromid,t1.content,t1.createtime,t2.num,t1.flag from tb_message t1 join (SELECT fromid,MAX(createtime) ct,COUNT(createtime) num from tb_message where toid=#{userId} GROUP BY fromid) t2 on t1.createtime=t2.ct and t1.fromid=t2.fromid) t3 on t3.fromid=t4.id order by t3.createtime desc")
    List<MessageDTO> getUnRead(@Param("userId") String userId);

    @Select("SELECT * from tb_message where (toid=#{userId} and fromid=#{id}) or (toid=#{id} and fromid=#{userId}) order by createtime asc")
    List<Message> getMessage(@Param("userId") String userId, @Param("id") String id);
@Update("UPDATE tb_message set flag='1' where fromid=#{id} and toid=#{userId}")
    void setHasRead(@Param("userId") String userId, @Param("id") String id);
}
