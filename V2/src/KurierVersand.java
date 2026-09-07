/**
 * Preis stark von der Distanz abhängig, nur innerhalb einer maximalen
 * Distanz verfügbar (eigenes Zusatzattribut maxDistanzKm - existiert nur
 * bei dieser Subklasse).
 */
public class KurierVersand extends Versandart {

    private final double preisProKm;
    private final double maxDistanzKm;

    /**
     * Erstellt einen neuen Kurierversand.
     *
     * @param code eindeutiger Kurzcode (z. B. "KUR")
     * @param bezeichnung menschenlesbarer Name
     * @param grundPreis fixer Basispreis in CHF
     * @param maxGewichtKg maximal zulässiges Gewicht in kg
     * @param preisProKm zusätzlicher Preis pro Kilometer Distanz
     * @param maxDistanzKm maximale Distanz in km, ab der diese Versandart nicht mehr verfügbar ist
     */
    public KurierVersand(String code, String bezeichnung, double grundPreis, double maxGewichtKg,
                          double preisProKm, double maxDistanzKm) {
        super(code, bezeichnung, grundPreis, maxGewichtKg);
        this.preisProKm = preisProKm;
        this.maxDistanzKm = maxDistanzKm;
    }

    @Override
    public double berechneVersandkosten(double gewichtKg, double distanzKm) {
        return getGrundPreis() + distanzKm * preisProKm;
    }

    @Override
    public int berechneLieferTage(double distanzKm) {
        return 1;
    }

    @Override
    public boolean akzeptiert(double gewichtKg, double distanzKm) {
        return istAktiv() && gewichtKg <= getMaxGewichtKg() && distanzKm <= maxDistanzKm;
    }
}
