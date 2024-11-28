package com.grayseal.transactapi;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/testApp")
public class AppController {
    @GetMapping("/")
    public String testTransactApiApp() {
        return "App Running";
    }
}
