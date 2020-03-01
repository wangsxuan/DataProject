package com.baizhi.wsx.action;

import com.baizhi.wsx.entity.User;
import com.baizhi.wsx.service.UserServier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.websocket.Session;

@Controller
@RequestMapping("/user")
public class UserController {


    @Autowired
    private UserServier userServier;

    @RequestMapping("/register")
    public String register(User user)throws Exception{
        userServier.register(user);
        return "loginOk";
    }
    @RequestMapping("/login")
    public String Login(User user, Model model)throws Exception{
        String select = userServier.select(
                user.getUsername(), user.getPassword(), user.getEmail()
        );
        if (select.equals("000")){
            return "loginOk";
        }else{
            model.addAttribute("select",select);
            System.out.println(select);
            return "login";
        }
    }
}
