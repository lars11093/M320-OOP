/**
 * Neue Subklasse (Aufgabe 11): Drucker erbt von Produkt wie die anderen drei
 * Produkttypen - das zeigt, dass die Hierarchie ohne Änderung an Produkt
 * oder Shop erweiterbar ist.
 */
public class Drucker extends Produkt {

    private final boolean farbdruck;
    private final boolean duplex;
    private final int seitenProMinute;

    public Drucker(String produktname, String hersteller, double preis, String artikelnummer,
                    boolean farbdruck, boolean duplex, int seitenProMinute) {
        super(produktname, hersteller, preis, artikelnummer);
        this.farbdruck = farbdruck;
        this.duplex = duplex;
        this.seitenProMinute = seitenProMinute;
    }

    public boolean isFarbdruck() {
        return farbdruck;
    }

    public boolean isDuplex() {
        return duplex;
    }

    public int getSeitenProMinute() {
        return seitenProMinute;
    }

    @Override
    public String toString() {
        return super.toString() + " | Drucker: " + (farbdruck ? "Farbe" : "S/W")
                + ", " + (duplex ? "Duplex" : "Simplex") + ", " + seitenProMinute + " Seiten/min";
    }
}
