package cz.cvut.fit.niam1.messaging;

public class Trip {

    String id;
    String location;

    public Trip(String id, String location) {
        this.id = id;
        this.location = location;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String toString() {
        return "Trip [id=" + id + ", location=" + location + "]";
    }

}
