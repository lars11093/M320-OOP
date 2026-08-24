/**
 * Superklasse für alle Produkte im Online-Shop.
 *
 * Enthält alle Attribute, die WIRKLICH für jedes Produkt gelten (Aufgabe 2/3).
 * Alle Attribute sind private - Subklassen greifen nur über die geerbten
 * Getter/Methoden darauf zu, nie direkt (Aufgabe 6).
 */
public class Produkt {

    private final String produktname;
    private final String hersteller;
    private final double preis;
    private final String artikelnummer;
    private boolean anLager;

    public Produkt(String produktname, String hersteller, double preis, String artikelnummer) {
        this.produktname = produktname;
        this.hersteller = hersteller;
        this.preis = preis;
        this.artikelnummer = artikelnummer;
        this.anLager = true;
    }

    public String getProduktname() {
        return produktname;
    }

    public String getHersteller() {
        return hersteller;
    }

    public double getPreis() {
        return preis;
    }

    public String getArtikelnummer() {
        return artikelnummer;
    }

    public boolean isAnLager() {
        return anLager;
    }

    /**
     * Wird von allen Subklassen geerbt (Aufgabe 5), ohne dass sie diese
     * Methode selbst nochmals implementieren müssen.
     */
    public void verkaufen() {
        if (!anLager) {
            throw new IllegalStateException(produktname + " ist nicht an Lager und kann nicht verkauft werden.");
        }
        anLager = false;
    }

    public void einlagern() {
        anLager = true;
    }

    @Override
    public String toString() {
        return produktname + " (" + hersteller + ", " + artikelnummer + ") - CHF " + preis
                + (anLager ? " [an Lager]" : " [verkauft]");
    }
}
