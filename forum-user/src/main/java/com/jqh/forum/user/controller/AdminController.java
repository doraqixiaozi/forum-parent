package com.jqh.forum.user.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.jqh.forum.user.pojo.Admin;
import com.jqh.forum.user.service.AdminService;

import entity.Result;
import entity.StatusCode;
import util.JwtUtil;

import javax.servlet.http.HttpServletRequest;

/**
 * 控制器层
 *
 * @author Administrator
 */
@RestController
@CrossOrigin
@RequestMapping("/admin")
@Slf4j
public class AdminController {

    @Autowired
    private AdminService adminService;
    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private HttpServletRequest request;

    /**
     * 查询全部数据
     *
     * @return
     */
    @RequestMapping(method = RequestMethod.GET)
    public Result findAll() {
        return new Result(true, StatusCode.OK, "查询成功", adminService.findAll());
    }

    /**
     * 根据ID查询
     *
     * @param id ID
     * @return
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Result findById(@PathVariable String id) {
        return new Result(true, StatusCode.OK, "查询成功", adminService.findById(id));
    }


    /**
     * 分页+多条件查询
     *
     * @param searchMap 查询条件封装
     * @param page      页码
     * @param size      页大小
     * @return 分页结果
     */
    @RequestMapping(value = "/search/{page}/{size}", method = RequestMethod.POST)
    public Result findSearch(@RequestBody Map searchMap, @PathVariable int page, @PathVariable int size) {
        return new Result(true, StatusCode.OK, "查询成功", adminService.findSearch(searchMap, page, size));
    }

    /**
     * 根据条件查询
     *
     * @param searchMap
     * @return
     */
    @RequestMapping(value = "/search", method = RequestMethod.POST)
    public Result findSearch(@RequestBody Map searchMap) {
        return new Result(true, StatusCode.OK, "查询成功", adminService.findSearch(searchMap));
    }

    /**
     * 增加
     *
     * @param admin
     */
    @RequestMapping(method = RequestMethod.POST)
    public Result add(@RequestBody Admin admin) {
        adminService.add(admin);
        return new Result(true, StatusCode.OK, "增加成功");
    }

    /**
     * 修改
     *
     * @param admin
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public Result update(@RequestBody Admin admin, @PathVariable String id) {
        admin.setId(id);
        adminService.update(admin);
        return new Result(true, StatusCode.OK, "修改成功");
    }

    /**
     * 删除
     *
     * @param id
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public Result delete(@PathVariable String id) {
        adminService.deleteById(id);
        return new Result(true, StatusCode.OK, "删除成功");
    }

    @PostMapping("/login")
    public Result login(@RequestBody Admin admin) {
        admin = adminService.login(admin);
        if (admin == null) {
            return new Result(false, StatusCode.LOGINERROR, "登录失败");
        }
        //这里应该用户角色权限三表联查拿到权限的，但是这里简化写死了
        String token = jwtUtil.createJWT(admin.getId(), admin.getLoginname(), "admin","管理员");
        Map map = new HashMap();
        map.put("token", token);
        map.put("roles", "admin");
        return new Result(true, StatusCode.OK, "登录成功", map);
    }

    @GetMapping("/testLogin")
    public Result testLogin() {
        Claims claims_admin = jwtUtil.parseJWT((String) request.getAttribute("claims_admin"));
        HashMap dataMap = new HashMap<>();
        dataMap.put("roles",claims_admin.get("roles"));
        dataMap.put("nickName",claims_admin.get("nickName"));
        dataMap.put("id",claims_admin.getId());
        return new Result(true, StatusCode.OK, "测试成功", dataMap);
    }

    @PostMapping("/logout")
    public Result logout() {
//       String token= (String) request.getAttribute("claims_admin");
//       if (StringUtils.isBlank(token)){
//           return new Result(false, StatusCode.ACCESSERROR, "请先登录");
//       }
//        HashMap<String, String> objectObjectHashMap = new HashMap<>();
//        objectObjectHashMap.put("token", token);
//        return new Result(true, StatusCode.OK, "退出成功", objectObjectHashMap);
        return new Result(true, StatusCode.OK, "退出成功");
    }

    @GetMapping("/info")
    public Result getInfo() {
        String token = (String) request.getAttribute("claims_admin");
        if (StringUtils.isBlank(token)) {
            return new Result(false, StatusCode.ACCESSERROR, "请先登录");
        }
        Claims claims = jwtUtil.parseJWT(token);
        Map<String,Object> map=adminService.getInfo(claims);
        return new Result(true, StatusCode.OK, "验证成功", map);
    }
}
