package cz.cvut.fit.niam1.sixth_assignment;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@RestController
@RequestMapping(value = "/deletion")
public class RestDeletionController {

    @Autowired
    RestApplicationRepository repository;

    @GetMapping("")
    @ResponseStatus(HttpStatus.OK)
    public CollectionModel<Deletion> getDeletions() {
        List<Deletion> deletions = repository.getDeletions();

        for (Deletion d : deletions) {
            d.removeLinks();
            d.add(linkTo(RestDeletionController.class).slash(d.getId()).withRel("Detail"));
        }

        return CollectionModel.of(deletions);
    }

    @GetMapping("/{did}")
    public HttpEntity<Deletion> getDeletion(@PathVariable String did) {
        Deletion d = repository.getDeletionById(did);

        d.removeLinks();
        d.add(linkTo(RestDeletionController.class).slash(did).withSelfRel());
        d.add(linkTo(RestDeletionController.class).withRel("List"));

        return ResponseEntity.status(HttpStatus.OK).body(d);
    }

}
