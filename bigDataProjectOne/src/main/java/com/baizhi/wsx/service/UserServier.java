package com.baizhi.wsx.service;

import com.baizhi.wsx.entity.User;

import javax.servlet.Servlet;

public interface UserServier {
    //注册
    public void register(User user);
    /* 查询*/
    public String select(String username, String password, String email);
}
