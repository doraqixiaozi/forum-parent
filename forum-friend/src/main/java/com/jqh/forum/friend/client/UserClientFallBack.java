package com.jqh.forum.friend.client;

import entity.Result;
import org.springframework.stereotype.Component;

/**
 * @Auther: 几米
 * @Date: 2019/5/31 15:52
 * @Description: UserClientFallBack
 */
@Component
public class UserClientFallBack implements UserClient {
    @Override
    public Result updateFanscountAndFollowcount(String userid, String friendid, int num) {
        return null;
    }
}
