package cz.cvut.fit.niam1.availability;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@Component
public class RestApplicationServices {

    private static final List<Service> services = new ArrayList<Service>();

    @PostConstruct
    public void initRepo() {
        services.add(new Service(0, HttpStatus.OK.value(), "http://147.32.233.18:8888/MI-MDW-LastMinute1/list"));
        services.add(new Service(1, HttpStatus.OK.value(), "http://147.32.233.18:8888/MI-MDW-LastMinute2/list"));
        services.add(new Service(2, HttpStatus.OK.value(), "http://147.32.233.18:8888/MI-MDW-LastMinute3/list"));
    }

    public List<Service> getServices() {
        return services;
    }

    public Service getServiceById(int id) {
        return services.stream().filter(s -> s.getId() == id).findAny().orElse(null);
    }

}
