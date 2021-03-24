package com.cos.reflect.controller;

import com.cos.reflect.annotation.RequestMapping;
import com.cos.reflect.controller.dto.JoinDto;
import com.cos.reflect.controller.dto.LoginDto;
import com.cos.reflect.model.User;

public class UserController {


    @RequestMapping("/user/join")
    public String join(JoinDto dto) {
        System.out.println("UserController.join");
        System.out.println(dto);
        return "/";
    }

    @RequestMapping("/user/login")
    public String login(LoginDto dto) {
        System.out.println("UserController.login");
        System.out.println(dto);
        return "/";
    }

    @RequestMapping("/user/logout")
    public String logout() {
        System.out.println("UserController.logout");
        return "/";
    }


    @RequestMapping("/user")
    public String user(User user) {
        System.out.println("UserController.user");
        System.out.println(user);
        return "/";
    }
}
