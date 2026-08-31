import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * ASSOZIATION (loseste Kopplung): Zeitplan verwaltet nur Referenzen auf
 * bereits existierende Flug-Objekte, um sie aufzulisten und zu durchsuchen.
 * Zeitplan erzeugt keine Flüge selbst und "besitzt" sie nicht - ein Flug
 * könnte genauso gut ganz ohne Zeitplan existieren. Kein Diamant im
 * Klassendiagramm, nur eine einfache Linie.
 */
public class Zeitplan {

    private final List<Flug> fluege = new ArrayList<>();

    public void flugHinzufuegen(Flug flug) {
        fluege.add(flug);
    }

    /**
     * DELEGATION: Zeitplan prüft nicht selbst, ob ein Flug an einem
     * bestimmten Datum startet - das weiss nur der Flug selbst (dessen
     * Attribut datum ist private). Zeitplan delegiert die Prüfung an
     * Flug.startetAm().
     */
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
