package cz.cvut.fit.niam1.sixth_assignment;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@RestController
@RequestMapping(value = "/tour")
public class RestTourController {

    @Autowired
    RestApplicationRepository repository;

    @GetMapping("")
    @ResponseStatus(HttpStatus.OK)
    public CollectionModel<Tour> getTours() {
        List<Tour> tours = repository.getTours();

        for (Tour t : tours) {
            t.removeLinks();
            t.add(linkTo(RestTourController.class).slash(t.getId()).withRel("Detail"));
        }

        return CollectionModel.of(tours);
    }

    @GetMapping("/{tid}")
    public HttpEntity<Tour> getTour(@PathVariable String tid) {
        Tour t = repository.getTourById(tid);

        t.removeLinks();
        t.add(linkTo(RestTourController.class).slash(tid).withSelfRel());
        t.add(linkTo(RestTourController.class).withRel("List"));
        t.add(linkTo(RestTourController.class).slash(tid).withRel("Delete"));

        return ResponseEntity.status(HttpStatus.OK).body(t);
    }

    @DeleteMapping("/{tid}")
    public HttpEntity<String> deleteTour(@PathVariable String tid, @RequestHeader(required = false, name = "x-authorization") String password) {
        String message;

        if (password != null) {
            repository.deleteTourAsOperator(tid);
            message = "The record was permanently deleted.";
        } else {
            String deletionId = repository.deleteTourAsUser(tid);
            message = "You can track your request here: /deletion/" + deletionId;
        }

        return ResponseEntity.status(HttpStatus.OK).body(message);
    }

}
