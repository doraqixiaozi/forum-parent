package com.jqh.forum.friend.mapper;

import com.jqh.forum.friend.pojo.NoFriend;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;

/**
 * @Auther: 几米
 * @Date: 2019/5/31 15:04
 * @Description: NofriendMapper
 */
public interface NofriendMapper extends Mapper<NoFriend> {
    @Select("select * from tb_nofriend where userid=#{userid} and friendid=#{friendid}")
    NoFriend selectByUseridAndFriendid(@Param("userid") String userid,@Param("friendid") String friendid);
    @Delete("delete from tb_nofriend where userid=#{userid} and friendid=#{friendid}")
    void deleteNoFriend(@Param("userid") String userid, @Param("friendid") String friendid);
}
