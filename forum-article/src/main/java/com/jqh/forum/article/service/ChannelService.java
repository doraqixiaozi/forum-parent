package com.jqh.forum.article.service;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.jqh.forum.article.mapper.ChannelMapper;
import entity.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;
import util.IdWorker;

import com.jqh.forum.article.pojo.Channel;

/**
 * 服务层
 * 
 * @author Administrator
 *
 */
@Service
@Transactional
public class ChannelService {

	@Resource
	private ChannelMapper channelMapper;
	
	@Autowired
	private IdWorker idWorker;

	/**
	 * 查询全部列表
	 * @return
	 */
	public List<Channel> findAll() {
		return channelMapper.selectAll();
	}

	
	/**
	 * 条件查询+分页
	 * @param whereMap
	 * @param page
	 * @param size
	 * @return
	 */
	public PageResult<Channel> findSearch(Map whereMap, int page, int size) {
		Page<Channel> problems = PageHelper.startPage(page, size);
		List<Channel> search = this.findSearch(whereMap);
		PageResult<Channel> problemPageResult = new PageResult<>(problems.getTotal(), search);
		return problemPageResult;
	}

	
	/**
	 * 条件查询
	 * @param whereMap
	 * @return
	 */
	public List<Channel> findSearch(Map whereMap) {
		Example example = new Example(Channel.class);
		// ID
		if (whereMap.get("id")!=null && !"".equals(whereMap.get("id"))) {
			example.createCriteria().andLike("id","%"+(String)whereMap.get("id")+"%");
		}
		// 频道名称
		if (whereMap.get("name")!=null && !"".equals(whereMap.get("name"))) {
			example.createCriteria().andLike("name","%"+(String)whereMap.get("name")+"%");
		}
		// 状态
		if (whereMap.get("state")!=null && !"".equals(whereMap.get("state"))) {
			example.createCriteria().andLike("state","%"+(String)whereMap.get("state")+"%");
		}

		return channelMapper.selectByExample(example);
	}

	/**
	 * 根据ID查询实体
	 * @param id
	 * @return
	 */
	public Channel findById(String id) {
		return channelMapper.selectByPrimaryKey(id);
	}

	/**
	 * 增加
	 * @param channel
	 */
	public void add(Channel channel) {
		channel.setId( idWorker.nextId()+"" );
		channelMapper.insert(channel);
	}

	/**
	 * 修改
	 * @param channel
	 */
	public void update(Channel channel) {
		channelMapper.updateByPrimaryKey(channel);
	}

	/**
	 * 删除
	 * @param id
	 */
	public void deleteById(String id) {
		channelMapper.deleteByPrimaryKey(id);
	}


}
