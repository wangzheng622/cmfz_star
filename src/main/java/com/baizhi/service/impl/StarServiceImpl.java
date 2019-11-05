package com.baizhi.service.impl;

import com.baizhi.dao.StarDao;
import com.baizhi.entity.Star;
import com.baizhi.service.StarService;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
@Transactional
public class StarServiceImpl implements StarService {

    @Autowired
    private StarDao starDao;

    @Override
    public Map<String, Object> selectAll(Integer page, Integer rows) {
        Star star = new Star();
        RowBounds rowBounds = new RowBounds((page-1)*rows,rows);
        List<Star> list = starDao.selectByRowBounds(star,rowBounds);
        int count = starDao.selectCount(star);

        Map<String, Object> map = new HashMap<>();
        map.put("page",page);
        map.put("rows",list);
        map.put("total",count%rows==0?count/rows:count/rows+1);
        map.put("records",count);

        return map;
    }

    @Override
    public String add(Star star) {
        star.setId(UUID.randomUUID().toString());
        int i = starDao.insert(star);
        if(i==0){
            throw new RuntimeException("添加明星失败");

        }
        return star.getId();
    }

    @Override
    public void edit(Star star) {
    int i = starDao.updateByPrimaryKeySelective(star);
    if(i==0){
        throw new  RuntimeException("修改明星失败");
    }
    }

    @Override
    public List<Star> getAllStarForSelect() {
        List<Star> list = starDao.selectAll();
        return list;
    }
}
