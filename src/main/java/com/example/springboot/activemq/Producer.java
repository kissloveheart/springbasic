package com.example.springboot.activemq;

import com.example.springboot.model.Orders;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

import static com.example.springboot.activemq.JmsConfig.ORDER_IN_QUEUE;

@Slf4j
@Component
public class Producer {
    @Autowired
    private JmsTemplate jmsTemplate;

    public void send(Orders myMessage) {
        log.info("sending with convertAndSend() to queue <" + myMessage + ">");
        jmsTemplate.convertAndSend(ORDER_IN_QUEUE, myMessage);
    }
}
