package com.jqh.forum.base.service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.jqh.forum.base.mapper.CityMapper;
import com.jqh.forum.base.pojo.City;
import com.jqh.forum.base.pojo.Label;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;
import util.IdWorker;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * CityService
 *
 * @author 862965251@qq.com
 * @date 2020-04-07 18:40
 */
@Service
@Slf4j
public class CityService {
    @Resource
    private IdWorker idWorker;
    @Resource
    private CityMapper cityMapper;

    public List<City> findAll(){
        return cityMapper.selectAll();
    }

    public City findById(String id){
        return cityMapper.selectByPrimaryKey(id);
    }

    public void add(City city){
        city.setId(idWorker.nextId()+"");
        cityMapper.insert(city);
    }

    public void update(City city){
        cityMapper.updateByPrimaryKeySelective(city);
    }

    public void deleteById(String id){
        cityMapper.deleteByPrimaryKey(id);
    }

    public List<City> findByCondition(City city){
        Example example = new Example(City.class);
        Example.Criteria criteria = example.createCriteria();

        if (StringUtils.isNotEmpty(city.getId())){
            criteria.andEqualTo("id",city.getId());
        }
        if (StringUtils.isNotEmpty(city.getName())){
            criteria.andLike("name","%"+city.getName()+"%");
        }
        if (StringUtils.isNotEmpty(city.getIshot())){
            criteria.andEqualTo("ishot",city.getIshot());
        }

        return cityMapper.selectByExample(example);
    }

    public Map findByCondition(Integer page, Integer size, City city){
        Page<City> cityPage = PageHelper.startPage(page == null ? 0 : page, size == null ? 10 : size);
        List<City> cities = findByCondition(city);
        HashMap hashMap = new HashMap();
        hashMap.put("total",cityPage.getTotal());
        hashMap.put("rows",cities);
//        log.trace(hashMap.toString());
        return hashMap;
    }
}
