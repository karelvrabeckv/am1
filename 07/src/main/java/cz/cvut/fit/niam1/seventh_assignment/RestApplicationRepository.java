package cz.cvut.fit.niam1.seventh_assignment;

import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@Component
public class RestApplicationRepository {

    private static final List<Tour> tours = new ArrayList<Tour>();

    @PostConstruct
    public void initRepo() {
        /* create the first tour */
        List<String> lc1 = new ArrayList<String>();
        lc1.add("Jan Novotny");
        lc1.add("Petr Prochazka");
        lc1.add("Lucie Stastna");
        tours.add(new Tour("0", "Tour to GB", lc1));

        /* create the second tour */
        List<String> lc2 = new ArrayList<String>();
        lc2.add("Iveta Odvazna");
        lc2.add("Ivo Novak");
        lc2.add("Eliska Pospisilova");
        tours.add(new Tour("1", "Tour to US", lc2));
    }

    public List<Tour> getTours() {
        return tours;
    }

    public void addTour(Tour new_t) {
        int max = tours.stream().map(t -> Integer.valueOf(t.getId())).max(Integer::compare).get();
        new_t.setId(String.valueOf(max + 1));
        tours.add(new_t);
    }

    public void updateTour(String id, Tour t) {
        deleteTour(id);
        t.setId(id);
        tours.add(t);
    }

    public void deleteTour(String id) {
        tours.removeIf(t -> t.getId().equals(id));
    }

}
