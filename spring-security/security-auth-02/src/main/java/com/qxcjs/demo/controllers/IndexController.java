package com.qxcjs.demo.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class IndexController {
    @GetMapping(path = "index")
    public String index() {
        return "index";
    }
}
