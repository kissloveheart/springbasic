package com.example.springboot.activemq;

import lombok.extern.slf4j.Slf4j;
import org.apache.qpid.jms.JmsConnectionFactory;

import javax.jms.*;


public class Consumer {
    public static void main(String[] args) throws JMSException {

        System.out.println("Create a ConnectionFactory");
        // ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://localhost:61616");
        ConnectionFactory connectionFactory = new JmsConnectionFactory("amqp://localhost:5672");

        System.out.println("Create a Connection");
        Connection connection = connectionFactory.createConnection("admin", "admin");
        connection.start();

        System.out.println("Create a Session");
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

        System.out.println("Create a Topic/ Queue based on the given parameter");
        Destination destination = null;
        if (args.length > 0 && args[0].equalsIgnoreCase("QUEUE")) {
            destination = session.createQueue("jms-queue");
        } else if (args.length > 0 && args[0].equalsIgnoreCase("TOPIC")) {
            destination = session.createTopic("jms-topic");
        } else {
            System.out.println("Error: You must specify Queue or Topic");
            connection.close();
            System.exit(1);
        }

        System.out.println("Create a Consumer to receive messages from one Topic or Queue.");
        MessageConsumer consumer = session.createConsumer(destination);

        System.out.println("Start receiving messages ... ");
        String body;
        do {
            Message msg = consumer.receive();
            body = ((TextMessage) msg).getText();
            System.out.println("Received = " + body);
        } while (!body.equalsIgnoreCase("close"));

        System.out.println("Shutdown JMS connection and free resources");
        connection.close();
        System.exit(1);
    }
}
