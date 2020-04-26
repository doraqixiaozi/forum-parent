package com.jqh.forum.search.controller;

import com.jqh.forum.search.pojo.Article;
import com.jqh.forum.search.pojo.ArticleES;
import com.jqh.forum.search.service.ArticleService;
import com.jqh.forum.search.util.SearchUtil;
import entity.PageResult;
import entity.Result;
import entity.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * @Auther: 几米
 * @Date: 2019/5/22 18:46
 * @Description: ArticleController
 */
@RestController
@CrossOrigin
@RequestMapping("/articleES")
public class ArticleController {
    @Autowired
    private ArticleService articleService;

    @PostMapping()
    public Result add(@RequestBody Article article) {
        articleService.add(SearchUtil.article2ES(article));
        return new Result(true, StatusCode.OK, "增加成功");
    }

    /**
     * 查询
     *
     * @param param
     * @param page
     * @param size
     * @return
     */
    @PutMapping("/{page}/{size}")
    public Result searchByKey(@RequestBody Map<String, String> param, @PathVariable int page, @PathVariable int size) {
        Page<ArticleES> pageInfromation = articleService.searchByKey(param.get("key"), page, size);
        return new Result(true, StatusCode.OK, "查询成功", new PageResult(pageInfromation.getTotalElements(), pageInfromation.getContent()));
    }

    @DeleteMapping("/{id}")
    public Result deleteByKey(@PathVariable String id) {
        articleService.deleteById(id);
        return new Result(true, StatusCode.OK, "删除成功");
    }

    @GetMapping("")
    public Result list() {
        return new Result(true, StatusCode.OK, "查询成功");
    }
}
