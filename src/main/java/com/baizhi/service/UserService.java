package com.baizhi.service;

import com.baizhi.entity.User;

import java.util.List;
import java.util.Map;

public interface UserService {
    Map<String,Object> selectUsersByStarId(Integer page,Integer rows,String starId);

    String add(User user);

    void edit(User user);

    List<User> getAllUserForSelect();
}
