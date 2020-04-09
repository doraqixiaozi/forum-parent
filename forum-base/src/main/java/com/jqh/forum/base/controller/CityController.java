package com.jqh.forum.base.controller;

import com.jqh.forum.base.pojo.City;
import com.jqh.forum.base.service.CityService;
import entity.Result;
import entity.StatusCode;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * CityController
 *
 * @author 862965251@qq.com
 * @date 2020-04-07 18:34
 */
@RestController
@CrossOrigin//跨域
@RequestMapping("/city")
public class CityController {
    @Resource
    private CityService cityService;

    @GetMapping()
    public Result list() {
        return new Result(true, StatusCode.OK, "查询成功", cityService.findAll());
    }

    @GetMapping("/{cityId}")
    public Result findById(@PathVariable("cityId") String cityId) {
        return new Result(true, StatusCode.OK, "查询成功", cityService.findById(cityId));
    }

    @PostMapping()
    public Result addLabel(@RequestBody City city) {
        if(city==null||city.getName()==null){
            return new Result(false, StatusCode.ERROR, "添加失败，城市名不能为空");
        }
        if (city.getIshot()==null){
            city.setIshot("0");
        }
        cityService.add(city);
        return new Result(true, StatusCode.OK, "添加成功");
    }

    @PutMapping("/{cityId}")
    public Result updateById(@PathVariable("cityId") String cityId, @RequestBody City city) {
        city.setId(cityId);
        cityService.update(city);
        return new Result(true, StatusCode.OK, "修改成功");
    }

    @DeleteMapping("/{cityId}")
    public Result deleteById(@PathVariable("cityId") String cityId) {
        cityService.deleteById(cityId);
        return new Result(true, StatusCode.OK, "删除成功");
    }

    @PostMapping("/search/{page}/{size}")
    public Result findByCondition(@PathVariable("page") Integer page, @PathVariable("size") Integer size, @RequestBody City city) {
        Map map = cityService.findByCondition(page, size, city);
        return new Result(true, StatusCode.OK, "查询成功", map);
    }

    @PostMapping("/search")
    public Result findByCondition(@RequestBody City city) {
        List<City> list = cityService.findByCondition(city);
        return new Result(true, StatusCode.OK, "查询成功", list);
    }
}
