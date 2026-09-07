import java.util.ArrayList;
import java.util.List;

/**
 * Verwaltet eine List<Versandart> und arbeitet AUSSCHLIESSLICH über
 * Versandart-Referenzen. Kein instanceof, keine Typabfrage - die konkrete
 * Berechnung/Regel wird per Polymorphie an das jeweilige Objekt delegiert.
 * Neue Versandarten (z.B. Abholstation) brauchen darum keine Änderung hier.
 */
public class VersandVerwaltung {

    private final List<Versandart> versandarten = new ArrayList<>();

    public void add(Versandart versandart) {
        versandarten.add(versandart);
    }

    public boolean removeByCode(String code) {
        return versandarten.removeIf(v -> v.getCode().equals(code));
    }

    public Versandart findByCode(String code) {
        for (Versandart v : versandarten) {
            if (v.getCode().equals(code)) {
                return v;
            }
        }
        return null;
    }

    public void printAlle() {
        for (Versandart v : versandarten) {
            System.out.println(v.printOut());
        }
    }

    /**
     * POLYMORPHIE: ruft akzeptiert() auf jeder Versandart auf, ohne zu
     * wissen, ob es ein StandardVersand, ExpressVersand, KurierVersand oder
     * eine Abholstation ist.
     */
    public List<Versandart> findeMoeglicheVersandarten(double gewichtKg, double distanzKm) {
        List<Versandart> moegliche = new ArrayList<>();
        for (Versandart v : versandarten) {
            if (v.akzeptiert(gewichtKg, distanzKm)) {
                moegliche.add(v);
            }
        }
        return moegliche;
    }

    /**
     * POLYMORPHIE: vergleicht berechneVersandkosten() über beliebig viele
     * verschiedene konkrete Typen hinweg, rein über die gemeinsame
     * Versandart-Schnittstelle.
     */
    public Versandart findeGuenstigsteVariante(double gewichtKg, double distanzKm) {
        Versandart guenstigste = null;
        double minKosten = Double.MAX_VALUE;
        for (Versandart v : findeMoeglicheVersandarten(gewichtKg, distanzKm)) {
            double kosten = v.berechneVersandkosten(gewichtKg, distanzKm);
            if (kosten < minKosten) {
                minKosten = kosten;
                guenstigste = v;
            }
        }
        return guenstigste;
    }
}
