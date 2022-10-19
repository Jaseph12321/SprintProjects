package com.example.Sprint3Consumer.service;


import com.example.Sprint3Consumer.model.User;
import com.example.Sprint3Consumer.model.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;

@Component
public class ConsumerService  {

    private Logger logger = LoggerFactory.getLogger(ConsumerService.class);
    @Autowired
    UserRepository repo;

    @JmsListener(destination = "youtubeg")
    public void sendtest(String id){
        System.out.println("The name sent from the youtubeg is:" + id);
    }
    //@Async("executor")
//    @JmsListener(destination = "topic_test.TOPIC",containerFactory = "topicConnectionFactory")
//    public  void displayUser(String user){
//
//        System.out.println("The name sent from the topic is:" + user);
//        String[] userInfo = user.split("/");
//        for(String s: userInfo)
//            System.out.println(s);
//        //1. read 2. create 3. update 4. delete
//        //for read and delete
//        if(userInfo[0] == "1"){
//            List<User> users = this.repo.findById(Integer.parseInt(userInfo[1]));
//            System.out.println(users);
//        }else if(userInfo[0] == "4"){
//            this.repo.findById(Integer.parseInt(userInfo[1]));
//            System.out.println("delete success");
//        }else{
//            //for create and update
//            User theUser = new User();
//            theUser.setId(Integer.parseInt(userInfo[1]));
//            theUser.setName(userInfo[2]);
//            theUser.setAge(Integer.parseInt(userInfo[3]));
//            theUser.setEmail(userInfo[4]);
//            repo.save(theUser);
//        }
//        }

    @JmsListener(destination = "topic_test.QUEUE",containerFactory = "queueConnectionFactory")
    public  void displayUser2(String user){

        System.out.println("The name sent from the queue is:" + user);
        String[] userInfo = user.split("/");
        for(String s: userInfo)
            System.out.println(s);
        //1. read 2. create 3. update 4. delete
        //for read and delete
        if(Objects.equals(userInfo[0], "1")){
            logger.info("The id get from the queue");
            List<User> users = this.repo.findById(Integer.parseInt(userInfo[1]));
            System.out.println(users);
        }else if(Objects.equals(userInfo[0], "4")){
            logger.info("The id get from the queue");
            this.repo.deleteById(Integer.parseInt(userInfo[1]));
            System.out.println("delete success");
        }else{
            //for create and update
            logger.info("The user info get from the queue");
            User theUser = new User();
            theUser.setId(Integer.parseInt(userInfo[1]));
            theUser.setName(userInfo[2]);
            theUser.setAge(Integer.parseInt(userInfo[3]));
            theUser.setEmail(userInfo[4]);
            repo.save(theUser);
        }
    }



}
