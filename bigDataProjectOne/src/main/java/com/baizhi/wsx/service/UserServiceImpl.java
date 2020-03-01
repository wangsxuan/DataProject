package com.baizhi.wsx.service;

import com.baizhi.wsx.dao.UserDao;
import com.baizhi.wsx.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.Servlet;

@Service
@Transactional
public class UserServiceImpl implements UserServier {
    @Autowired
    private UserDao userDao;

    @Override
    public void register(User user) {
        userDao.save(user);
    }


    /*通过用户名查询一个*/
    @Override
    public String select(String username, String password, String email) {
        if(username.equals(""))return "用户名错误！！！";
        User user = userDao.q(username);
        if(!password.equals(user.getPassword()))return "密码错误！！！";
       // if(!email.equals(user.getEmail()))return "";

        return "000";
    }
}
