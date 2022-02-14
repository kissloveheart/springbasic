package com.example.springboot.activemq;

import lombok.extern.slf4j.Slf4j;
import org.apache.qpid.jms.JmsConnectionFactory;

import javax.jms.*;
import java.io.BufferedReader;
import java.io.InputStreamReader;

public class Producer {
    public static void main(String[] args) throws Exception {
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

        System.out.println("Create a Producer to send messages to one Topic or Queue.");
        MessageProducer producer = session.createProducer(destination);

        System.out.println("Start sending messages ... ");
        try (BufferedReader br = new BufferedReader(new InputStreamReader(System.in));) {
            String response;
            do {
                System.out.print("Enter message: ");
                response = br.readLine().trim();
                TextMessage msg = session.createTextMessage(response);
                producer.send(msg);
            } while (!response.equalsIgnoreCase("close"));
        }

        System.out.println("Shutdown JMS connection and free resources");
        connection.close();
        System.exit(1);
    }
}
