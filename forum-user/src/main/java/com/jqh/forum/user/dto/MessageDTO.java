package com.jqh.forum.user.dto;

import com.jqh.forum.user.pojo.Message;
import lombok.Data;

/**
 * MessageDTO
 *
 * @author 862965251@qq.com
 * @date 2020-05-16 14:06
 */
@Data
public class MessageDTO extends Message {
    /**
     * 有多少条消息
     */
    private int num;
    private String avatar;
    private String nickname;
}
