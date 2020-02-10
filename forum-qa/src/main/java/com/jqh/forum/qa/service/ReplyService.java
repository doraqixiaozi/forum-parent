package com.jqh.forum.qa.service;

import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.jqh.forum.qa.mapper.ProblemMapper;
import com.jqh.forum.qa.mapper.ReplyMapper;
import com.jqh.forum.qa.pojo.Problem;
import entity.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;
import util.IdWorker;

import com.jqh.forum.qa.pojo.Reply;

/**
 * 服务层
 * 
 * @author Administrator
 *
 */
@Service
@Transactional
public class ReplyService {

	@Resource
	private ReplyMapper replyMapper;
	@Resource
	private ProblemMapper problemMapper;
	
	@Autowired
	private IdWorker idWorker;

	/**
	 * 查询全部列表
	 * @return
	 */
	public List<Reply> findAll() {
		return replyMapper.selectAll();
	}

	
	/**
	 * 条件查询+分页
	 * @param whereMap
	 * @param page
	 * @param size
	 * @return
	 */
	public PageResult<Reply> findSearch(Map whereMap, int page, int size) {
		Page<Reply> replys = PageHelper.startPage(page, size);
		List<Reply> search = this.findSearch(whereMap);
		PageResult<Reply> problemPageResult = new PageResult<>(replys.getTotal(),search);
		return problemPageResult;
	}

	
	/**
	 * 条件查询
	 * @param whereMap
	 * @return
	 */
	public List<Reply> findSearch(Map whereMap) {
		Example example = new Example(Problem.class);
		Example.Criteria criteria = example.createCriteria();
		// 编号
		if (whereMap.get("id")!=null && !"".equals(whereMap.get("id"))) {
			criteria.andLike("id","%"+(String)whereMap.get("id")+"%");
		}
		// 问题ID
		if (whereMap.get("problemid")!=null && !"".equals(whereMap.get("problemid"))) {
			criteria.andLike("problemid","%"+(String)whereMap.get("problemid")+"%");
		}
		// 回答内容
		if (whereMap.get("content")!=null && !"".equals(whereMap.get("content"))) {
			criteria.andLike("content","%"+(String)whereMap.get("content")+"%");
		}
		// 回答人ID
		if (whereMap.get("userid")!=null && !"".equals(whereMap.get("userid"))) {
			criteria.andLike("userid","%"+(String)whereMap.get("userid")+"%");
		}
		// 回答人昵称
		if (whereMap.get("nickname")!=null && !"".equals(whereMap.get("nickname"))) {
			criteria.andLike("nickname","%"+(String)whereMap.get("nickname")+"%");
		}
		return replyMapper.selectByExample(example);
	}

	/**
	 * 根据ID查询实体
	 * @param id
	 * @return
	 */
	public Reply findById(String id) {
		return replyMapper.selectByPrimaryKey(id);
	}

	/**
	 * 增加(顺带更新problem的回答数)
	 * @param reply
	 */
	public void add(Reply reply) {
		reply.setId( idWorker.nextId()+"" );
		replyMapper.insert(reply);
		Problem problem = problemMapper.selectByPrimaryKey(reply.getProblemid());
		problem.setUpdatetime(new Date());
		problem.setReply(problem.getReply()+1);
		problemMapper.updateByPrimaryKeySelective(problem);
	}

	/**
	 * 修改
	 * @param reply
	 */
	public void update(Reply reply) {
		replyMapper.updateByPrimaryKey(reply);
	}

	/**
	 * 删除
	 * @param id
	 */
	public void deleteById(String id) {
		replyMapper.deleteByPrimaryKey(id);
	}

}
