import java.util.ArrayList;
import java.util.List;

/**
 * FLEXIBILITÄT: Portfolio kennt nur das Interface StockExchange, nie eine
 * konkrete Börse. Welche Börse verwendet wird, entscheidet der Aufrufer -
 * Portfolio bekommt sie als Parameter übergeben (Dependency Injection über
 * eine Methode statt über den Konstruktor). Eine neue Börse (z.B. NY)
 * hinzuzufügen bräuchte NULL Änderungen an dieser Klasse.
 */
public class Portfolio {

    private final List<Aktie> aktien = new ArrayList<>();

    public void aktieHinzufuegen(Aktie aktie) {
        aktien.add(aktie);
    }

    public List<Aktie> getAktien() {
        return new ArrayList<>(aktien);
    }

    /**
     * POLYMORPHIE: ruft boerse.istKotiert()/getPreis() auf einer
     * StockExchange-Referenz auf. Zur Laufzeit entscheidet der tatsächliche
     * Objekttyp (ZuercherBoerse, LondonBoerse, ...), welcher Code läuft -
     * Portfolio selbst enthält kein einziges instanceof.
     *
     * @param boerse die Börse, gegen die die aktuellen Kurse abgefragt werden
     * @return der Gesamtwert aller im Portfolio gehaltenen, an dieser Börse
     *         kotierten Aktien
     */
    public double berechneWert(StockExchange boerse) {
        double summe = 0.0;
        for (Aktie a : aktien) {
            if (boerse.istKotiert(a.getTickerSymbol())) {
                summe += boerse.getPreis(a.getTickerSymbol()) * a.getAnzahl();
            }
        }
        return summe;
    }
}
