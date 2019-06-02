package com.jqh.forum.friend.service;

import com.jqh.forum.friend.client.UserClient;
import com.jqh.forum.friend.mapper.FriendMapper;
import com.jqh.forum.friend.mapper.NofriendMapper;
import com.jqh.forum.friend.pojo.Friend;
import com.jqh.forum.friend.pojo.NoFriend;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * @Auther: 几米
 * @Date: 2019/5/30 23:23
 * @Description: FriendService
 */
@Service
@Transactional
public class FriendService {
    @Resource
    private FriendMapper friendMapper;
    @Resource
    private NofriendMapper nofriendMapper;
    @Resource
    private UserClient userClient;

    public boolean addFriend(String userId, String friendid) {
        //先查询是否userid到friendid已经存在
        Friend friend = friendMapper.selectByUseridAndFriendid(userId, friendid);
        if (friend != null) {
            return false;
        }
        //再查询是否userid到friendid是黑名单
        NoFriend nofriend = nofriendMapper.selectByUseridAndFriendid(userId, friendid);
        if (nofriend != null) {
            //如果是则删除黑名单数据
            nofriendMapper.deleteNoFriend(userId,friendid);
        }
        //如果不存在则插入且islike置0
        friend = new Friend(userId, friendid, "0");
        friendMapper.insert(friend);
        //判断从friendid到userid是否有数据，如果有则两个状态都改为1
        friend = friendMapper.selectByUseridAndFriendid(friendid, userId);
        if (friend != null) {
            //把双方都置1
            friendMapper.weAreFriend(userId, friendid);
        }
        //更新双方的粉丝和关注
        userClient.updateFanscountAndFollowcount(userId, friendid,1);
        return true;
    }

    public boolean addNoFriend(String userId, String friendid) {
        //先查询是否userid到friendid已经存在
        NoFriend nofriend = nofriendMapper.selectByUseridAndFriendid(userId, friendid);
        if (nofriend != null) {
            return false;
        }
        nofriend = new NoFriend(userId, friendid);
        return true;
    }

    //删除并拉黑
    public void deleteFriend(String userid,String friendid) {
        //删除好友表中的数据
        friendMapper.deleteFriend(userid,friendid);
        //更新friendid到userid的islike为0
        friendMapper.iAmNotYourFriend(friendid,userid);
        //更新黑名单并添加进去
        NoFriend noFriend = new NoFriend(userid, friendid);
        nofriendMapper.insert(noFriend);
        //双方关注数与粉丝数更新
        userClient.updateFanscountAndFollowcount(userid,friendid,-1);

    }
}
