package com.qxcjs.demo.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class IndexController {
    @GetMapping(path = "index")
    public String index() {
        return "index";
    }

    @PostMapping(path = "postHello")
    public String postHello() {
        return "hello";
    }

    @GetMapping(path = "getHello")
    public String getHello() {
        return "hello";
    }

    @GetMapping(path = "redirectHello")
    public String redirectHello() {
        return "redirect:/index";
    }
}
