import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Konkrete Börse 2: London (LSE). Komplett andere Aktien als Zürich, aber
 * gleiches Interface - genau das erlaubt Portfolio, beide austauschbar zu
 * verwenden (Polymorphie).
 */
public class LondonBoerse implements StockExchange {

    private final Map<String, Double> kurse = new HashMap<>();

    public LondonBoerse() {
        kurse.put("HSBA", 6.85);   // HSBC
        kurse.put("BP", 4.95);     // BP
        kurse.put("VOD", 0.72);    // Vodafone
        kurse.put("BARC", 2.10);   // Barclays
    }

    @Override
    public String getName() {
        return "London Stock Exchange (LSE)";
    }

    @Override
    public boolean istKotiert(String tickerSymbol) {
        return kurse.containsKey(tickerSymbol);
    }

    @Override
    public double getPreis(String tickerSymbol) {
        if (!istKotiert(tickerSymbol)) {
            throw new IllegalArgumentException(tickerSymbol + " ist an der " + getName() + " nicht kotiert.");
        }
        return kurse.get(tickerSymbol);
    }

    @Override
    public List<String> getKotierteAktien() {
        return new ArrayList<>(kurse.keySet());
    }
}
