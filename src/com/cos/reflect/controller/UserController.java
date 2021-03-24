package com.cos.reflect.controller;

import com.cos.reflect.annotation.RequestMapping;

public class UserController {

    @RequestMapping("/user/login")
    public String login() {
        System.out.println("UserController.login");
        return "/";
    }

    @RequestMapping("/user/logout")
    public String logout() {
        System.out.println("UserController.logout");
        return "/";
    }

    @RequestMapping("/user/join")
    public String join() {
        System.out.println("UserController.join");
        return "/";
    }

    @RequestMapping("/user")
    public String user() {
        System.out.println("UserController.user");
        return "/";
    }
}
