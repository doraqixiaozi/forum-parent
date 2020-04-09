package com.jqh.forum.recruit.dto;

import com.jqh.forum.recruit.pojo.Recruit;
import lombok.Data;

/**
 * RecruitDTO
 *
 * @author 862965251@qq.com
 * @date 2020-04-09 15:07
 */
@Data
public class RecruitDTO extends Recruit {
    /**
     * 职位所属企业名称
     */
    private String eName;
}
