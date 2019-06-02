package com.jqh.forum.friend.pojo;

import lombok.Data;

import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @Auther: 几米
 * @Date: 2019/5/31 15:03
 * @Description: NoFriend
 */
@Data
@Table(name = "tb_nofriend")
public class NoFriend {
    @Id
    private String userid;
    @Id
    private String friendid;

    public NoFriend() {
    }

    public NoFriend(String userid, String friendid) {
        this.userid = userid;
        this.friendid = friendid;
    }
}
