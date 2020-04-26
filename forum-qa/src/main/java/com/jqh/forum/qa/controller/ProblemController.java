package com.jqh.forum.qa.controller;

import java.util.List;
import java.util.Map;

import com.jqh.forum.qa.client.BaseClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.jqh.forum.qa.pojo.Problem;
import com.jqh.forum.qa.service.ProblemService;

import entity.PageResult;
import entity.Result;
import entity.StatusCode;

import javax.annotation.Resource;

/**
 * 控制器层
 *
 * @author Administrator
 */
@RestController
@CrossOrigin
@RequestMapping("/problem")
public class ProblemController {

    @Autowired
    private ProblemService problemService;
    @Resource
    private BaseClient baseClient;
    @GetMapping("/label")
    public Result findAllBase(){
        return baseClient.findAll();
    }

    /**
     * 查询全部数据
     *
     * @return
     */
    @RequestMapping(method = RequestMethod.GET)
    public Result findAll() {
        return new Result(true, StatusCode.OK, "查询成功", problemService.findAll());
    }

    /**
     * 根据ID查询
     *
     * @param id ID
     * @return
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Result findById(@PathVariable String id) {
        return new Result(true, StatusCode.OK, "查询成功", problemService.findById(id));
    }


    /**
     * 分页+多条件查询，,按照创建时间排序
     *
     * @param searchMap 查询条件封装
     * @param page      页码
     * @param size      页大小
     * @return 分页结果
     */
    @RequestMapping(value = "/search/{page}/{size}", method = RequestMethod.POST)
    public Result findSearch(@RequestBody Map searchMap, @PathVariable int page, @PathVariable int size) {
        return new Result(true, StatusCode.OK, "查询成功", problemService.findSearch(searchMap, page, size));
    }

    /**
     * 分页+多条件查询,按照回复数排序
     *
     * @param searchMap 查询条件封装
     * @param page      页码
     * @param size      页大小
     * @return 分页结果
     */
    @RequestMapping(value = "/search/hot/{page}/{size}", method = RequestMethod.POST)
    public Result findSearchOrderByHot(@RequestBody Map searchMap, @PathVariable int page, @PathVariable int size) {
        return new Result(true, StatusCode.OK, "查询成功", problemService.findSearchOrderByHot(searchMap, page, size));
    }

    /**
     * 分页+多条件查询,按照最新回复时间排序
     *
     * @param searchMap 查询条件封装
     * @param page      页码
     * @param size      页大小
     * @return 分页结果
     */
    @RequestMapping(value = "/search/new/{page}/{size}", method = RequestMethod.POST)
    public Result findSearchOrderByNewReply(@RequestBody Map searchMap, @PathVariable int page, @PathVariable int size) {
        return new Result(true, StatusCode.OK, "查询成功", problemService.findSearchOrderByNewReply(searchMap, page, size));
    }

    /**
     * 根据条件查询
     *
     * @param searchMap
     * @return
     */
    @RequestMapping(value = "/search", method = RequestMethod.POST)
    public Result findSearch(@RequestBody Map searchMap) {
        return new Result(true, StatusCode.OK, "查询成功", problemService.findSearch(searchMap));
    }

    /**
     * 增加
     *
     * @param problem
     */
    @RequestMapping(method = RequestMethod.POST)
    public Result add(@RequestBody Problem problem) {
        problemService.add(problem);
        return new Result(true, StatusCode.OK, "增加成功");
    }

    /**
     * 修改
     *
     * @param problem
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public Result update(@RequestBody Problem problem, @PathVariable String id) {
        problem.setId(id);
        problemService.update(problem);
        return new Result(true, StatusCode.OK, "修改成功");
    }

    /**
     * 删除
     *
     * @param id
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public Result delete(@PathVariable String id) {
        problemService.deleteById(id);
        return new Result(true, StatusCode.OK, "删除成功");
    }

    /**
     * 最新问题
     * @param lableid
     * @param page
     * @param size
     * @return
     */
    @GetMapping("/newlist/{labelid}/{page}/{size}")
    public Result newlist(@PathVariable("labelid") String lableid, @PathVariable("page") int page, @PathVariable("size") int size) {
        return new Result(true, StatusCode.OK, "查询成功", problemService.newlist(lableid, page, size));
    }
    /**
     * 最热问题
     * @param lableid
     * @param page
     * @param size
     * @return
     */
    @GetMapping("/hotlist/{labelid}/{page}/{size}")
    public Result hotlist(@PathVariable("labelid") String lableid, @PathVariable("page") int page, @PathVariable("size") int size) {
        return new Result(true, StatusCode.OK, "查询成功", problemService.hotlist(lableid, page, size));
    }
    /**
     * 待回答问题
     * @param lableid
     * @param page
     * @param size
     * @return
     */
    @GetMapping("/waitlist/{labelid}/{page}/{size}")
    public Result waitlist(@PathVariable("labelid") String lableid, @PathVariable("page") int page, @PathVariable("size") int size) {
        return new Result(true, StatusCode.OK, "查询成功", problemService.waitlist(lableid, page, size));
    }

    /**
     * 设置问题为已解决（设置某个回答为正确回答）
     * @param problemId
     * @param replyId
     * @return
     */
    @PutMapping("/solve/{problemId}/{replyId}")
    public Result solve(@PathVariable("problemId") String problemId,@PathVariable("replyId") String replyId) {
        problemService.solve(problemId, replyId);
        return new Result(true, StatusCode.OK, "设置问题解决成功" );
    }

    /**
     * 点赞
     * @param problemId
     * @return
     */
    @PutMapping("/thumbup/{problemId}")
    public Result thumbup(@PathVariable String problemId){
        problemService.addThumbup(problemId);
        return new Result(true,StatusCode.OK,"点赞成功");
    }
}
