package cz.cvut.fit.niam1.messaging;

import org.apache.activemq.command.ActiveMQQueue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.core.JmsTemplate;

import javax.jms.Queue;
import java.time.LocalDateTime;

@SpringBootApplication
@EnableJms
public class MessageProducerApplication implements CommandLineRunner {

    private final Logger logger = LoggerFactory.getLogger(MessageProducerApplication.class);

    @Autowired
    private JmsTemplate jmsTemplate;

    @Autowired
    private Queue allOrdersQueue;

    @Bean
    public Queue allOrdersQueue() {
        return new ActiveMQQueue("allOrdersQueue");
    }

    public static void main(String[] args) {
        SpringApplication.run(MessageProducerApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        Thread.sleep(5000);

        Booking b1 = new Booking("0", "Karel", "Vrabec", "New York");
        logger.info("Sending message to {} at {}", allOrdersQueue.getQueueName(), LocalDateTime.now());
        jmsTemplate.convertAndSend(allOrdersQueue, b1.toString(), m -> {
            m.setStringProperty("type", "booking");
            return m;
        });

        Thread.sleep(5000);

        Trip t1 = new Trip("0", "Charles Bridge");
        logger.info("Sending message to {} at {}", allOrdersQueue.getQueueName(), LocalDateTime.now());
        jmsTemplate.convertAndSend(allOrdersQueue, t1.toString(), m -> {
            m.setStringProperty("type", "trip");
            return m;
        });

        Thread.sleep(5000);

        Booking b2 = new Booking("1", "Petr", "Novak", "Prague");
        logger.info("Sending message to {} at {}", allOrdersQueue.getQueueName(), LocalDateTime.now());
        jmsTemplate.convertAndSend(allOrdersQueue, b2.toString(), m -> {
            m.setStringProperty("type", "booking");
            return m;
        });
    }

}
