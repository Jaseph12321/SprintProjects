package com.example.sprint3.service;


import com.example.sprint3.model.User;
import com.example.sprint3.model.UserRepository;
import com.example.sprint3.request.UpdateRequest;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.jms.*;

@Service
@Component
public class SendService {


    private Logger logger = LoggerFactory.getLogger(SendService.class);
    private JmsTemplate jmsTemplate;
    public User user;

    @Autowired
    private UserRepository repo;
    @Autowired
    public SendService(JmsTemplate jmsTemplate) {
        this.jmsTemplate = jmsTemplate;
    }

    public void sendName(String id) {
        String option = "1";
        jmsTemplate.convertAndSend("topic_test.QUEUE", option+"/"+ id);
    }

    public void createUser(User user) {
        String option = "2";
        System.out.println(user.getAge());
        logger.trace("combine to string");
        jmsTemplate.convertAndSend("topic_test.QUEUE", option + "/"+user.toString());
    }


    public void updateUser(UpdateRequest user, int id)throws JMSException {
        String option = "3";

        ActiveMQConnectionFactory activeMQConnectionFactory = new ActiveMQConnectionFactory("tcp://localhost:61616");
        Connection connection = activeMQConnectionFactory.createConnection();
        connection.start();
        Queue queue;

        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        logger.info("creating log");
        queue = session.createQueue("topic_test.QUEUE");

        MessageProducer messageProducer = session.createProducer(queue);


        logger.trace("combine to string");
        TextMessage textMessage = session.createTextMessage(option+"/"+id+"/"+user.toString());
        messageProducer.send(textMessage);

        messageProducer.close();
        session.close();
        connection.close();
    }

    public void deleteUser(int id)throws JMSException {
        String option = "4";
        jmsTemplate.convertAndSend("topic_test.QUEUE",option+"/"+id);
    }

    public String getAll() {
        jmsTemplate.convertAndSend("topic_test.QUEUE","all");
        String users = this.repo.findAll().toString();
        return users;
    }
}
