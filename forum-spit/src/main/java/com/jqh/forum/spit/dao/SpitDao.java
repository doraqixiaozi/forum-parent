package com.jqh.forum.spit.dao;

import com.jqh.forum.spit.pojo.Spit;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import sun.security.provider.ConfigFile;

import java.util.List;

/**
 * @Auther: 几米
 * @Date: 2019/5/15 20:28
 * @Description: SpitDao
 */
public interface SpitDao extends MongoRepository<Spit,String> {
    Page<Spit> findByParentid(String parentId, Pageable pageable);
}
