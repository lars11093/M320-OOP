import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Zeitplan {

    private final List<Flug> fluege = new ArrayList<>();

    public void flugHinzufuegen(Flug flug) {
        fluege.add(flug);
    }

    public List<Flug> sucheFluegeAb(LocalDate datum) {
        List<Flug> treffer = new ArrayList<>();
        for (Flug f : fluege) {
            if (f.startetAm(datum)) {
                treffer.add(f);
            }
        }
        return treffer;
    }

    public List<Flug> getAlleFluege() {
        return new ArrayList<>(fluege);
    }
}
