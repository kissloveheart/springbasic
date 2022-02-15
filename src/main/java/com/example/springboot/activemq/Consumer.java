package com.example.springboot.activemq;

import com.example.springboot.model.Orders;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Component;

import javax.jms.Message;
import javax.jms.Session;

import static com.example.springboot.activemq.JmsConfig.ORDER_IN_QUEUE;
import static com.example.springboot.activemq.JmsConfig.ORDER_OUT_QUEUE;

@Component
@Slf4j
public class Consumer {

    @SendTo(ORDER_OUT_QUEUE)
    @JmsListener(destination = ORDER_IN_QUEUE)
    public String receiveMessage(@Payload Orders order,
                               @Headers MessageHeaders headers,
                               Message message, Session session) {
        log.info("received <" + order + ">");

        log.info("- - - - - - - - - - - - - - - - - - - - - - - -");
        log.info("######          Message Details           #####");
        log.info("- - - - - - - - - - - - - - - - - - - - - - - -");
        log.info("headers: " + headers);
        log.info("message: " + message);
        log.info("session: " + session);
        log.info("- - - - - - - - - - - - - - - - - - - - - - - -");
        return order.toString();
    }
}
