import java.util.ArrayList;
import java.util.List;

/**
 * Verwaltet eine {@code List<Versandart>} und arbeitet AUSSCHLIESSLICH über
 * {@code Versandart}-Referenzen. Kein {@code instanceof}, keine Typabfrage -
 * die konkrete Berechnung/Regel wird per Polymorphie an das jeweilige Objekt
 * delegiert. Neue Versandarten (z. B. {@code Abholstation}) brauchen darum
 * keine Änderung an dieser Klasse.
 */
public class VersandVerwaltung {

    private final List<Versandart> versandarten = new ArrayList<>();

    /** Erstellt eine leere Versandverwaltung ohne registrierte Versandarten. */
    public VersandVerwaltung() {
    }

    /**
     * Fügt eine Versandart der Verwaltung hinzu.
     *
     * @param versandart die hinzuzufügende Versandart (beliebige Subklasse)
     */
    public void add(Versandart versandart) {
        versandarten.add(versandart);
    }

    /**
     * Entfernt die Versandart mit dem angegebenen Code, falls vorhanden.
     *
     * @param code der Code der zu entfernenden Versandart
     * @return {@code true}, wenn eine Versandart entfernt wurde
     */
    public boolean removeByCode(String code) {
        return versandarten.removeIf(v -> v.getCode().equals(code));
    }

    /**
     * Sucht eine Versandart anhand ihres Codes.
     *
     * @param code der gesuchte Code
     * @return die gefundene Versandart, oder {@code null}, wenn keine passt
     */
    public Versandart findByCode(String code) {
        for (Versandart v : versandarten) {
            if (v.getCode().equals(code)) {
                return v;
            }
        }
        return null;
    }

    /** Gibt alle registrierten Versandarten zeilenweise auf der Konsole aus. */
    public void printAlle() {
        for (Versandart v : versandarten) {
            System.out.println(v.printOut());
        }
    }

    /**
     * POLYMORPHIE: ruft {@code akzeptiert()} auf jeder Versandart auf, ohne
     * zu wissen, ob es ein {@code StandardVersand}, {@code ExpressVersand},
     * {@code KurierVersand} oder eine {@code Abholstation} ist.
     *
     * @param gewichtKg Gewicht der Sendung in Kilogramm
     * @param distanzKm Distanz in Kilometern
     * @return alle Versandarten, die diese Sendung annehmen können
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
     * POLYMORPHIE: vergleicht {@code berechneVersandkosten()} über beliebig
     * viele verschiedene konkrete Typen hinweg, rein über die gemeinsame
     * {@code Versandart}-Schnittstelle.
     *
     * @param gewichtKg Gewicht der Sendung in Kilogramm
     * @param distanzKm Distanz in Kilometern
     * @return die günstigste mögliche Versandart, oder {@code null}, wenn keine passt
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
