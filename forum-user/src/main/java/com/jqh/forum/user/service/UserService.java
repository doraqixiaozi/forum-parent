package com.jqh.forum.user.service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.jqh.forum.user.client.ArticleClient;
import com.jqh.forum.user.mapper.UserMapper;
import entity.PageResult;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;
import util.IdWorker;

import com.jqh.forum.user.pojo.User;
import util.JwtUtil;

/**
 * 服务层
 *
 * @author Administrator
 */
@Slf4j
@Transactional
@Service
public class UserService {
    @Resource
    private ArticleClient articleClient;
    @Autowired
    private BCryptPasswordEncoder encoder;
    @Resource
    private UserMapper userMapper;

    @Autowired
    private IdWorker idWorker;
    @Resource
    private RabbitTemplate rabbitTemplate;
    @Resource
    private RedisTemplate redisTemplate;
    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private HttpServletRequest request;
    private static String checkCodeKey = "checkCode:::";

    /**
     * 查询全部列表
     *
     * @return
     */
    public List<User> findAll() {
        return userMapper.selectAll();
    }


    /**
     * 条件查询+分页
     *
     * @param whereMap
     * @param page
     * @param size
     * @return
     */
    public PageResult<User> findSearch(Map whereMap, int page, int size) {
        Page<User> users = PageHelper.startPage(page, size);
        List<User> search = this.findSearch(whereMap).stream().
                map(user -> {
                    user.setPassword("");
                    return user;
                }).
                collect(Collectors.toList());

        PageResult<User> pageResult = new PageResult<>(users.getTotal(), search);
        return pageResult;
    }


    /**
     * 条件查询
     *
     * @param searchMap
     * @return
     */
    public List<User> findSearch(Map searchMap) {
        Example example = new Example(User.class);
        Example.Criteria criteria = example.createCriteria();
        // 登陆名称
        if (searchMap.get("loginname") != null && !"".equals(searchMap.get("loginname"))) {
            criteria.andLike("loginname", "%" + (String) searchMap.get("loginname") + "%");
        }
        // 手机号
        if (searchMap.get("mobile") != null && !"".equals(searchMap.get("mobile"))) {
            criteria.andLike("mobile", "%" + (String) searchMap.get("mobile") + "%");
        }
        // 昵称
        if (searchMap.get("nickname") != null && !"".equals(searchMap.get("nickname"))) {
            criteria.andLike("nickname", "%" + (String) searchMap.get("nickname") + "%");
        }
        // 性别
        if (searchMap.get("sex") != null && !"".equals(searchMap.get("sex"))) {
            criteria.andLike("sex", "%" + (String) searchMap.get("sex") + "%");
        }
        // 头像
        if (searchMap.get("avatar") != null && !"".equals(searchMap.get("avatar"))) {
            criteria.andLike("avatar", "%" + (String) searchMap.get("avatar") + "%");
        }
        // E-Mail
        if (searchMap.get("email") != null && !"".equals(searchMap.get("email"))) {
            criteria.andLike("email", "%" + (String) searchMap.get("email") + "%");
        }
        // 兴趣
        if (searchMap.get("interest") != null && !"".equals(searchMap.get("interest"))) {
            criteria.andLike("interest", "%" + (String) searchMap.get("interest") + "%");
        }
        // 个性
        if (searchMap.get("personality") != null && !"".equals(searchMap.get("personality"))) {
            criteria.andLike("personality", "%" + (String) searchMap.get("personality") + "%");
        }
        // 登陆名称
        if (searchMap.get("state") != null && !"".equals(searchMap.get("state"))) {
            criteria.andEqualTo("state", (String) searchMap.get("state"));
        }
        return userMapper.selectByExample(example);
    }

    /**
     * 根据ID查询实体
     *
     * @param id
     * @return
     */
    public User findById(String id) {
        User user = userMapper.selectByPrimaryKey(id);
        if(user!=null) {
            user.setPassword(null);
        }
        return user;
    }

