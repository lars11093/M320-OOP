import java.util.List;

/**
 * INTERFACE: Portfolio kennt nur diesen Vertrag, nie eine konkrete Börse.
 * Jede Börse legt selbst fest, welche Aktien kotiert sind und zu welchem
 * Kurs - Zürich hat andere Aktien als London, mit völlig unterschiedlichen
 * internen Datenstrukturen. Für Portfolio spielt das keine Rolle.
 */
public interface StockExchange {

    /** @return der menschenlesbare Name dieser Börse */
    String getName();

    /**
     * Prüft, ob eine Aktie an dieser Börse kotiert (registriert) ist.
     * @param tickerSymbol das Tickersymbol der Aktie (z. B. "NESN")
     * @return {@code true}, wenn die Aktie hier kotiert ist
     */
    boolean istKotiert(String tickerSymbol);

    /**
     * Liefert den aktuellen Kurs einer kotierten Aktie.
     * @param tickerSymbol das Tickersymbol der Aktie
     * @return der aktuelle Kurs
     * @throws IllegalArgumentException wenn die Aktie hier nicht kotiert ist
     */
    double getPreis(String tickerSymbol);

    /** @return alle an dieser Börse kotierten Tickersymbole */
    List<String> getKotierteAktien();
}
