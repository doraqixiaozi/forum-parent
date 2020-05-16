package com.jqh.forum.user.service;

import com.jqh.forum.user.dto.MessageDTO;
import com.jqh.forum.user.mapper.MessageMapper;
import com.jqh.forum.user.pojo.Message;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;
import util.IdWorker;

import javax.annotation.Resource;
import java.util.*;

/**
 * MessageService
 *
 * @author 862965251@qq.com
 * @date 2020-05-15 17:43
 */
@Service
public class MessageService {
    @Resource
    private IdWorker idWorker;
    @Resource
    private MessageMapper messageMapper;

    public void insert(Message message) {
        message.setCreatetime(new Date());
        message.setFlag(0 + "");
        message.setId(idWorker.nextId() + "");
        messageMapper.insert(message);
        //如果发消息说明对方的消息已经看过了
        messageMapper.setHasRead(message.getFromid(),message.getToid());
    }
    /**
     * 获取用户的未读消息列表
     * @return
     */
    public List<MessageDTO> getUnRead(String id) {
        List<MessageDTO> list = messageMapper.getUnRead(id);
//        list.sort(Comparator.comparing(Message::getCreatetime));
        return list;
    }
    /**
     * 获取与某用户的所有对话列表
     * @param id
     * @return
     */
    public List<Message> getUnRead(String userId, String id) {
        return messageMapper.getMessage(userId,id);
    }

    public void setHasRead(String userId, String id) {
        messageMapper.setHasRead(userId,id);
    }
}
