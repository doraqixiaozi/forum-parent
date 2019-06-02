package com.jqh.forum.recruit.service;

import java.util.*;

import javax.annotation.Resource;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.jqh.forum.recruit.mapper.EnterpriseMapper;
import entity.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;
import util.IdWorker;

import com.jqh.forum.recruit.pojo.Enterprise;

/**
 * 服务层
 *
 * @author Administrator
 */
@Service
@Transactional
public class EnterpriseService {

    @Resource
    private EnterpriseMapper enterpriseMapper;

    @Autowired
    private IdWorker idWorker;

    /**
     * 查询全部列表
     *
     * @return
     */
    public List<Enterprise> findAll() {
        return enterpriseMapper.selectAll();
    }


    /**
     * 条件查询+分页
     *
     * @param whereMap
     * @param page
     * @param size
     * @return
     */
    public PageResult<Enterprise> findSearch(Map whereMap, int page, int size) {
        Page<Enterprise> enterprisePage = PageHelper.startPage(page, size);
        List<Enterprise> enterprises = this.findSearch(whereMap);
        PageResult<Enterprise> enterprisePageResult = new PageResult<>();
        enterprisePageResult.setTotal(enterprisePage.getTotal());
        enterprisePageResult.setRows(enterprises);
        return enterprisePageResult;
    }


    /**
     * 条件查询
     *
     * @param whereMap
     * @return
     */
    public List<Enterprise> findSearch(Map whereMap) {
        Example example = new Example(Enterprise.class);
        // ID
        if (whereMap.get("id") != null && !"".equals(whereMap.get("id"))) {
            example.createCriteria().andLike("id", "%" + (String) whereMap.get("id") + "%");
        }
        // 企业名称
        if (whereMap.get("name") != null && !"".equals(whereMap.get("name"))) {
            example.createCriteria().andLike("name", "%" + (String) whereMap.get("name") + "%");
        }
        // 企业简介
        if (whereMap.get("summary") != null && !"".equals(whereMap.get("summary"))) {
            example.createCriteria().andLike("summary", "%" + (String) whereMap.get("summary") + "%");
        }
        // 企业地址
        if (whereMap.get("address") != null && !"".equals(whereMap.get("address"))) {
            example.createCriteria().andLike("address", "%" + (String) whereMap.get("address") + "%");
        }
        // 标签列表
        if (whereMap.get("labels") != null && !"".equals(whereMap.get("labels"))) {
            example.createCriteria().andLike("labels", "%" + (String) whereMap.get("labels") + "%");
        }
        // 坐标
        if (whereMap.get("coordinate") != null && !"".equals(whereMap.get("coordinate"))) {
            example.createCriteria().andLike("coordinate", "%" + (String) whereMap.get("coordinate") + "%");
        }
        // 是否热门
        if (whereMap.get("ishot") != null && !"".equals(whereMap.get("ishot"))) {
            example.createCriteria().andLike("ishot", "%" + (String) whereMap.get("ishot") + "%");
        }
        // LOGO
        if (whereMap.get("logo") != null && !"".equals(whereMap.get("logo"))) {
            example.createCriteria().andLike("logo", "%" + (String) whereMap.get("logo") + "%");
        }
        // URL
        if (whereMap.get("url") != null && !"".equals(whereMap.get("url"))) {
            example.createCriteria().andLike("url", "%" + (String) whereMap.get("url") + "%");
        }
        return enterpriseMapper.selectByExample(example);
    }

    /**
     * 根据ID查询实体
     *
     * @param id
     * @return
     */
    public Enterprise findById(String id) {
        return enterpriseMapper.selectByPrimaryKey(id);
    }

    /**
     * 增加
     *
     * @param enterprise
     */
    public void add(Enterprise enterprise) {
        enterprise.setId(idWorker.nextId() + "");
        enterpriseMapper.insert(enterprise);
    }

    /**
     * 修改
     *
     * @param enterprise
     */
    public void update(Enterprise enterprise) {
        enterpriseMapper.updateByPrimaryKey(enterprise);
    }

    /**
     * 删除
     *
     * @param id
     */
    public void deleteById(String id) {
        enterpriseMapper.deleteByPrimaryKey(id);
    }

    //查询热门企业
    public List<Enterprise> selectByIsHot(String isHot){
        Example example = new Example(Enterprise.class);
        example.createCriteria().andEqualTo("ishot",isHot);
        return enterpriseMapper.selectByExample(example);
    }
}