    /**
     * 增加
     *
     * @param user
     */
    public void add(User user) throws DuplicateKeyException {
        user.setId(idWorker.nextId() + "");
        if (StringUtils.isBlank(user.getPassword())) {
//            如果没有密码则生成一个随机初始密码
            String random = RandomStringUtils.randomAlphanumeric(6);
            log.trace("用户email:" + user.getEmail() + ",mobile:" + user.getMobile() + ",密码:" + random);
            user.setPassword(random);
        }
        user.setPassword(encoder.encode(user.getPassword()));//密码加密
        user.setId(idWorker.nextId() + "");
        user.setFollowcount(0);//关注数
        user.setFanscount(0);//粉丝数
        user.setOnline(0L);//在线时长
        user.setRegdate(new Date());//注册日期
        user.setUpdatedate(new Date());//更新日期
        user.setLastdate(new Date());//最后登陆日期
        userMapper.insert(user);
    }

    /**
     * 修改,更新时需同步更新article表中的冗余数据
     *
     * @param user
     */
    public void update(User user) {
        log.trace(user.toString());
        HashMap map = new HashMap();
        map.put("nickname",user.getNickname());
        map.put("userid",user.getId());
        map.put("avatar",user.getAvatar());
        articleClient.updateUserData(map);
        userMapper.updateByPrimaryKeySelective(user);
    }

    /**
     * 删除
     *
     * @param id
     */
    public void deleteById(String id) {
//        String authorization = request.getHeader("Authorization");
//        //这里是内部的约定需要以Bearer+空格开头
//        if (StringUtils.isBlank(authorization) || !authorization.startsWith("Bearer ")) {
//            throw new RuntimeException("权限不足!");
//        }
//        //开始验证token
//        String token = authorization.substring(7);
//        try {
//            Claims claims = jwtUtil.parseJWT(token);
//            String roles = (String) claims.get("roles");
//            if (roles == null || !roles.equals("admin")) {
//                throw new RuntimeException("权限不足!");
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//            throw new RuntimeException("登录过期!");
//        }
//        上面的复杂操作被拦截器做了，这里只需简单的存取
        String token = (String) request.getAttribute("claims_admin");
        if (StringUtils.isBlank(token)) {
            throw new RuntimeException("权限不足");
        }
        userMapper.deleteByPrimaryKey(id);
    }

    /**
     * 发送短信验证码
     *
     * @param mobile
     */
    public void sendSms(String mobile) {
        //由apache的lang3工具类生成六位验证码
        String checkCode = RandomStringUtils.randomNumeric(6);
        //向缓存中添加以便验证
        redisTemplate.opsForValue().set(checkCodeKey + mobile, checkCode, 30, TimeUnit.MINUTES);
        //向用户短信发送
        HashMap map = new HashMap<>();
        map.put("mobile", mobile);
        map.put("checkCode", checkCode);
        rabbitTemplate.convertAndSend("forum", "sms", map);
        //用作检查
        log.trace("验证码为:" + checkCode);
    }

    public int register(String code, User user) {
        String redisCheckCode = (String) redisTemplate.opsForValue().get(checkCodeKey + user.getMobile());
        //如果redis缓存中还未存入此用户手机号所对应的验证码,则说明并未进行验证
        log.trace(redisCheckCode);
        log.trace(code);
        log.trace(user.toString());
        if (StringUtils.isBlank(redisCheckCode)) {
//            如果手机验证码为空则尝试邮箱验证码
            redisCheckCode = (String) redisTemplate.opsForValue().get(checkCodeKey + user.getEmail());
            log.trace(redisCheckCode);
            if (StringUtils.isBlank(redisCheckCode)) {
                //表示未发送验证码
                return 1;
            }
        }
        //验证码错误
        if (!code.equals(redisCheckCode)) {
            return 2;
        }
        this.add(user);
        return 0;
    }

