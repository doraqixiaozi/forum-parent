package com.jqh.forum.article.controller;
import java.util.HashMap;
import java.util.Map;

import com.jqh.forum.article.service.ColumnService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.jqh.forum.article.pojo.Column;

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
@RequestMapping("/column")
public class ColumnController {

	@Autowired
	private ColumnService columnService;
	
	
	/**
	 * 查询全部数据
	 * @return
	 */
	@RequestMapping(method= RequestMethod.GET)
	public Result findAll(){
		return new Result(true,StatusCode.OK,"查询成功",columnService.findAll());
	}
	
	/**
	 * 根据ID查询
	 * @param id ID
	 * @return
	 */
	@RequestMapping(value="/{id}",method= RequestMethod.GET)
	public Result findById(@PathVariable String id){
		return new Result(true,StatusCode.OK,"查询成功",columnService.findById(id));
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
		return  new Result(true,StatusCode.OK,"查询成功",  columnService.findSearch(searchMap, page, size) );
	}

	/**
     * 根据条件查询
     * @param searchMap
     * @return
     */
    @RequestMapping(value="/search",method = RequestMethod.POST)
    public Result findSearch( @RequestBody Map searchMap){
        return new Result(true,StatusCode.OK,"查询成功",columnService.findSearch(searchMap));
    }
	
	/**
	 * 增加
	 * @param column
	 */
	@RequestMapping(method=RequestMethod.POST)
	public Result add(@RequestBody Column column  ){
		columnService.add(column);
		return new Result(true,StatusCode.OK,"增加成功");
	}

	/**
	 * 专栏申请
	 * @param column
	 */
	@RequestMapping(value = "/apply",method=RequestMethod.POST)
	public Result apply(@RequestBody Column column  ){
		columnService.add(column);
		return new Result(true,StatusCode.OK,"增加成功");
	}
	
	/**
	 * 修改
	 * @param column
	 */
	@RequestMapping(value="/{id}",method= RequestMethod.PUT)
	public Result update(@RequestBody Column column, @PathVariable String id ){
		column.setId(id);
		columnService.update(column);
		return new Result(true,StatusCode.OK,"修改成功");
	}
	/**
	 * 申请审核
	 * @param columnId
	 */
	@RequestMapping(value="/examine/{columnId}",method= RequestMethod.PUT)
	public Result examine( @PathVariable String columnId ){
		columnService.examine(columnId);
		return new Result(true,StatusCode.OK,"审核成功");
	}
	
	/**
	 * 删除
	 * @param id
	 */
	@RequestMapping(value="/{id}",method= RequestMethod.DELETE)
	public Result delete(@PathVariable String id ){
		columnService.deleteById(id);
		return new Result(true,StatusCode.OK,"删除成功");
	}

	/**
	 * 根据userId查询
	 * @param userId
	 * @return
	 */
	@GetMapping(value="/user/{userId}")
	public Result findSearch( @PathVariable String userId){
		HashMap<Object, Object> searchMap = new HashMap<>();
		searchMap.put("userId",userId);
		return new Result(true,StatusCode.OK,"查询成功",columnService.findSearch(searchMap));
	}

	/**
	 * 审核/封禁专栏
	 */
	@PutMapping(value="/{columnId}/{state}")
	public Result changeState( @PathVariable String columnId,@PathVariable String state){
		HashMap<Object, Object> searchMap = new HashMap<>();
		columnService.changeState(columnId,state);
		return new Result(true,StatusCode.OK,"修改成功");
	}
}
