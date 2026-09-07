/**
 * Günstig, aber langsam - typisches Beispiel für Methodenüberschreibung.
 */
public class StandardVersand extends Versandart {

    private final double preisProKg;

    /**
     * Erstellt einen neuen Standardversand.
     *
     * @param code eindeutiger Kurzcode (z. B. "STD")
     * @param bezeichnung menschenlesbarer Name
     * @param grundPreis fixer Basispreis in CHF
     * @param maxGewichtKg maximal zulässiges Gewicht in kg
     * @param preisProKg zusätzlicher Preis pro Kilogramm Gewicht
     */
    public StandardVersand(String code, String bezeichnung, double grundPreis, double maxGewichtKg, double preisProKg) {
        super(code, bezeichnung, grundPreis, maxGewichtKg);
        this.preisProKg = preisProKg;
    }

    @Override
    public double berechneVersandkosten(double gewichtKg, double distanzKm) {
        return getGrundPreis() + gewichtKg * preisProKg;
    }

    @Override
    public int berechneLieferTage(double distanzKm) {
        return 3 + (int) Math.ceil(distanzKm / 200.0);
    }

    @Override
    public boolean akzeptiert(double gewichtKg, double distanzKm) {
        return istAktiv() && gewichtKg <= getMaxGewichtKg();
    }
}
