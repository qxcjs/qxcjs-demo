package com.qxcjs.demo.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/api/")
public class IndexController {
    @GetMapping(path = "hello")
    public String hello() {
        return "hello";
    }

    @GetMapping(path = "/anonymous/hello")
    public String anonymous() {
        return "anonymous hello";
    }

    @GetMapping("/admin/hello")
    public String admin() {
        return "admin hello";
    }

    @GetMapping("/user/hello")
    public String user() {
        return "user hello";
    }
}
