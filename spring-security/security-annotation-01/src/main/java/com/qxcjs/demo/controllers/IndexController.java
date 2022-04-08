package com.qxcjs.demo.controllers;

import com.qxcjs.demo.annotation.Auth;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api")
@Auth(id = 1000, name = "管理测试")
public class IndexController {
    @GetMapping(path = "index")
    public String index() {
        return "index";
    }

    @GetMapping(path = "hello")
    public String hello() {
        return "hello";
    }

    @Auth(id = 3, name = "anonymous角色")
    @GetMapping(path = "/anonymous/hello")
    public String anonymous() {
        return "anonymous hello";
    }

    @Auth(id = 1, name = "admin角色")
    @GetMapping("/admin/hello")
    public String admin() {
        return "admin hello";
    }

    @Auth(id = 2, name = "user角色")
    @GetMapping("/user/hello")
    public String user() {
        return "user hello";
    }
}
