package cz.cvut.fit.niam1.seventh_assignment;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping(value = "/tour")
public class RestTourController {

    @Autowired
    RestApplicationRepository repository;

    Date lastModified = new Date();

    public String getWeakETag() {
        String weakETag = "";

        for (Tour t : repository.getTours()) {
            weakETag += t.getId() + t.getName();
        }

        return Integer.toString(weakETag.hashCode());
    }

    public String getStrongETag() {
        String strongETag = "";

        for (Tour t : repository.getTours()) {
            strongETag += t.getId() + t.getName();

            for (String c : t.getCustomers()) {
                strongETag += c;
            }
        }

        return Integer.toString(strongETag.hashCode());
    }

    @GetMapping("/last-modified")
    public ResponseEntity getToursWithLastModified(WebRequest request) {

        /* prepare the data */
        List<Tour> tours = repository.getTours();
        CollectionModel<Tour> data = CollectionModel.of(tours);

        /* check the last modified date */
        if (request.checkNotModified(lastModified.getTime())) {
            /* cache the data */
            return ResponseEntity.status(HttpStatus.NOT_MODIFIED).build();
        }

        /* send back new data */
        return new ResponseEntity<CollectionModel<Tour>>(data, new HttpHeaders(), HttpStatus.OK);

    }

    @GetMapping("/weak-etag")
    public ResponseEntity getToursWithWeakETag(WebRequest request) {

        /* prepare the data */
        List<Tour> tours = repository.getTours();
        CollectionModel<Tour> data = CollectionModel.of(tours);

        /* check the last modified date */
        if (request.checkNotModified(getWeakETag())) {
            /* cache the data */
            return ResponseEntity.status(HttpStatus.NOT_MODIFIED).build();
        }

        /* send back new data */
        return new ResponseEntity<CollectionModel<Tour>>(data, new HttpHeaders(), HttpStatus.OK);

    }

    @GetMapping("/strong-etag")
    public ResponseEntity getToursWithStrongETag(WebRequest request) {

        /* prepare the data */
        List<Tour> tours = repository.getTours();
        CollectionModel<Tour> data = CollectionModel.of(tours);

        /* check the last modified date */
        if (request.checkNotModified(getStrongETag())) {
            /* cache the data */
            return ResponseEntity.status(HttpStatus.NOT_MODIFIED).build();
        }

        /* send back new data */
        return new ResponseEntity<CollectionModel<Tour>>(data, new HttpHeaders(), HttpStatus.OK);

    }

    @PostMapping("")
    public ResponseEntity addTour(@RequestBody Tour tour) {
        repository.addTour(tour);
        lastModified = new Date();

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateTour(@PathVariable String id, @RequestBody Tour tour) {
        repository.updateTour(id, tour);
        lastModified = new Date();
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteTour(@PathVariable String id) {
        repository.deleteTour(id);
        lastModified = new Date();
    }

}
