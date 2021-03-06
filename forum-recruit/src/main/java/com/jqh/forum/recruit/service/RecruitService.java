package com.jqh.forum.recruit.service;

import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.jqh.forum.recruit.client.BaseClient;
import com.jqh.forum.recruit.dto.RecruitDTO;
import com.jqh.forum.recruit.mapper.EnterpriseMapper;
import com.jqh.forum.recruit.mapper.RecruitMapper;
import com.jqh.forum.recruit.pojo.Enterprise;
import entity.Result;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
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
    @Resource
    private EnterpriseMapper enterpriseMapper;
    @Resource
    private RedisTemplate redisTemplate;
    private static String labelsKey = "labels";
    private static String enterprisesKey = "enterprises";

    @Autowired
    private IdWorker idWorker;
    @Resource
    private BaseClient baseClient;

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
        Page<Recruit> recruitPage = PageHelper.startPage(page, size,"createtime desc");
        List<Recruit> recruits = this.findSearch(whereMap);
        //如果企业缓存为null则设置缓存
        HashMap<String,String> Emap=(HashMap) redisTemplate.opsForValue().get(enterprisesKey);
        if (Emap==null){
            List<Enterprise> es = enterpriseMapper.selectAll();
            Emap = new HashMap<>();
            if (es != null) {
                for (Enterprise e:es) {
                    Emap.put(e.getId(), e.getName());
                }
            }
            redisTemplate.opsForValue().set(enterprisesKey,Emap, 30, TimeUnit.MINUTES);
        }
        //如果标签缓存为null则设置标签缓存
        HashMap<String,String> Lmap=(HashMap) redisTemplate.opsForValue().get(labelsKey);
        if (Lmap == null) {
            Result result = baseClient.getLabelMap();
            HashMap<String, String> map = (HashMap) result.getData();
            Lmap = map;
            redisTemplate.opsForValue().set(labelsKey,Lmap, 30, TimeUnit.MINUTES);
        }
        //将结果集转为dto
        List<RecruitDTO> dtos=new ArrayList<>(recruits.size());
        for (Recruit r:recruits) {
            RecruitDTO recruitDTO = new RecruitDTO();
            BeanUtils.copyProperties(r, recruitDTO);
            recruitDTO.setEName(Emap.get(recruitDTO.getEid()));
            recruitDTO.setLabelName(Lmap.get(recruitDTO.getLabel()));
            dtos.add(recruitDTO);
        }
        HashMap hashMap = new HashMap();
        hashMap.put("total", recruitPage.getTotal());
        hashMap.put("rows", dtos);
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
        Example.Criteria criteria = example.createCriteria();
        makeExample(searchMap, criteria);
        return recruitMapper.selectByExample(example);
    }

    /**
     * 根据查询条件构造条件构造器
     *
     * @param searchMap 查询条件
     * @param criteria  条件构造器
     */
    private void makeExample(Map searchMap, Example.Criteria criteria) {
        // ID
        if (searchMap.get("id") != null && !"".equals(searchMap.get("id"))) {
            criteria.andEqualTo("id", (String) searchMap.get("id"));
        }
        // 职位名称
        if (searchMap.get("jobname") != null && !"".equals(searchMap.get("jobname"))) {
            criteria.andLike("jobname", "%" + (String) searchMap.get("jobname") + "%");
        }
        // 薪资范围
        if (searchMap.get("salary") != null && !"".equals(searchMap.get("salary"))) {
            criteria.andLike("salary", "%" + (String) searchMap.get("salary") + "%");
        }
        // 经验要求
        if (searchMap.get("experience") != null && !"".equals(searchMap.get("experience"))) {
            criteria.andLike("experience", "%" + (String) searchMap.get("experience") + "%");
        }
        // 学历要求
        if (searchMap.get("education") != null && !"".equals(searchMap.get("education"))) {
            criteria.andLike("education", "%" + (String) searchMap.get("education") + "%");
        }
        // 任职方式
        if (searchMap.get("type") != null && !"".equals(searchMap.get("type"))) {
            criteria.andEqualTo("type", (String) searchMap.get("type"));
        }
        // 企业ID
        if (searchMap.get("eid") != null && !"".equals(searchMap.get("eid"))) {
            criteria.andEqualTo("eid",  (String) searchMap.get("eid") );
        }
        // 状态
        if (searchMap.get("state") != null && !"".equals(searchMap.get("state"))) {
            criteria.andEqualTo("state", (String) searchMap.get("state"));
        }
        // 网址
        if (searchMap.get("url") != null && !"".equals(searchMap.get("url"))) {
            criteria.andLike("url", "%" + (String) searchMap.get("url") + "%");
        }
        // 标签
        if (searchMap.get("label") != null && !"".equals(searchMap.get("label"))) {
            criteria.andEqualTo("label", (String) searchMap.get("label"));
        }
        // 职位描述
        if (searchMap.get("content1") != null && !"".equals(searchMap.get("content1"))) {
            criteria.andLike("content1", "%" + (String) searchMap.get("content1") + "%");
        }
        // 职位要求
        if (searchMap.get("content2") != null && !"".equals(searchMap.get("content2"))) {
            criteria.andLike("content2", "%" + (String) searchMap.get("content2") + "%");
        }
        // 办公地址
        if (searchMap.get("address") != null && !"".equals(searchMap.get("address"))) {
            criteria.orLike("address", "%" + (String) searchMap.get("address") + "%");
        }
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
        recruit.setCreatetime(new Date());
//        log.trace(recruit.toString());
        enterpriseMapper.addJobCount(recruit.getEid());
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
        Recruit recruit = recruitMapper.selectByPrimaryKey(id);
        recruitMapper.deleteByPrimaryKey(id);
        enterpriseMapper.cutJobCount(recruit.getEid());
    }

    //查询推荐职位，按创建时间排序的第一页的6个
    public List<Recruit> selectByState(String state, boolean isEquals) {
        Example example = new Example(Recruit.class);
        Example.Criteria criteria = example.createCriteria();
        if (isEquals) {
            criteria.andEqualTo("state", state);
        } else {
            criteria.andNotEqualTo("state", state);
        }
        example.setOrderByClause("createtime Desc");
        return recruitMapper.selectByExampleAndRowBounds(example, new RowBounds(0, 6));
    }
}
