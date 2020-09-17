package com.bbb.redis.controller;

import com.bbb.redis.common.JsonResponse;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/sec")
public class TestController {
    @GetMapping("/test")
    @CrossOrigin("*")
    JsonResponse<String> get(){
        return new JsonResponse<String>().setCode(200).setMsg("no");
    }
}
