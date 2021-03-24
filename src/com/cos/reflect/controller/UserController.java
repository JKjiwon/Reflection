package com.cos.reflect.controller;

public class UserController {

    public String login() {
        System.out.println("UserController.login");
        return "/";
    }

    public String logout() {
        System.out.println("UserController.logout");
        return "/";
    }

    public String join() {
        System.out.println("UserController.join");
        return "/";
    }

    public String user() {
        System.out.println("UserController.user");
        return "/";
    }
}
