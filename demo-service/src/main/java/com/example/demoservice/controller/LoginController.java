package com.example.demoservice.controller;


import com.example.demoservice.dto.Test;
import org.springframework.http.HttpRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api")
public class LoginController {

    @PostMapping("/test")
    public ResponseEntity<String> Login(HttpServletRequest request, @RequestBody Test t){
        return ResponseEntity.ok(t.getUser());
    }
}
