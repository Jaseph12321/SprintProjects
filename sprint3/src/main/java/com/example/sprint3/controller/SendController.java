package com.example.sprint3.controller;


import com.example.sprint3.model.User;
import com.example.sprint3.request.UpdateRequest;
import com.example.sprint3.service.SendService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.jms.JMSException;

@RestController
@RequestMapping("/send")
public class SendController {

    private static Logger LOGGER  =  LoggerFactory.getLogger(SendController.class);

    @Autowired
    private SendService sendService;

    @GetMapping()
    public String getAll(){
       return sendService.getAll();
    }

    @GetMapping("/{id}")
    public String sendName(@PathVariable String id){
        LOGGER.trace("sending id message");
        sendService.sendName(id);
      return id;
    }

    @PostMapping("/create")
    public String createUser(@RequestBody User user) throws JMSException {
        LOGGER.trace("sending create user message");
        sendService.createUser(user);
        return "success";
    }

    @PostMapping("/update/{id}")
    public String updateUser(@RequestBody UpdateRequest user, @PathVariable int id) throws JMSException {
        LOGGER.trace("sending update user message");
        sendService.updateUser(user, id);
        return "success";
    }

    @DeleteMapping("/delete/{id}")
    public String deleteUser(@PathVariable int id) throws JMSException {
        LOGGER.trace("sending id message");
        sendService.deleteUser(id);
        return "success";
    }


}
