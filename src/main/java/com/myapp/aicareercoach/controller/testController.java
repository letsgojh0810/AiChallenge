package com.myapp.aicareercoach.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class testController { // 클래스 이름은 파스칼 케이스(PascalCase)를 따르는 것이 자바 표준 컨벤션입니다.

    @GetMapping("/test")
    public String testEndpoint() {
        return "Test Controller is working! ✅";
    }
}