/**
 * Abstrakte Superklasse für alle Versandarten.
 *
 * <p>Enthält alles, was jede Versandart gemeinsam hat (gemeinsame Attribute).
 * Die drei abstrakten Methoden zwingen jede Subklasse, ihre EIGENE
 * Berechnung/Regel zu liefern (Polymorphie) - {@code VersandVerwaltung} ruft
 * diese Methoden später nur über {@code Versandart}-Referenzen auf, ohne zu
 * wissen, um welche konkrete Subklasse es sich handelt.
 */
public abstract class Versandart {

    private final String code;
    private final String bezeichnung;
    private final double grundPreis;
    private final double maxGewichtKg;
    private boolean aktiv;

    /**
     * Erstellt eine neue Versandart mit den gemeinsamen Basisdaten.
     * Wird von jedem Subklassen-Konstruktor über {@code super(...)} aufgerufen.
     *
     * @param code eindeutiger Kurzcode dieser Versandart (z. B. "STD")
     * @param bezeichnung menschenlesbarer Name (z. B. "Standardversand")
     * @param grundPreis fixer Basispreis in CHF, unabhängig von Gewicht/Distanz
     * @param maxGewichtKg maximal zulässiges Gewicht in kg für diese Versandart
     */
    protected Versandart(String code, String bezeichnung, double grundPreis, double maxGewichtKg) {
        this.code = code;
        this.bezeichnung = bezeichnung;
        this.grundPreis = grundPreis;
        this.maxGewichtKg = maxGewichtKg;
        this.aktiv = true;
    }

    /**
     * Berechnet die Versandkosten für eine konkrete Sendung. Jede Subklasse
     * liefert hier ihre eigene Preislogik (z. B. gewichts- oder
     * distanzabhängig).
     *
     * @param gewichtKg Gewicht der Sendung in Kilogramm
     * @param distanzKm Distanz in Kilometern
     * @return die berechneten Versandkosten in CHF
     */
    public abstract double berechneVersandkosten(double gewichtKg, double distanzKm);

    /**
     * Berechnet die voraussichtliche Lieferdauer. Jede Subklasse liefert
     * hier ihre eigene Regel (z. B. fixe Tage oder distanzabhängig).
     *
     * @param distanzKm Distanz in Kilometern
     * @return die Lieferdauer in Tagen
     */
    public abstract int berechneLieferTage(double distanzKm);

    /**
     * Prüft, ob diese Versandart für eine konkrete Sendung überhaupt
     * verwendet werden darf. Jede Subklasse definiert hier ihre eigene
     * Annahmeregel (z. B. maximales Gewicht, maximale Distanz).
     *
     * @param gewichtKg Gewicht der Sendung in Kilogramm
     * @param distanzKm Distanz in Kilometern
     * @return {@code true}, wenn diese Versandart die Sendung annehmen kann
     */
    public abstract boolean akzeptiert(double gewichtKg, double distanzKm);

    /** Aktiviert diese Versandart wieder (sie erscheint danach wieder in Suchergebnissen). */
    public void aktivieren() {
        aktiv = true;
    }

    /** Deaktiviert diese Versandart (sie erscheint danach in keiner Suche mehr). */
    public void deaktivieren() {
        aktiv = false;
    }

    /** @return der eindeutige Kurzcode dieser Versandart */
    public String getCode() {
        return code;
    }

    /** @return der menschenlesbare Name dieser Versandart */
    public String getBezeichnung() {
        return bezeichnung;
    }

    /** @return der fixe Basispreis in CHF */
    public double getGrundPreis() {
        return grundPreis;
    }

    /** @return das maximal zulässige Gewicht in kg */
    public double getMaxGewichtKg() {
        return maxGewichtKg;
    }

    /** @return {@code true}, wenn diese Versandart aktuell aktiv ist */
    public boolean istAktiv() {
        return aktiv;
    }

    /** @return Kurzbeschreibung: Name, Code und Aktiv-Status */
    public String info() {
        return bezeichnung + " (" + code + ")" + (aktiv ? " [aktiv]" : " [inaktiv]");
    }

    /** @return vollständige Beschreibung inkl. Grundpreis und Maximalgewicht */
    public String printOut() {
        return info() + " | Grundpreis CHF " + grundPreis + " | max. " + maxGewichtKg + " kg";
    }
}
