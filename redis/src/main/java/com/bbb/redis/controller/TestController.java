package com.bbb.redis.controller;

import com.bbb.redis.common.JsonResponse;
import com.bbb.redis.service.SecKill;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Random;

@RestController
@RequestMapping("/sec")
public class TestController {
    @Autowired
    SecKill secKill;
    @GetMapping("/test")
    @CrossOrigin("*")
    JsonResponse<String> get(){
        String uid=new Random().nextInt(5000)+"";
        String pid="1001";
        boolean flag=secKill.doKill_script(uid,pid);
        if(flag){
           return new JsonResponse<String>().setCode(200).setMsg("yes");
        }else{
           return new JsonResponse<String>().setCode(200).setMsg("no");
        }



    }
}
