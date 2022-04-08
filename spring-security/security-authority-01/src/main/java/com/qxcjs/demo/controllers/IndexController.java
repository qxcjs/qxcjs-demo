package com.qxcjs.demo.controllers;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api")
public class IndexController {
    @GetMapping(path = "index")
    public String index() {
        return "index";
    }

    @GetMapping(path = "hello")
    public String hello() {
        return "hello";
    }

    @PreAuthorize("hasAuthority('hello:anonymous')")
    @GetMapping(path = "/anonymous/hello")
    public String anonymous() {
        return "anonymous hello";
    }

    @PreAuthorize("hasRole('admin')")
    @GetMapping("/admin/hello")
    public String admin() {
        return "admin hello";
    }

    @PreAuthorize("hasAuthority('user')")
    @GetMapping("/user/hello")
    public String user() {
        return "user hello";
    }
}
