package cz.cvut.fit.niam1.sixth_assignment;

import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@Component
public class RestApplicationRepository {

    private static final List<Tour> tours = new ArrayList<Tour>();
    private static final List<Deletion> deletions = new ArrayList<Deletion>();

    @PostConstruct
    public void initRepo() {
        /* create the first tour */
        List<String> c1 = new ArrayList<String>();
        c1.add("Karel Vrabec");
        c1.add("Jan Novak");
        c1.add("Alice Prochazkova");
        tours.add(new Tour("0", "Tour to CZ", "Prague", c1));

        /* create the second tour */
        List<String> c2 = new ArrayList<String>();
        c2.add("Eva Pospisilova");
        c2.add("Jana Nova");
        c2.add("Petra Ucestovana");
        tours.add(new Tour("1", "Tour to US", "Las Vegas", c2));
    }

    public List<Tour> getTours() {
        return tours;
    }

    public Tour getTourById(String id) {
        return tours.stream().filter(t -> t.getId().equals(id)).findAny().orElse(null);
    }

    public String deleteTourAsUser(String tourId) {
        String deletionId = String.valueOf(deletions.size());
        Deletion deletion = new Deletion(deletionId, tourId, "PENDING...");
        deletions.add(deletion);

        return deletionId;
    }

    public void deleteTourAsOperator(String tourId) {
        tours.removeIf(t -> t.getId().equals(tourId));

        for (Deletion d : deletions) {
            if (d.getTourId().equals(tourId)) {
                d.setStatus("DELETED");
            }
        }
    }

    public List<Deletion> getDeletions() {
        return deletions;
    }

    public Deletion getDeletionById(String id) {
        return deletions.stream().filter(d -> d.getId().equals(id)).findAny().orElse(null);
    }

}
