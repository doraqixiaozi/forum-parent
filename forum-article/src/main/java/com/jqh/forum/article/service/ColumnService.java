package com.jqh.forum.article.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;


import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.jqh.forum.article.mapper.ColumnMapper;
import com.jqh.forum.article.pojo.Channel;
import entity.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;
import util.IdWorker;

import com.jqh.forum.article.pojo.Column;

import javax.annotation.Resource;

/**
 * 服务层
 * 
 * @author Administrator
 *
 */
@Service
@Transactional
public class ColumnService {

	@Resource
	private ColumnMapper columnMapper;
	
	@Autowired
	private IdWorker idWorker;

	/**
	 * 查询全部列表
	 * @return
	 */
	public List<Column> findAll() {
		return columnMapper.selectAll();
	}

	
	/**
	 * 条件查询+分页
	 * @param whereMap
	 * @param page
	 * @param size
	 * @return
	 */
	public PageResult<Column> findSearch(Map whereMap, int page, int size) {
		Page<Column> problems = PageHelper.startPage(page, size);
		List<Column> search = this.findSearch(whereMap);
		PageResult<Column> problemPageResult = new PageResult<>(problems.getTotal(), search);
		return problemPageResult;
	}

	
	/**
	 * 条件查询
	 * @param whereMap
	 * @return
	 */
	public List<Column> findSearch(Map whereMap) {
		Example example = new Example(Column.class);
		// ID
		if (whereMap.get("id")!=null && !"".equals(whereMap.get("id"))) {
			example.createCriteria().andLike("id","%"+(String)whereMap.get("id")+"%");
		}
		// 专栏名称
		if (whereMap.get("name")!=null && !"".equals(whereMap.get("name"))) {
			example.createCriteria().andLike("name","%"+(String)whereMap.get("name")+"%");
		}
		// 专栏简介
		if (whereMap.get("summary")!=null && !"".equals(whereMap.get("summary"))) {
			example.createCriteria().andLike("summary","%"+(String)whereMap.get("summary")+"%");
		}
		// 用户ID
		if (whereMap.get("userid")!=null && !"".equals(whereMap.get("userid"))) {
			example.createCriteria().andLike("userid","%"+(String)whereMap.get("userid")+"%");
		}
		// 状态
		if (whereMap.get("state")!=null && !"".equals(whereMap.get("state"))) {
			example.createCriteria().andLike("state","%"+(String)whereMap.get("state")+"%");
		}

		return columnMapper.selectByExample(example);
	}

	/**
	 * 根据ID查询实体
	 * @param id
	 * @return
	 */
	public Column findById(String id) {
		return columnMapper.selectByPrimaryKey(id);
	}

	/**
	 * 增加
	 * @param column
	 */
	public void add(Column column) {
		column.setId( idWorker.nextId()+"" );
		columnMapper.insert(column);
	}

	/**
	 * 修改
	 * @param column
	 */
	public void update(Column column) {
		columnMapper.updateByPrimaryKey(column);
	}

	/**
	 * 删除
	 * @param id
	 */
	public void deleteById(String id) {
		columnMapper.deleteByPrimaryKey(id);
	}


	public void examine(String columnId) {
		Column column = new Column();
		column.setId(columnId);
		column.setChecktime(new Date());
		column.setState("1");
		this.update(column);
	}


}
