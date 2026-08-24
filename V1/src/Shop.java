import java.util.ArrayList;

/**
 * Verwaltet alle Produkte des Online-Shops (Aufgabe 8).
 *
 * Weil Laptop, Smartphone, Monitor und Drucker alle "IST-EIN" Produkt sind
 * (Vererbung), können Objekte aller vier Klassen in derselben
 * ArrayList<Produkt> gespeichert werden (Polymorphie, siehe Aufgabe 8/9).
 */
public class Shop {

    private final ArrayList<Produkt> produkte = new ArrayList<>();

    public void produktHinzufuegen(Produkt produkt) {
        produkte.add(produkt);
    }

    public Produkt sucheProdukt(String artikelnummer) {
        for (Produkt p : produkte) {
            if (p.getArtikelnummer().equals(artikelnummer)) {
                return p;
            }
        }
        return null;
    }

    /**
     * Sucht das Produkt und verwendet dann dessen geerbte Methode verkaufen()
     * (Aufgabe 10) - Shop weiss nicht, ob es ein Laptop, Smartphone, Monitor
     * oder Drucker ist, und muss es auch nicht wissen.
     */
    public void produktVerkaufen(String artikelnummer) {
        Produkt produkt = sucheProdukt(artikelnummer);
        if (produkt == null) {
            throw new IllegalArgumentException("Kein Produkt mit Artikelnummer " + artikelnummer + " gefunden.");
        }
        produkt.verkaufen();
    }

    public void produktEntfernen(String artikelnummer) {
        Produkt produkt = sucheProdukt(artikelnummer);
        if (produkt != null) {
            produkte.remove(produkt);
        }
    }

    public ArrayList<Produkt> getAlleProdukte() {
        return new ArrayList<>(produkte);
    }
}
