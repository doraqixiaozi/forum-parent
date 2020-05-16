package com.jqh.forum.gathering.service;

import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import entity.PageResult;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import tk.mybatis.mapper.entity.Example;
import util.CommonUtil;
import util.IdWorker;

import com.jqh.forum.gathering.mapper.GatheringMapper;
import com.jqh.forum.gathering.pojo.Gathering;

/**
 * 服务层
 * 
 * @author Administrator
 *
 */
@Service
@Slf4j
public class GatheringService {

	@Resource
	private GatheringMapper gatheringMapper;
	
	@Autowired
	private IdWorker idWorker;

	/**
	 * 查询全部列表
	 * @return
	 */
	public List<Gathering> findAll() {
		return gatheringMapper.selectAll();
	}

	
	/**
	 * 条件查询+分页
	 * @param whereMap
	 * @param page
	 * @param size
	 * @return
	 */
	public PageResult<Gathering> findSearch(Map whereMap, int page, int size) {
		Page<Gathering> gatherings = PageHelper.startPage(page, size,"starttime desc");
		List<Gathering> search = this.findSearch(whereMap);
		PageResult<Gathering> problemPageResult = new PageResult(gatherings.getTotal(), search);
		return problemPageResult;
	}

	
	/**
	 * 条件查询
	 * @param searchMap
	 * @return
	 */
	public List<Gathering> findSearch(Map searchMap) {
		Example example = new Example(Gathering.class);
		Example.Criteria criteria = example.createCriteria();
		// 编号
		if (searchMap.get("id")!=null && !"".equals(searchMap.get("id"))) {
			criteria.andEqualTo("id",(String)searchMap.get("id"));
		}
		// 活动名称
		if (searchMap.get("name")!=null && !"".equals(searchMap.get("name"))) {
			criteria.andLike("name","%"+(String)searchMap.get("name")+"%");
		}
		// 大会简介
		if (searchMap.get("summary")!=null && !"".equals(searchMap.get("summary"))) {
			criteria.andLike("summary","%"+(String)searchMap.get("summary")+"%");
		}
		// 详细说明
		if (searchMap.get("detail")!=null && !"".equals(searchMap.get("detail"))) {
			criteria.andLike("detail","%"+(String)searchMap.get("detail")+"%");
		}
		// 主办方
		if (searchMap.get("sponsor")!=null && !"".equals(searchMap.get("sponsor"))) {
			criteria.andLike("sponsor","%"+(String)searchMap.get("sponsor")+"%");
		}
		// 活动图片
		if (searchMap.get("image")!=null && !"".equals(searchMap.get("image"))) {
			criteria.andEqualTo("image",(String)searchMap.get("image"));
		}
		// 举办地点
		if (searchMap.get("address")!=null && !"".equals(searchMap.get("address"))) {
			criteria.andLike("address","%"+(String)searchMap.get("address")+"%");
		}
		// 是否可见
		if (searchMap.get("state")!=null && !"".equals(searchMap.get("state"))) {
			criteria.andEqualTo("state",(String)searchMap.get("state"));
		}
		// 城市
		if (searchMap.get("city")!=null && !"".equals(searchMap.get("city"))) {
			criteria.andLike("city","%"+(String)searchMap.get("city")+"%");
		}
		//开始结束时间
		if (StringUtils.isNotEmpty((String)searchMap.get("startTime"))) {
			Date startTime =null;
			try {
				startTime =CommonUtil.convertUtil((String) searchMap.get("startTime"));
			} catch (ParseException e) {
				log.error("日期转换失败");
				throw new RuntimeException(e);
			}
			criteria.andGreaterThanOrEqualTo("starttime",startTime);
		}
		if (StringUtils.isNotEmpty((String)searchMap.get("endTime"))) {
			Date endTime =null;
			try {
				endTime =CommonUtil.convertUtil((String) searchMap.get("endTime"));
			} catch (ParseException e) {
				log.error("日期转换失败");
				throw new RuntimeException(e);
			}
			criteria.andLessThanOrEqualTo("endtime",endTime);
		}
		return gatheringMapper.selectByExample(example);
	}

	/**
	 * 根据ID查询实体
	 * @param id
	 * @return
	 */
	@Cacheable(value = "gathering",key = "#id")
	public Gathering findById(String id) {
		return gatheringMapper.selectByPrimaryKey(id);
	}

	/**
	 * 增加
	 * @param gathering
	 */
	public void add(Gathering gathering) {
		gathering.setId( idWorker.nextId()+"" );
		gatheringMapper.insert(gathering);
	}

	/**
	 * 修改
	 * @param gathering
	 */
	//更新时清除原缓存
	@CacheEvict(value = "gathering",key = "#gathering.getId()")
	public void update(Gathering gathering) {
		gatheringMapper.updateByPrimaryKey(gathering);
	}

	/**
	 * 删除
	 * @param id
	 */
	@CacheEvict(value = "gathering",key = "#id")
	public void deleteById(String id) {
		gatheringMapper.deleteByPrimaryKey(id);
	}
}
