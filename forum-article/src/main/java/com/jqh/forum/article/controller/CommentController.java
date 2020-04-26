package com.jqh.forum.article.controller;

import com.jqh.forum.article.pojo.Comment;
import com.jqh.forum.article.service.CommentService;
import entity.Result;
import entity.StatusCode;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;

/**
 * CommentController
 *
 * @author 862965251@qq.com
 * @date 2020-04-20 15:56
 */
@RestController
@CrossOrigin
@RequestMapping("/comment")
public class CommentController {
    @Resource
    private CommentService commentService;
    @Resource
    private HttpServletRequest request;
    @GetMapping("/{articleId}/{page}/{size}")
    public Result getComments(@PathVariable String articleId,@PathVariable Integer page,@PathVariable Integer size){
        return new Result(true, StatusCode.OK,"查询成功",commentService.getComments(articleId,page,size));
    }
    @DeleteMapping("/{id}")
    public Result delete(@PathVariable String id){
        commentService.delete(id);
        return new Result(true, StatusCode.OK,"删除成功");
    }

    @PostMapping()
    public Result add(@RequestBody Comment comment){
        if (StringUtils.isEmpty(request.getHeader("Authorization"))){
            return new Result(false, StatusCode.ERROR,"请先登录");
        }
        commentService.add(comment);
        return new Result(true, StatusCode.OK,"增加成功");
    }

    /**
     * 批量文章中的用户信息
     */
    @PutMapping(value="/user")
    public Result updateUserData( @PathVariable HashMap userData){
        commentService.updateUserData(userData);
        return new Result(true,StatusCode.OK,"更新成功");
    }
}
