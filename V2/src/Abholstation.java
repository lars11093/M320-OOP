/**
 * ERWEITERUNG (Skalierbarkeit): eine vierte Versandart, ohne dass
 * VersandVerwaltung oder die bestehende Such-/Vergleichslogik angepasst
 * werden mussten - genau das beweist, dass die Polymorphie-Architektur
 * neue Typen "for free" unterstützt.
 */
public class Abholstation extends Versandart {

    public Abholstation(String code, String bezeichnung, double grundPreis, double maxGewichtKg) {
        super(code, bezeichnung, grundPreis, maxGewichtKg);
    }

    @Override
    public double berechneVersandkosten(double gewichtKg, double distanzKm) {
        return getGrundPreis(); // Distanz spielt keine Rolle - Kunde holt selbst ab
    }

    @Override
    public int berechneLieferTage(double distanzKm) {
        return 0; // sofort abholbereit
    }

    @Override
    public boolean akzeptiert(double gewichtKg, double distanzKm) {
        return istAktiv() && gewichtKg <= getMaxGewichtKg(); // keine Distanzbegrenzung
    }
}
