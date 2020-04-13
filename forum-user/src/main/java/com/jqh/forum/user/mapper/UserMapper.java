package com.jqh.forum.user.mapper;

import com.jqh.forum.user.pojo.User;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;
import tk.mybatis.mapper.common.Mapper;

/**
 * @Auther: 几米
 * @Date: 2019/5/25 17:17
 * @Description: UserMapper
 */
public interface UserMapper extends Mapper<User> {
    //粉丝数加一
    @Update("update tb_user set fanscount=fanscount+#{num} where id=#{userid}")
    void updateFanscount(@Param("num")int num, @Param("userid") String userid);
    //关注数加一
    @Update("update tb_user set followcount=followcount+#{num} where id=#{userid}")
    void updateFollowcount(@Param("num")int num, @Param("userid")String userid);
    @Update("update tb_user set state=#{state} where id=#{id}")
    void changeState(@Param("id")String id,@Param("state") String state);
}
