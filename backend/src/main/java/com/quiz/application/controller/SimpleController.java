package com.quiz.application.controller;

import org.springframework.web.bind.annotation.*;

@RestController
public class SimpleController {

    @GetMapping("/simple")
    public String simpleEndpoint() {
        return "Hello World - Simple Test";
    }
}
