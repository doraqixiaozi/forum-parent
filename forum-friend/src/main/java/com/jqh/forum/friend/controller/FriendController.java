package com.jqh.forum.friend.controller;

import com.jqh.forum.friend.service.FriendService;
import entity.Result;
import entity.StatusCode;
import io.jsonwebtoken.Claims;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * @Auther: 几米
 * @Date: 2019/5/30 23:07
 * @Description: FriendService
 */
@RestController()
@CrossOrigin
@RequestMapping("/friend")
public class FriendController {
    @Autowired
    private HttpServletRequest request;
    @Autowired
    private FriendService friendService;

    //添加好友或黑名单
    @PutMapping("/like/{friendid}/{type}")
    public Result addFriend(@PathVariable String friendid, @PathVariable String type) {
//验证是否登录并拿到当前用户id
        Claims claims = (Claims) request.getAttribute("claims_user");
        if (claims == null) {
            return new Result(false, StatusCode.ACCESSERROR, "登录状态异常");
        }
        String userId = claims.getId();
        //判断添加好友还是添加黑名单
        if (type != null) {
            //添加好友
            if (type.equals("1")) {
                boolean flag = friendService.addFriend(userId, friendid);
                if (flag) {
                    return new Result(true, StatusCode.OK, "添加成功");
                } else {
                    return new Result(false, StatusCode.ERROR, "重复添加好友");
                }
            }
            //添加黑名单
            if (type.equals("2")) {
                boolean flag = friendService.addNoFriend(userId, friendid);
                if (flag) {
                    return new Result(true, StatusCode.OK, "添加成功");
                } else {
                    return new Result(false, StatusCode.ERROR, "重复添加黑名单");
                }
            }
            return new Result(false, StatusCode.ERROR, "参数异常");
        } else {
            return new Result(false, StatusCode.ERROR, "参数异常");
        }
    }

    //删除好友
    @DeleteMapping("/{friendid}")
    public Result deleteFriend(@PathVariable String friendid) {
//验证是否登录并拿到当前用户id
        Claims claims = (Claims) request.getAttribute("claims_user");
        if (claims == null) {
            return new Result(false, StatusCode.ACCESSERROR, "登录状态异常");
        }
        String userId = claims.getId();
        friendService.deleteFriend(userId,friendid);
        return new Result(true, StatusCode.OK, "删除成功");
    }

}
