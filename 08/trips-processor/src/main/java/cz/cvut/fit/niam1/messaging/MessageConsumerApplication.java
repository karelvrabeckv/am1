package cz.cvut.fit.niam1.messaging;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.annotation.JmsListener;

@SpringBootApplication
@EnableJms
public class MessageConsumerApplication {

    private final Logger logger = LoggerFactory.getLogger(MessageConsumerApplication.class);

    @JmsListener(destination = "tripsQueue")
    public void readMessage(String message) throws InterruptedException {
        logger.info("Received message: {}", message);

        Thread.sleep(2000);
    }

    public static void main(String[] args) {
        SpringApplication.run(MessageConsumerApplication.class, args);
    }

}
