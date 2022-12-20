package com.vish.accounts.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AccountLevelsController {

    @GetMapping("/")
    public String sayHelloAll(){
        return "Hello buddy Welcome";
    }

    @GetMapping("/userLevel")
    public String sayHelloUser(){
        return "Hello User Welcome";
    }

    @GetMapping("/adminLevel")
    public String sayHelloAdmin(){
        return "Hello Admin Welcome";
    }

}
