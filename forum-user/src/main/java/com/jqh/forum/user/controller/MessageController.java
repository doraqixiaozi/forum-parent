package com.jqh.forum.user.controller;

import com.jqh.forum.user.dto.MessageDTO;
import com.jqh.forum.user.pojo.Message;
import com.jqh.forum.user.service.MessageService;
import entity.Result;
import entity.StatusCode;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * MessageController
 *
 * @author 862965251@qq.com
 * @date 2020-05-16 13:40
 */
@RestController
@CrossOrigin
@Slf4j
@RequestMapping("/message")
public class MessageController {
    @Resource
    private HttpServletRequest request;
    @Resource
    private MessageService messageService;

    /**
     * 获取用户的未读消息列表
     * @return
     */
    @GetMapping()
    public Result getUnRead(){
        Claims claims= (Claims) request.getAttribute("claims_user");
        if (claims!=null) {
            String id = claims.getId();
            List<MessageDTO> list = messageService.getUnRead(id);
            return new Result(true, StatusCode.OK, "查询成功", list);
        }
        return new Result(true, StatusCode.ERROR, "用户未登录");
    }

    /**
     * 获取与某用户的所有对话列表
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public Result getUnRead(@PathVariable String id){
        Claims claims= (Claims) request.getAttribute("claims_user");
        if (claims!=null) {
            String userId = claims.getId();
            List<Message> list = messageService.getUnRead(userId, id);
            return new Result(true, StatusCode.OK, "查询成功", list);
        }
        return new Result(true, StatusCode.ERROR, "用户未登录");
    }

    /**
     * 设置已读
     * @return
     */
    @GetMapping("/read/{id}")
    public Result setHasRead(@PathVariable String id){
        Claims claims= (Claims) request.getAttribute("claims_user");
        if (claims!=null) {
            String userId = claims.getId();
            messageService.setHasRead(userId, id);
            return new Result(true, StatusCode.OK, "设置成功");
        }
        return new Result(true, StatusCode.ERROR, "用户未登录");
    }

}
