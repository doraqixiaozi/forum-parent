package com.jqh.forum.article.service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.jqh.forum.article.mapper.CommentMapper;
import com.jqh.forum.article.pojo.Comment;
import entity.PageResult;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;
import util.IdWorker;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * CommentService
 *
 * @author 862965251@qq.com
 * @date 2020-04-20 16:05
 */
@Service
public class CommentService {
    @Resource
    private CommentMapper commentMapper;
    @Resource
    private IdWorker idWorker;
    public PageResult<Comment> getComments(String articleId, Integer page, Integer size) {
        Example example = new Example(Comment.class);
        example.createCriteria().andEqualTo("articleid",articleId);
        Page<Comment> comments = PageHelper.startPage(page, size,"createtime desc");
        List<Comment> search = commentMapper.selectByExample(example);
        PageResult<Comment> PageResult = new PageResult<>(comments.getTotal(), search);
        return PageResult;
    }

    public void delete(String id) {
        commentMapper.deleteByPrimaryKey(id);
    }

    public void add(Comment comment) {
        comment.setCreatetime(new Date());
        comment.setId(idWorker.nextId()+"");
        commentMapper.insert(comment);
    }

    public void updateUserData(HashMap userData) {
    }
}