    /**
     * 发送邮箱验证码
     *
     * @param email
     */
    public void sendEmail(String email) {
        //由apache的lang3工具类生成六位验证码
        String checkCode = RandomStringUtils.randomNumeric(6);
        //向缓存中添加以便验证
        redisTemplate.opsForValue().set(checkCodeKey + email, checkCode, 30, TimeUnit.MINUTES);
        //调用消息队列向用户邮箱发送
        HashMap map = new HashMap<>();
        map.put("email", email);
        map.put("checkCode", checkCode);
        rabbitTemplate.convertAndSend("forum", "email", map);
        //用作检查
        log.trace("验证码为:" + checkCode);
    }

    public Map loginByMobile(String code, User user) {
        HashMap<String, Object> map = new HashMap<>();
        String redisCheckCode = (String) redisTemplate.opsForValue().get(checkCodeKey + user.getMobile());
        if (StringUtils.isBlank(redisCheckCode)) {
            redisCheckCode = (String) redisTemplate.opsForValue().get(checkCodeKey + user.getEmail());
            if (StringUtils.isBlank(redisCheckCode)) {
                //表示未发送验证码
                map.put("state", 2);
                return map;
            }
        }
        if (!redisCheckCode.equals(code)) {
            //验证码错误
            map.put("state", 3);
        }
        //如果验证码正确，则从数据库查询用户信息
        Example example = new Example(User.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("mobile", user.getMobile());
        User DB_user = userMapper.selectOneByExample(example);
        //如果不存在此用户，则说明未注册，自动帮其生成密码并注册
        if (DB_user == null) {
            this.add(user);
            DB_user = userMapper.selectOneByExample(example);
            //自动注册并登录成功
            map.put("state", 1);
        } else {
            map.put("state", 0);
        }
        //生成token并返回
        String token = jwtUtil.createJWT(DB_user.getId(), DB_user.getMobile() == null ? DB_user.getEmail() : DB_user.getMobile(), "user", DB_user.getNickname());
        map.put("nickname", DB_user.getNickname());
        map.put("id", DB_user.getId());
        map.put("token", token);
        //登录成功
        return map;
    }

    public Map<String, Object> loginByPassword(User user) {
        HashMap<String, Object> map = new HashMap<>();
        Example example = new Example(User.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.orEqualTo("loginname", user.getLoginname()).orEqualTo("mobile", user.getMobile()).orEqualTo("email", user.getEmail());
        User DB_user = userMapper.selectOneByExample(example);
        log.trace(user.getPassword());
        if (DB_user != null && encoder.matches(user.getPassword(), DB_user.getPassword())) {
            //登录成功
            String token = null;
            if (!StringUtils.isBlank(DB_user.getLoginname())) {
                token = jwtUtil.createJWT(DB_user.getId(), DB_user.getLoginname(), "user", DB_user.getNickname());
            }
            if (!StringUtils.isBlank(DB_user.getMobile())) {
                token = jwtUtil.createJWT(DB_user.getId(), DB_user.getMobile(), "user", DB_user.getNickname());
            }
            if (!StringUtils.isBlank(DB_user.getEmail())) {
                token = jwtUtil.createJWT(DB_user.getId(), DB_user.getEmail(), "user", DB_user.getNickname());
            }
            map.put("token", token);
            map.put("nickname", DB_user.getNickname());
            map.put("id", DB_user.getId());
            map.put("avatar",DB_user.getAvatar());
            map.put("state", 1);
        } else {
            //登录失败
            map.put("state", 0);
        }
        return map;
    }

    /**
     * 更新用户的关注和被关注人的粉丝数
     *
     * @param num
     * @param userid
     * @param friendid
     */
    public void updateFanscountAndFollowcount(int num, String userid, String friendid) {
        userMapper.updateFollowcount(num, userid);
        userMapper.updateFanscount(num, friendid);
    }

    public User getInfo(Claims claims) {
        String id = claims.getId();
        User user = userMapper.selectByPrimaryKey(id);
        user.setPassword(null);
        return user;
    }

    public void changeState(String id, String state) {
        userMapper.changeState(id,state);
    }
}
