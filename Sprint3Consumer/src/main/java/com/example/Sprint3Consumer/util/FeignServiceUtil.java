package com.example.Sprint3Consumer.util;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(value="feignDemo", url="http://localhost:8080/send")
public interface FeignServiceUtil {

    @GetMapping("/{id}")
    String sendName(@PathVariable String id);

}
