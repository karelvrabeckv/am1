package cz.cvut.fit.niam1.messaging;

import org.apache.activemq.command.ActiveMQQueue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Queue;
import java.time.LocalDateTime;

@SpringBootApplication
@EnableJms
public class MessageMediatorApplication {

    private final Logger logger = LoggerFactory.getLogger(MessageMediatorApplication.class);

    @Autowired
    private JmsTemplate jmsTemplate;

    @Autowired
    private Queue bookingsQueue;

    @Bean
    public Queue bookingsQueue() {
        return new ActiveMQQueue("bookingsQueue");
    }

    @Autowired
    private Queue tripsQueue;

    @Bean
    public Queue tripsQueue() {
        return new ActiveMQQueue("tripsQueue");
    }

    @JmsListener(destination = "allOrdersQueue")
    public void readMessage(Message message) throws InterruptedException, JMSException {
        String type = message.getStringProperty("type");

        if (type.equals("booking")) {
            logger.info("Forwarding message to {} at {}", bookingsQueue.getQueueName(), LocalDateTime.now());
            jmsTemplate.convertAndSend(bookingsQueue, message);
        }
        else if (type.equals("trip")) {
            logger.info("Forwarding message to {} at {}", tripsQueue.getQueueName(), LocalDateTime.now());
            jmsTemplate.convertAndSend(tripsQueue, message);
        }

        Thread.sleep(2000);
    }

    public static void main(String[] args) {
        SpringApplication.run(MessageMediatorApplication.class, args);
    }

}
