package com.example.demo.controller;

import com.example.demo.service.HttpClientService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
public class HttpClientController {

    @Resource
    HttpClientService httpClientService;

    @GetMapping("")
    public void connectTo8801() {
        System.out.println("=======开始连接=======");
        httpClientService.connectTo8801();
        System.out.println("=======结束连接=======");
    }
}
