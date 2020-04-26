package com.jqh.forum.base.controller;

import com.jqh.forum.base.service.LabelService;
import com.jqh.forum.base.pojo.Label;
import entity.Result;
import entity.StatusCode;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Auther: 几米
 * @Date: 2019/5/5 19:01
 * @Description: LabelController
 */
@RestController
@CrossOrigin//跨域
@RequestMapping("/label")
public class LabelController {
    @Resource
    private LabelService labelService;

    @GetMapping()
    public Result findAll() {
        return new Result(true, StatusCode.OK, "查询成功", labelService.findAll());
    }

    @GetMapping("/{labelId}")
    public Result findById(@PathVariable("labelId") String labelId) {
        return new Result(true, StatusCode.OK, "查询成功", labelService.findById(labelId));
    }

    @PostMapping()
    public Result addLabel(@RequestBody Label label) {
        labelService.add(label);
        return new Result(true, StatusCode.OK, "添加成功");
    }

    @PutMapping("/{labelId}")
    public Result updateById(@PathVariable("labelId") String labelId, @RequestBody Label label) {
        label.setId(labelId);
        labelService.update(label);
        return new Result(true, StatusCode.OK, "修改成功");
    }

    @DeleteMapping("/{labelId}")
    public Result deleteById(@PathVariable("labelId") String labelId) {
        labelService.deleteById(labelId);
        return new Result(true, StatusCode.OK, "删除成功");
    }

    @PostMapping("/search/{page}/{size}")
    public Result findByCondition(@PathVariable("page") Integer page, @PathVariable("size") Integer size, @RequestBody Label label) {
        Map map = labelService.findByCondition(page, size,label);
        return new Result(true, StatusCode.OK, "查询成功", map);
    }
    @PostMapping("/search")
    public Result findByCondition(@RequestBody Label label) {
        List<Label> list= labelService.findByCondition(label);
        return new Result(true, StatusCode.OK, "查询成功", list);
    }

    @GetMapping("/labelMap")
    public Result getLabelMap() {
        List<Label> list= labelService.findAll();
        HashMap map = new HashMap<>();
        if ( list != null) {
            list.forEach(label -> {
                map.put(label.getId(), label.getLabelname());
            });
        }
        return new Result(true, StatusCode.OK, "查询成功", map);
    }
}
