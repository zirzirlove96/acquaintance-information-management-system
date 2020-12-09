package com.fastcampus.java.project3.demo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @GetMapping("/api/helloWorld")
    public String show(){
        return "Hello World!!";
    }
}
