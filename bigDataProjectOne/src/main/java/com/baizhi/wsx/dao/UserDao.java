package com.baizhi.wsx.dao;

import com.baizhi.wsx.entity.User;

public interface UserDao {
    /*添加的方法*/
    public void save(User user);
    /*通过用户名查询一个*/
    public User q(String username);
}
