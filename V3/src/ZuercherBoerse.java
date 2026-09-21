import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Konkrete Börse 1: Zürich (SIX). Eigene Aktienliste, eigene Kurse -
 * Portfolio weiss davon nichts, es sieht nur das StockExchange-Interface.
 */
public class ZuercherBoerse implements StockExchange {

    private final Map<String, Double> kurse = new HashMap<>();

    public ZuercherBoerse() {
        kurse.put("NESN", 95.50);  // Nestlé
        kurse.put("ABBN", 45.20);  // ABB
        kurse.put("SREN", 88.10);  // Swiss Re
        kurse.put("NOVN", 92.30);  // Novartis
    }

    @Override
    public String getName() {
        return "Zürcher Börse (SIX)";
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
