/**
 * Ein Portfolio-Eintrag: welche Aktie, wie viele Stück. Kennt keine Börse
 * und keinen Kurs - das ist bewusst getrennt (Single Responsibility).
 */
public class Aktie {

    private final String tickerSymbol;
    private final String name;
    private final int anzahl;

    public Aktie(String tickerSymbol, String name, int anzahl) {
        this.tickerSymbol = tickerSymbol;
        this.name = name;
        this.anzahl = anzahl;
    }

    public String getTickerSymbol() {
        return tickerSymbol;
    }

    public String getName() {
        return name;
    }

    public int getAnzahl() {
        return anzahl;
    }

    @Override
    public String toString() {
        return anzahl + "x " + name + " (" + tickerSymbol + ")";
    }
}
