package cz.cvut.fit.niam1.seventh_assignment;

import org.springframework.hateoas.RepresentationModel;

import java.util.ArrayList;
import java.util.List;

public class Tour extends RepresentationModel<Tour> {

    String id;
    String name;
    List<String> customers;

    public Tour(String id, String name, List<String> customers) {
        this.id = id;
        this.name = name;
        this.customers = new ArrayList<String>();
        this.customers.addAll(customers);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getCustomers() {
        return customers;
    }

    public void setCustomers(List<String> customers) {
        this.customers.clear();
        this.customers.addAll(customers);
    }

}
