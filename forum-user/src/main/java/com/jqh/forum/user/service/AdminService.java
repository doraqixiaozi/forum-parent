package com.jqh.forum.user.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.jqh.forum.user.mapper.AdminMapper;
import entity.PageResult;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;
import util.IdWorker;

import com.jqh.forum.user.pojo.Admin;
import util.JwtUtil;

/**
 * 服务层
 *
 * @author Administrator
 */
@Transactional
@Service
public class AdminService {
    @Autowired
    private BCryptPasswordEncoder encoder;
    @Resource
    private AdminMapper adminMapper;

    @Autowired
    private IdWorker idWorker;
    @Resource
    private RedisTemplate redisTemplate;

    /**
     * 查询全部列表
     *
     * @return
     */
    public List<Admin> findAll() {
        return adminMapper.selectAll();
    }


    /**
     * 条件查询+分页
     *
     * @param whereMap
     * @param page
     * @param size
     * @return
     */
    public PageResult<Admin> findSearch(Map whereMap, int page, int size) {
        Page<Admin> admins = PageHelper.startPage(page, size);
        List<Admin> search = this.findSearch(whereMap);
        PageResult<Admin> pageResult = new PageResult<>(admins.getTotal(), search);
        return pageResult;
    }


    /**
     * 条件查询
     *
     * @param whereMap
     * @return
     */
    public List<Admin> findSearch(Map whereMap) {
        Example example = new Example(Admin.class);
        Example.Criteria criteria = example.createCriteria();
        // ID
        if (whereMap.get("id") != null && !"".equals(whereMap.get("id"))) {
            criteria.andLike("id", "%" + (String) whereMap.get("id") + "%");
        }
        // 登陆名称
        if (whereMap.get("loginname") != null && !"".equals(whereMap.get("loginname"))) {
            criteria.andLike("loginname", "%" + (String) whereMap.get("loginname") + "%");
        }
        // 密码
        if (whereMap.get("password") != null && !"".equals(whereMap.get("password"))) {
            criteria.andLike("password", "%" + (String) whereMap.get("password") + "%");
        }
        // 状态
        if (whereMap.get("state") != null && !"".equals(whereMap.get("state"))) {
            criteria.andLike("state", "%" + (String) whereMap.get("state") + "%");
        }
        return adminMapper.selectByExample(example);
    }

    /**
     * 根据ID查询实体
     *
     * @param id
     * @return
     */
    public Admin findById(String id) {
        return adminMapper.selectByPrimaryKey(id);
    }

    /**
     * 增加
     *
     * @param admin
     */
    public void add(Admin admin) {
        admin.setId(idWorker.nextId() + "");
        //利用springsecurity的工具类将密码加密
        admin.setPassword(encoder.encode(admin.getPassword()));
        adminMapper.insert(admin);
    }

    /**
     * 修改
     *
     * @param admin
     */
    public void update(Admin admin) {
        adminMapper.updateByPrimaryKey(admin);
    }

    /**
     * 删除
     *
     * @param id
     */
    public void deleteById(String id) {
        adminMapper.deleteByPrimaryKey(id);
    }

    public Admin login(Admin admin) {
        Example example = new Example(Admin.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("loginname", admin.getLoginname());
        Admin DB_admin = adminMapper.selectOneByExample(example);
        if (DB_admin != null && encoder.matches(admin.getPassword(), DB_admin.getPassword())) {
            return DB_admin;
        }
        return null;
    }

    public Map<String, Object> getInfo(Claims claims) {
        HashMap<String, Object> infoMap = new HashMap<>();
        String id = claims.getId();
        Admin admin = (Admin) redisTemplate.opsForValue().get("admin_info_" + id);
        if (admin==null){
            admin = adminMapper.selectByPrimaryKey(id);
            redisTemplate.opsForValue().set("admin_info_" + id,admin,3, TimeUnit.DAYS);
        }
        infoMap.put("avatar","https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif");
        infoMap.put("name", admin.getLoginname());
        ArrayList<Object> roles = new ArrayList<>();
        roles.add("admin");
        infoMap.put("roles", roles);
        return infoMap;
    }
}
