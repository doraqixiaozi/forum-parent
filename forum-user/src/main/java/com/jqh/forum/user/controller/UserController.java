package com.jqh.forum.user.controller;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.jqh.forum.user.pojo.User;
import com.jqh.forum.user.service.UserService;

import entity.Result;
import entity.StatusCode;

import javax.servlet.http.HttpServletRequest;

/**
 * 控制器层
 * @author Administrator
 *
 */
@RestController
@CrossOrigin
@Slf4j
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserService userService;
	
	
	/**
	 * 查询全部数据
	 * @return
	 */
	@RequestMapping(method= RequestMethod.GET)
	public Result findAll(){
		return new Result(true,StatusCode.OK,"查询成功",userService.findAll());
	}
	
	/**
	 * 根据ID查询
	 * @param id ID
	 * @return
	 */
	@RequestMapping(value="/{id}",method= RequestMethod.GET)
	public Result findById(@PathVariable String id){
		return new Result(true,StatusCode.OK,"查询成功",userService.findById(id));
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
		return  new Result(true,StatusCode.OK,"查询成功",  userService.findSearch(searchMap, page, size) );
	}

	/**
     * 根据条件查询
     * @param searchMap
     * @return
     */
    @RequestMapping(value="/search",method = RequestMethod.POST)
    public Result findSearch( @RequestBody Map searchMap){
        return new Result(true,StatusCode.OK,"查询成功",userService.findSearch(searchMap));
    }
	
	/**
	 * 增加
	 * @param user
	 */
	@RequestMapping(method=RequestMethod.POST)
	public Result add(@RequestBody User user  ){
		userService.add(user);
		return new Result(true,StatusCode.OK,"增加成功");
	}
	
	/**
	 * 修改
	 * @param user
	 */
	@RequestMapping(value="/{id}",method= RequestMethod.PUT)
	public Result update(@RequestBody User user, @PathVariable String id ){
		user.setId(id);
		userService.update(user);		
		return new Result(true,StatusCode.OK,"修改成功");
	}
	
	/**
	 * 删除
	 * @param id
	 */
	@RequestMapping(value="/{id}",method= RequestMethod.DELETE)
	public Result delete(@PathVariable String id ){
		//需要从请求中看到有admin角色才可以删除
		userService.deleteById(id);
		return new Result(true,StatusCode.OK,"删除成功");
	}
	//发送短信验证码
	@PostMapping("/sendsms/{mobile}")
	public Result sendSms(@PathVariable String mobile){
		userService.sendSms(mobile);
		return new Result(true,StatusCode.OK,"发送成功");
	}

	//发送邮箱验证码
	@PostMapping("/sendemail/{email}")
	public Result sendEmail(@PathVariable String email){
		userService.sendEmail(email);
		return new Result(true,StatusCode.OK,"发送成功");
	}
	//验证码发送成功之后进行用户注册
	@PostMapping("/register/{code}")
	public Result register(@PathVariable String code,@RequestBody User user){
		//0.成功，1.未发送验证码，2.验证码错误
        int flag=userService.register(code,user);
        if (flag==1){
        	return new Result(false, StatusCode.ERROR, "请先获取注册验证码");
		}
		if (flag==2){
			return new Result(false, StatusCode.ERROR, "验证码错误");
		}
			return new Result(true, StatusCode.OK, "注册成功");
	}

	//使用手机验证码登录
	@PostMapping("/login/{code}")
	public Result loginByMobile(@PathVariable String code,@RequestBody User user) {
		//0.成功 1.自动注册并登录 2.未发送验证码 3.验证码错误
		Map map = userService.loginByMobile(code, user);
		int state = ((Integer)map.get("state"));
		if (state==2){
			return new Result(false, StatusCode.LOGINERROR, "请先获取登录验证码");
		}
		if (state==3){
			return new Result(false, StatusCode.LOGINERROR, "验证码错误");
		}
		HashMap<Object, Object> resultMap = new HashMap<>();
		resultMap.put("token",map.get("token"));
		resultMap.put("roles","user");
		return new Result(true, StatusCode.OK, "登录成功",resultMap);
	}

	//使用账号密码登录
	@PostMapping("/login")
	public Result loginByPassword(@RequestBody User user) {
		Map map = userService.loginByPassword(user);
		int state = ((Integer)map.get("state"));
		if (state==0){
			return new Result(false, StatusCode.LOGINERROR, "登录失败");
		}
		HashMap<Object, Object> resultMap = new HashMap<>();
		resultMap.put("token",map.get("token"));
		resultMap.put("roles","user");
		return new Result(true, StatusCode.OK, "登录成功",resultMap);
	}

	//更新粉丝和关注数
    @PutMapping("/{userid}/{friendid}/{num}")
	public Result updateFanscountAndFollowcount(@PathVariable String userid,@PathVariable String friendid,@PathVariable int num){
	    userService.updateFanscountAndFollowcount(num,userid,friendid);
	    return new Result(true, StatusCode.OK, "更新成功");
    }

}
