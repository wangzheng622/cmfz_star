package com.baizhi.service.impl;

import com.baizhi.dao.UserDao;
import com.baizhi.entity.User;
import com.baizhi.service.UserService;
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
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;


    @Override
    public Map<String, Object> selectUsersByStarId(Integer page, Integer rows, String starId) {
        User user = new User();
        user.setStarId(starId);
        RowBounds rowBounds = new RowBounds((page - 1) * rows, rows);
        List<User> list = userDao.selectByRowBounds(user,rowBounds);
        int count = userDao.selectCount(user);
        Map<String,Object> map = new HashMap<>();
        map.put("page",page);
        map.put("rows",list);
        map.put("total",count%rows==0?count/rows:count/rows+1);
        map.put("records",count);
        return map;
    }

    @Override
    public String add(User user) {
        user.setId(UUID.randomUUID().toString());
        int i=userDao.insert(user);
        if(i==0){
            throw new RuntimeException("添加失败");
        }
        return user.getId();
    }

    @Override
    public void edit(User user) {
        int i=userDao.updateByPrimaryKeySelective(user);
        if(i==0){
            throw new RuntimeException("修改明星失败");
        }
    }

    @Override
    public List<User> getAllUserForSelect() {
        List<User> list = userDao.selectAll();
        return list;
    }


}
