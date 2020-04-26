package com.jqh.forum.article.controller;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.jqh.forum.article.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.jqh.forum.article.pojo.Article;

import entity.PageResult;
import entity.Result;
import entity.StatusCode;
/**
 * 控制器层
 * @author Administrator
 *
 */
@RestController
@CrossOrigin
@RequestMapping("/article")
public class ArticleController {

	@Autowired
	private ArticleService articleService;
	
	
	/**
	 * 查询全部数据
	 * @return
	 */
	@RequestMapping(method= RequestMethod.GET)
	public Result findAll(){
		return new Result(true,StatusCode.OK,"查询成功",articleService.findAll());
	}
	
	/**
	 * 根据ID查询
	 * @param id ID
	 * @return
	 */
	@RequestMapping(value="/{id}",method= RequestMethod.GET)
	public Result findById(@PathVariable String id){
		return new Result(true,StatusCode.OK,"查询成功",articleService.findById(id));
	}


	/**
	 * 分页+多条件查询
	 * @param searchMap 查询条件封装
	 * @param page 页码
	 * @param size 页大小
	 * @return 分页结果
	 */
	@RequestMapping(value="/search/{page}/{size}",method=RequestMethod.POST)
	public Result findSearch(@RequestBody Map searchMap , @PathVariable int page, @PathVariable int size){
		return  new Result(true,StatusCode.OK,"查询成功",  articleService.findSearch(searchMap,page,size) );
	}

	/**
     * 根据条件查询
     * @param searchMap
     * @return
     */
    @RequestMapping(value="/search",method = RequestMethod.POST)
    public Result findSearch( @RequestBody Map searchMap){
        return new Result(true,StatusCode.OK,"查询成功",articleService.findSearch(searchMap));
    }
	
	/**
	 * 增加
	 * @param article
	 */
	@RequestMapping(method=RequestMethod.POST)
	public Result add(@RequestBody Article article  ){
		articleService.add(article);
		return new Result(true,StatusCode.OK,"增加成功");
	}
	
	/**
	 * 修改
	 * @param article
	 */
	@RequestMapping(value="/{id}",method= RequestMethod.PUT)
	public Result update(@RequestBody Article article, @PathVariable String id ){
		article.setId(id);
		articleService.update(article);		
		return new Result(true,StatusCode.OK,"修改成功");
	}
	
	/**
	 * 删除
	 * @param id
	 */
	@RequestMapping(value="/{id}",method= RequestMethod.DELETE)
	public Result delete(@PathVariable String id ){
		articleService.deleteById(id);
		return new Result(true,StatusCode.OK,"删除成功");
	}
	@PutMapping("/thumbup/{articleId}")
	public Result thumbup(@PathVariable String articleId){
		articleService.addThumbup(articleId);
		return new Result(true,StatusCode.OK,"点赞成功");
	}
	@PutMapping("/examine/{articleId}")
	public Result examine(@PathVariable String articleId){
		articleService.updateState(articleId);
		return new Result(true,StatusCode.OK,"审核成功");
	}

	//根据频道ID获取文章列表
	@PostMapping("/channel/{channelId}/{page}/{size}")
	public Result channel(@PathVariable String channelId,@PathVariable int page,@PathVariable int size){
		return new Result(true,StatusCode.OK,"查询成功",articleService.channel(channelId,page,size));
	}

	//根据专栏ID获取文章列表
	@PostMapping("/column/{columnId}/{page}/{size}")
	public Result column(@PathVariable String columnId,@PathVariable int page,@PathVariable int size){
		return new Result(true,StatusCode.OK,"查询成功",articleService.column(columnId,page,size));
	}

	/**
	 * 获取未同步到es中的文章
	 * @param page
	 * @param size
	 * @return
	 */
	@GetMapping("/move/{page}/{size}")
	public Result<List<Article>> getUnMoveArticle(@PathVariable int page,@PathVariable int size){
		return new Result(true,StatusCode.OK,"查询成功",articleService.getUnMoveArticle(page,size));
	}

	/**
	 * 获取未同步到es中的文章数量
	 * @return
	 */
	@GetMapping("/move/num")
	public Result<Integer> getUnMoveArticleNum(){
		return new Result(true,StatusCode.OK,"查询成功",articleService.getUnMoveArticleNum());
	}

	/**
	 * 批量设置文章已被同步
	 * @return
	 */
	@PutMapping("/move")
	public Result hasMove(@RequestBody List<Article> articles){
		articleService.hasMove(articles);
		return new Result(true,StatusCode.OK,"设置同步状态成功",null);
	}

	/**
	 * 审核/下架文章
	 */
	@PutMapping(value="/{articleId}/{state}")
	public Result changeState( @PathVariable String articleId,@PathVariable String state){
		HashMap<Object, Object> searchMap = new HashMap<>();
		articleService.changeState(articleId,state);
		return new Result(true,StatusCode.OK,"修改成功");
	}

	/**
	 * 手动同步文章
	 */
	@PutMapping(value="/move/{articleId}")
	public Result move( @PathVariable String articleId){
		HashMap<Object, Object> searchMap = new HashMap<>();
		articleService.move(articleId);
		return new Result(true,StatusCode.OK,"同步成功");
	}

	/**
	 * 批量文章中的用户信息
	 */
	@PutMapping(value="/user")
	public Result updateUserData( @PathVariable HashMap userData){
		articleService.updateUserData(userData);
		return new Result(true,StatusCode.OK,"更新成功");
	}
}
