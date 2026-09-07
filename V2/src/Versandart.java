/**
 * Abstrakte Superklasse für alle Versandarten.
 *
 * Enthält alles, was jede Versandart gemeinsam hat (Aufgabe: Attribute).
 * Die drei abstrakten Methoden zwingen jede Subklasse, ihre EIGENE
 * Berechnung/Regel zu liefern (Polymorphie) - VersandVerwaltung ruft diese
 * Methoden später NUR über Versandart-Referenzen auf, ohne zu wissen,
 * um welche konkrete Subklasse es sich handelt.
 */
public abstract class Versandart {

    private final String code;
    private final String bezeichnung;
    private final double grundPreis;
    private final double maxGewichtKg;
    private boolean aktiv;

    protected Versandart(String code, String bezeichnung, double grundPreis, double maxGewichtKg) {
        this.code = code;
        this.bezeichnung = bezeichnung;
        this.grundPreis = grundPreis;
        this.maxGewichtKg = maxGewichtKg;
        this.aktiv = true;
    }

    /** Muss jede Subklasse selbst berechnen - unterschiedliche Preislogik. */
    public abstract double berechneVersandkosten(double gewichtKg, double distanzKm);

    /** Muss jede Subklasse selbst berechnen - unterschiedliche Lieferdauer. */
    public abstract int berechneLieferTage(double distanzKm);

    /** Muss jede Subklasse selbst entscheiden - unterschiedliche Annahmeregeln. */
    public abstract boolean akzeptiert(double gewichtKg, double distanzKm);

    public void aktivieren() {
        aktiv = true;
    }

    public void deaktivieren() {
        aktiv = false;
    }

    public String getCode() {
        return code;
    }

    public String getBezeichnung() {
        return bezeichnung;
    }

    public double getGrundPreis() {
        return grundPreis;
    }

    public double getMaxGewichtKg() {
        return maxGewichtKg;
    }

    public boolean istAktiv() {
        return aktiv;
    }

    public String info() {
        return bezeichnung + " (" + code + ")" + (aktiv ? " [aktiv]" : " [inaktiv]");
    }

    public String printOut() {
        return info() + " | Grundpreis CHF " + grundPreis + " | max. " + maxGewichtKg + " kg";
    }
}
