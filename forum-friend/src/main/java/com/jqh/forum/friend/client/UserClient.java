package com.jqh.forum.friend.client;

import entity.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;

/**
 * @Auther: 几米
 * @Date: 2019/5/31 15:50
 * @Description: UserClient
 */
@FeignClient(value = "forum-user",fallback = UserClientFallBack.class)
public interface UserClient {
    /**
     * 修改粉丝关注
     * @param userid
     * @param friendid
     * @param num
     * @return
     */
    @PutMapping("/user/{userid}/{friendid}/{num}")
    public Result updateFanscountAndFollowcount(@PathVariable String userid, @PathVariable String friendid,@PathVariable int num);
}
