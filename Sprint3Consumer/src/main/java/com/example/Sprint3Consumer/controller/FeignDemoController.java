package com.example.Sprint3Consumer.controller;


import com.example.Sprint3Consumer.util.FeignServiceUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/demo")
public class FeignDemoController {

    private Logger logger = LoggerFactory.getLogger(FeignDemoController.class);
    @Autowired
    private FeignServiceUtil feignServiceUtil;

    @GetMapping("/test/{id}")
    public String sendtest(@PathVariable String id){
        logger.info("cloud open feign small test");
        return feignServiceUtil.sendName(id);
    }
}
