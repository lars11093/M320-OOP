/**
 * Repräsentiert ein Bankkonto.
 *
 * Kapselung: kontostand ist private und kann nur über einzahlen()/abheben()
 * verändert werden - es gibt bewusst keinen setKontostand().
 */
public class Konto {

    private final String kontonummer;
    private final String inhaber;
    private double kontostand;

    public Konto(String kontonummer, String inhaber, double startguthaben) {
        this.kontonummer = kontonummer;
        this.inhaber = inhaber;
        this.kontostand = startguthaben;
    }

    public void einzahlen(double betrag) {
        if (betrag <= 0) {
            throw new IllegalArgumentException("Einzahlungsbetrag muss positiv sein.");
        }
        kontostand += betrag; // Zustandsänderung des Objekts über eine Methode
    }

    public void abheben(double betrag) {
        if (betrag <= 0) {
            throw new IllegalArgumentException("Abhebungsbetrag muss positiv sein.");
        }
        if (betrag > kontostand) {
            throw new IllegalStateException("Ungenuegender Kontostand.");
        }
        kontostand -= betrag;
    }

    public String getKontonummer() {
        return kontonummer;
    }

    public String getInhaber() {
        return inhaber;
    }

    public double getKontostand() {
        return kontostand;
    }

    @Override
    public String toString() {
        return kontonummer + " (" + inhaber + "): CHF " + kontostand;
    }
}
