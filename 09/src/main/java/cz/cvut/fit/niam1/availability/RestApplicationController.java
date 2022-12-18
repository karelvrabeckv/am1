package cz.cvut.fit.niam1.availability;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import java.net.URI;
import java.util.Collections;

@RestController
public class RestApplicationController {

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    RestApplicationServices services;

    private static int lastUsed = -1;

    private final Logger logger = LoggerFactory.getLogger(RestApplicationController.class);

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @GetMapping(value = "/test")
    public ResponseEntity test(HttpServletRequest request) throws Exception {
        // copy headers
        HttpHeaders headers = new HttpHeaders();
        Collections.list(request.getHeaderNames()).forEach(head -> headers.add(head, request.getHeader(head)));

        // create request entity
        HttpEntity<String> requestEntity = new HttpEntity<String>(headers);

        // health
        checkHealthOfServices();
        logHealthOfServices();

        if (servicesUnavailable()) {
            logger.info("None of the services is available...");

            return new ResponseEntity<>(HttpStatus.SERVICE_UNAVAILABLE);
        }

        // round-robin
        do {
            lastUsed = (lastUsed + 1) % services.getServices().size();
        } while (services.getServiceById(lastUsed).getStatus() == HttpStatus.INTERNAL_SERVER_ERROR.value());

        // HTTP
        ResponseEntity responseEntity = restTemplate.exchange(
                new URI(services.getServiceById(lastUsed).getUrl()),
                HttpMethod.GET,
                requestEntity,
                String.class
        );
        logger.info("Forwarded to SERVICE {}", services.getServiceById(lastUsed).getId() + 1);

        return responseEntity;
    }

    public void checkHealthOfServices() {
        for (Service s : services.getServices()) {
            // HTTP
            try {
                ResponseEntity responseEntity = restTemplate.exchange(
                        new URI(s.getUrl()),
                        HttpMethod.GET,
                        null,
                        String.class
                );

                s.setStatus(responseEntity.getStatusCode().value());
            } catch( Exception e ) {
                s.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            }
        }
    }

    public void logHealthOfServices() {
        for (Service s : services.getServices()) {
            logger.info("SERVICE {} -> [ID: {}, STATUS: {}, URL: {}]", s.getId() + 1, s.getId(), s.getStatus(), s.getUrl());
        }
    }

    public Boolean servicesUnavailable() {
        for (Service s : services.getServices()) {
            if (s.getStatus() == HttpStatus.OK.value()) {
                return false;
            }
        }

        return true;
    }

}
