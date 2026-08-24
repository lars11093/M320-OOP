import java.util.ArrayList;
import java.util.List;

/**
 * Die Garage verwaltet mehrere Fahrzeug-Objekte.
 *
 * Kommunikation zwischen Objekten: fahrzeugReparieren() sucht das passende
 * Fahrzeug-Objekt und ruft dessen Methode reparieren(kosten) auf - die Garage
 * übergibt also einen Wert (kosten) an ein anderes Objekt.
 */
public class Garage {

    private final List<Fahrzeug> fahrzeuge = new ArrayList<>();

    public void fahrzeugRegistrieren(Fahrzeug fahrzeug) {
        fahrzeuge.add(fahrzeug);
    }

    public boolean fahrzeugReparieren(String kennzeichen, double kosten) {
        Fahrzeug fahrzeug = findeFahrzeug(kennzeichen);
        if (fahrzeug == null) {
            return false;
        }
        fahrzeug.reparieren(kosten); // Kommunikation: Methodenaufruf mit übergebenem Wert
        return true;
    }

    public List<Fahrzeug> getReparierteFahrzeuge() {
        List<Fahrzeug> result = new ArrayList<>();
        for (Fahrzeug f : fahrzeuge) {
            if (f.istRepariert()) {
                result.add(f);
            }
        }
        return result;
    }

    public double getGesamtkosten() {
        double summe = 0.0;
        for (Fahrzeug f : fahrzeuge) {
            summe += f.getReparaturKosten();
        }
        return summe;
    }

    public List<Fahrzeug> getAlleFahrzeuge() {
        return new ArrayList<>(fahrzeuge);
    }

    private Fahrzeug findeFahrzeug(String kennzeichen) {
        for (Fahrzeug f : fahrzeuge) {
            if (f.getKennzeichen().equalsIgnoreCase(kennzeichen)) {
                return f;
            }
        }
        return null;
    }
}
