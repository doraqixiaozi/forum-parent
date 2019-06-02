package com.jqh.forum.spit.controller;

import com.jqh.forum.spit.pojo.Spit;
import com.jqh.forum.spit.service.SpitService;
import entity.PageResult;
import entity.Result;
import entity.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * @Auther: 几米
 * @Date: 2019/5/15 20:46
 * @Description: SpitController
 */
@RestController
@CrossOrigin
@RequestMapping("/spit")
public class SpitController {
    @Autowired
    private SpitService spitService;
    @Autowired
    RedisTemplate redisTemplate;//redis控制不能重复点赞

    @GetMapping
    public Result findAll() {
        return new Result(true, StatusCode.OK, "查询成功", spitService.findAll());
    }

    /**
     * 根据ID查询
     *
     * @param spitId
     * @return
     */
    @RequestMapping(value = "/{spitId}", method = RequestMethod.GET)
    public Result findById(@PathVariable String spitId) {
        return new Result(true, StatusCode.OK, "查询成功", spitService.findById(spitId));
    }


    /*  *
     * 分页+多条件查询
     * @param searchMap 查询条件封装
     * @param page 页码
     * @param size 页大小
     * @return 分页结果
     */
    @RequestMapping(value = "/comment/{parentid}/{page}/{size}", method = RequestMethod.GET)
    public Result findSearch(@PathVariable String parentid, @PathVariable int page, @PathVariable int size) {
        Page<Spit> spitPage = spitService.findByParentid(parentid, page, size);
        return new Result(true, StatusCode.OK, "查询成功", new PageResult<Spit>(spitPage.getTotalElements(), spitPage.getContent()));
    }

    /* *
     * 根据条件查询
     * @param searchMap
     * @return*/

    @RequestMapping(value = "/search", method = RequestMethod.POST)
    public Result findSearch(@RequestBody Spit spit) {
        return new Result(true, StatusCode.OK, "查询成功", spitService.search(spit));
    }

    @RequestMapping(value = "/search/{page}/{size}", method = RequestMethod.POST)
    public Result findSearch(@RequestBody Spit spit, @PathVariable int page, @PathVariable int size) {
        Page<Spit> search = spitService.search(spit, page, size);
        return new Result(true, StatusCode.OK, "查询成功", new PageResult<>(search.getTotalElements(), search.getContent()));
    }

    /**
     * 增加
     *
     * @param spit
     */
    @RequestMapping(method = RequestMethod.POST)
    public Result add(@RequestBody Spit spit) {
        spitService.add(spit);
        return new Result(true, StatusCode.OK, "增加成功");
    }

    /**
     * 修改
     *
     * @param spit
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public Result update(@RequestBody Spit spit, @PathVariable String id) {
        spit.set_id(id);
        spitService.update(spit);
        return new Result(true, StatusCode.OK, "修改成功");
    }

    /**
     * 删除
     *
     * @param id
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public Result delete(@PathVariable String id) {
        spitService.deleteById(id);
        return new Result(true, StatusCode.OK, "删除成功");
    }

    @PutMapping("/thumbup/{spitId}")
    public Result thumbup(@PathVariable String spitId) {
        //假设用户id为
        String userid="1111";
        Object o = redisTemplate.opsForValue().get("thumbup_" + userid + "_" + spitId);
        if (o!=null){
            return new Result(false, StatusCode.REPERROR, "重复操作");
        }
        spitService.thumbup(spitId);
        redisTemplate.opsForValue().set("thumbup_" + userid + "_" + spitId,1);
        return new Result(true, StatusCode.OK, "点赞成功");
    }
}
