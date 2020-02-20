package com.jqh.forum.gathering.service;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import entity.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import tk.mybatis.mapper.entity.Example;
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
		Page<Gathering> problems = PageHelper.startPage(page, size);
		List<Gathering> search = this.findSearch(whereMap);
		PageResult<Gathering> problemPageResult = new PageResult<>(problems.getTotal(), search);
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
			criteria.andLike("id","%"+(String)searchMap.get("id")+"%");
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
			criteria.andLike("image","%"+(String)searchMap.get("image")+"%");
		}
		// 举办地点
		if (searchMap.get("address")!=null && !"".equals(searchMap.get("address"))) {
			criteria.andLike("address","%"+(String)searchMap.get("address")+"%");
		}
		// 是否可见
		if (searchMap.get("state")!=null && !"".equals(searchMap.get("state"))) {
			criteria.andLike("state","%"+(String)searchMap.get("state")+"%");
		}
		// 城市
		if (searchMap.get("city")!=null && !"".equals(searchMap.get("city"))) {
			criteria.andLike("city","%"+(String)searchMap.get("city")+"%");
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
