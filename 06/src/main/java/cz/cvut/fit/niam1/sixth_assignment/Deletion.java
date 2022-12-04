package cz.cvut.fit.niam1.sixth_assignment;

import org.springframework.hateoas.RepresentationModel;

public class Deletion extends RepresentationModel<Deletion> {

    String id;
    String tourId;
    String status;

    public Deletion(String id, String tourId, String status) {
        this.id = id;
        this.tourId = tourId;
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTourId() {
        return tourId;
    }

    public void setTourId(String tourId) {
        this.tourId = tourId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}
