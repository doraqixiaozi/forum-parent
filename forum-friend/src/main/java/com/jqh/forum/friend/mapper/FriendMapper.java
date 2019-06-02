package com.jqh.forum.friend.mapper;

import com.jqh.forum.friend.pojo.Friend;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import tk.mybatis.mapper.common.Mapper;

/**
 * @Auther: 几米
 * @Date: 2019/5/31 13:27
 * @Description: FriendMapper
 */
public interface FriendMapper extends Mapper<Friend> {
    @Select("select * from tb_friend where userid=#{userid} and friendid=#{friendid}")
    Friend selectByUseridAndFriendid(@Param("userid") String userid, @Param("friendid") String friendid);

    @Update("update tb_friend set islike='1' where (userid=#{userid} and friendid=#{friendid}) or (userid=#{friendid} and friendid=#{userid})")
    void weAreFriend(@Param("userid") String userid, @Param("friendid") String friendid);

    @Delete("delete from tb_friend where userid=#{userid} and friendid=#{friendid}")
    void deleteFriend(@Param("userid") String userid, @Param("friendid") String friendid);

    @Update("update tb_friend set islike='0' where (userid=#{userid} and friendid=#{friendid})")
    void iAmNotYourFriend(@Param("userid") String userid, @Param("friendid") String friendid);
}
