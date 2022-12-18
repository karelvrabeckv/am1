package cz.cvut.fit.niam1.availability;

public class Service {

    int id;
    int status;
    String url;

    public Service(int id, int status, String url) {
        this.id = id;
        this.status = status;
        this.url = url;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

}
