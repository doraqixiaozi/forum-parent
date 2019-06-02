package com.jqh.forum.spit.service;

import com.jqh.forum.spit.dao.SpitDao;
import com.jqh.forum.spit.pojo.Spit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import util.IdWorker;

import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * @Auther: 几米
 * @Date: 2019/5/15 20:34
 * @Description: SpitService
 */
@Service
@Transactional
public class SpitService {
    @Autowired
    private SpitDao spitDao;
    @Autowired
    private IdWorker idWorker;
@Autowired
    private MongoTemplate mongoTemplate;
    public List<Spit> findAll() {
        return spitDao.findAll();
    }

    public Spit findById(String id) {
        return spitDao.findById(id).get();
    }

    public void add(Spit spit) {
        spit.set_id(idWorker.nextId() + "");
        spit.setPublishtime(new Date());//发布日期
        spit.setVisits(0);//浏览量
        spit.setShare(0);//分享数
        spit.setThumbup(0);//点赞数
        spit.setComment(0);//回复数
        spit.setState("1");//状态
        //如果此吐槽有父节点则说明是对某个吐槽的回复，父节点回复数应该加1
        if (!StringUtils.isEmpty(spit.getParentid())){
            Query query=new Query();
            query.addCriteria(Criteria.where("_id").is(spit.getParentid()));
            Update update=new Update();
            update.inc("comment",1);
            mongoTemplate.updateFirst(query,update,"spit");
        }
        spitDao.save(spit);
    }

    public void update(Spit spit) {
        spitDao.save(spit);
    }

    public void deleteById(String id) {
        spitDao.deleteById(id);
    }

    public Page<Spit> findByParentid(String parentId,int page,int size){
        Pageable pageable= PageRequest.of(page-1,size);
        return spitDao.findByParentid(parentId,pageable);
    }

    public List<Spit> search(Spit spit){
        ExampleMatcher matcher = ExampleMatcher.matching().withMatcher("nickname", ExampleMatcher.GenericPropertyMatchers.contains()).withIgnoreCase();
        return spitDao.findAll(Example.of(spit,matcher));
    }
    public Page<Spit> search(Spit spit,int page,int size){
        PageRequest request = PageRequest.of(page - 1, size);
        ExampleMatcher matcher = ExampleMatcher.matching().withMatcher("nickname", ExampleMatcher.GenericPropertyMatchers.contains()).withIgnoreCase();
        return spitDao.findAll(Example.of(spit,matcher),request);
    }

    public void thumbup(String spitId) {
        Query query=new Query();
        query.addCriteria(Criteria.where("_id").is(spitId));
        Update update=new Update();
        update.inc("thumbup",1);
        mongoTemplate.updateFirst(query,update,"spit");
    }
}
