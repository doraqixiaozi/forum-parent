package com.jqh.forum.friend.pojo;

import lombok.Data;

import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * @Auther: 几米
 * @Date: 2019/5/31 13:27
 * @Description: Friend
 */
@Data
@Table(name = "tb_friend")
public class Friend implements Serializable {
    @Id
    private String userid;
    @Id
    private String friendid;
    private String islike;

    public Friend() {
    }

    public Friend(String userid, String friendid, String islike) {
        this.userid = userid;
        this.friendid = friendid;
        this.islike = islike;
    }
}
