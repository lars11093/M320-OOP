/**
 * Laptop erbt alle gemeinsamen Attribute/Methoden von Produkt (Aufgabe 3/5)
 * und ergänzt nur die eigenen Attribute ram und speicherplatz.
 */
public class Laptop extends Produkt {

    private final int ram;
    private final int speicherplatz;

    public Laptop(String produktname, String hersteller, double preis,
                  String artikelnummer, int ram, int speicherplatz) {
        super(produktname, hersteller, preis, artikelnummer);
        this.ram = ram;
        this.speicherplatz = speicherplatz;
    }

    public int getRam() {
        return ram;
    }

    public int getSpeicherplatz() {
        return speicherplatz;
    }

    @Override
    public String toString() {
        return super.toString() + " | Laptop: " + ram + "GB RAM, " + speicherplatz + "GB Speicher";
    }
}
