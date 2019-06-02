package com.jqh.forum.base.service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.jqh.forum.base.mapper.LabelMapper;
import com.jqh.forum.base.pojo.Label;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.util.StringUtil;
import util.IdWorker;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Auther: 几米
 * @Date: 2019/5/5 19:48
 * @Description: LabelService
 */
@Slf4j
@Service
@Transactional
public class LabelService {
    @Resource
    private LabelMapper labelMapper;
    @Resource
    private IdWorker idWorker;

    public List<Label> findAll() {
        return labelMapper.selectAll();
    }

    public Label findById(String id) {
        return labelMapper.selectByPrimaryKey(id);
    }

    public void add(Label label) {
        label.setId(idWorker.nextId() + "");
        labelMapper.insert(label);
    }

    public void update(Label label) {
        labelMapper.updateByPrimaryKey(label);
    }

    public void deleteById(String id) {
        labelMapper.deleteByPrimaryKey(id);
    }

    public Map findByCondition(Integer page, Integer size,Label label) {
        Page<Label> labelPage = PageHelper.startPage(page == null ? 0 : page, size == null ? 10 : size);
        List<Label> labels = this.findByCondition(label);
        HashMap hashMap = new HashMap();
        hashMap.put("total",labelPage.getTotal());
        hashMap.put("rows",labels);
        log.trace(hashMap.toString());
        return hashMap;
    }

    public List<Label> findByCondition(Label label) {
        Example example = new Example(Label.class);
//        example.createCriteria().andEqualTo(label);

        if (StringUtil.isNotEmpty(label.getId())){
            example.createCriteria().andEqualTo("id",label.getId());
        }
        if (StringUtil.isNotEmpty(label.getLabelname())){
            example.createCriteria().andLike("labelname","%"+label.getLabelname()+"%");
        }
        if (StringUtil.isNotEmpty(label.getState())){
            example.createCriteria().andEqualTo("state",label.getState());
        }
        System.out.println(example.getOredCriteria());
        List<Label> list = labelMapper.selectByExample(example);
        return list;
    }
}