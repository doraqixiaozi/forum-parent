package com.jqh.forum.recruit.service;

import java.util.*;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.jqh.forum.recruit.mapper.RecruitMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;
import util.IdWorker;
import com.jqh.forum.recruit.pojo.Recruit;

import javax.annotation.Resource;

/**
 * 服务层
 *
 * @author Administrator
 */
@Slf4j
@Service
@Transactional
public class RecruitService {

    @Resource
    private RecruitMapper recruitMapper;

    @Autowired
    private IdWorker idWorker;

    /**
     * 查询全部列表
     *
     * @return
     */
    public List<Recruit> findAll() {
        return recruitMapper.selectAll();
    }


    /**
     * 条件查询+分页
     *
     * @param whereMap
     * @param page
     * @param size
     * @return
     */
    public Map findSearch(Map whereMap, int page, int size) {
        Page<Recruit> recruitPage = PageHelper.startPage(page, size);
        List<Recruit> recruits = this.findSearch(whereMap);
        HashMap hashMap = new HashMap();
        hashMap.put("total", recruitPage.getTotal());
        hashMap.put("rows", recruits);
        log.trace(hashMap.toString());
        return hashMap;
    }


    /**
     * 条件查询
     *
     * @param searchMap
     * @return
     */
    public List<Recruit> findSearch(Map searchMap) {

        Example example = new Example(Recruit.class);
        // ID
        if (searchMap.get("id") != null && !"".equals(searchMap.get("id"))) {
            example.createCriteria().andLike("id", "%" + (String) searchMap.get("id") + "%");
        }
        // 职位名称
        if (searchMap.get("jobname") != null && !"".equals(searchMap.get("jobname"))) {
            example.createCriteria().andLike("jobname", "%" + (String) searchMap.get("jobname") + "%");
        }
        // 薪资范围
        if (searchMap.get("salary") != null && !"".equals(searchMap.get("salary"))) {
            example.createCriteria().andLike("salary", "%" + (String) searchMap.get("salary") + "%");
        }
        // 经验要求
        if (searchMap.get("experience") != null && !"".equals(searchMap.get("experience"))) {
            example.createCriteria().andLike("experience", "%" + (String) searchMap.get("experience") + "%");
        }
        // 学历要求
        if (searchMap.get("education") != null && !"".equals(searchMap.get("education"))) {
            example.createCriteria().andLike("education", "%" + (String) searchMap.get("education") + "%");
        }
        // 任职方式
        if (searchMap.get("type") != null && !"".equals(searchMap.get("type"))) {
            example.createCriteria().andLike("type", "%" + (String) searchMap.get("type") + "%");
        }
        // 办公地址
        if (searchMap.get("address") != null && !"".equals(searchMap.get("address"))) {
            example.createCriteria().andLike("address", "%" + (String) searchMap.get("address") + "%");
        }
        // 企业ID
        if (searchMap.get("eid") != null && !"".equals(searchMap.get("eid"))) {
            example.createCriteria().andLike("eid", "%" + (String) searchMap.get("eid") + "%");
        }
        // 状态
        if (searchMap.get("state") != null && !"".equals(searchMap.get("state"))) {
            example.createCriteria().andLike("state", "%" + (String) searchMap.get("state") + "%");
        }
        // 网址
        if (searchMap.get("url") != null && !"".equals(searchMap.get("url"))) {
            example.createCriteria().andLike("url", "%" + (String) searchMap.get("url") + "%");
        }
        // 标签
        if (searchMap.get("label") != null && !"".equals(searchMap.get("label"))) {
            example.createCriteria().andLike("label", "%" + (String) searchMap.get("label") + "%");
        }
        // 职位描述
        if (searchMap.get("content1") != null && !"".equals(searchMap.get("content1"))) {
            example.createCriteria().andLike("content1", "%" + (String) searchMap.get("content1") + "%");
        }
        // 职位要求
        if (searchMap.get("content2") != null && !"".equals(searchMap.get("content2"))) {
            example.createCriteria().andLike("content2", "%" + (String) searchMap.get("content2") + "%");
        }
        return recruitMapper.selectByExample(example);
    }

    /**
     * 根据ID查询实体
     *
     * @param id
     * @return
     */
    public Recruit findById(String id) {
        return recruitMapper.selectByPrimaryKey(id);
    }

    /**
     * 增加
     *
     * @param recruit
     */
    public void add(Recruit recruit) {
        recruit.setId(idWorker.nextId() + "");
        recruitMapper.insert(recruit);
    }

    /**
     * 修改
     *
     * @param recruit
     */
    public void update(Recruit recruit) {
        recruitMapper.updateByPrimaryKey(recruit);
    }

    /**
     * 删除
     *
     * @param id
     */
    public void deleteById(String id) {
        recruitMapper.deleteByPrimaryKey(id);
    }

    //查询推荐职位，按创建时间排序的第一页的6个
    public List<Recruit> selectByState(String state,boolean isEquals) {
        Example example = new Example(Recruit.class);
        if (isEquals) {
            example.createCriteria().andEqualTo("state", state);
        }else {
            example.createCriteria().andNotEqualTo("state", state);
        }
        example.setOrderByClause("createtime Desc");
        return recruitMapper.selectByExampleAndRowBounds(example, new RowBounds(0, 6));
    }
}
