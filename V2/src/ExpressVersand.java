/**
 * Teuer, aber schnell. Spezialregel: akzeptiert nur die Hälfte des normalen
 * Maximalgewichts (Express-Fahrzeuge sind kleiner) - zeigt, dass jede
 * Subklasse akzeptiert() völlig eigenständig interpretieren darf.
 */
public class ExpressVersand extends Versandart {

    private final double expressZuschlag;
    private final int garantierteTage;

    public ExpressVersand(String code, String bezeichnung, double grundPreis, double maxGewichtKg,
                           double expressZuschlag, int garantierteTage) {
        super(code, bezeichnung, grundPreis, maxGewichtKg);
        this.expressZuschlag = expressZuschlag;
        this.garantierteTage = garantierteTage;
    }

    @Override
    public double berechneVersandkosten(double gewichtKg, double distanzKm) {
        return getGrundPreis() + expressZuschlag + gewichtKg * 0.5;
    }

    @Override
    public int berechneLieferTage(double distanzKm) {
        return garantierteTage;
    }

    @Override
    public boolean akzeptiert(double gewichtKg, double distanzKm) {
        double expressMaxGewicht = getMaxGewichtKg() / 2.0;
        return istAktiv() && gewichtKg <= expressMaxGewicht;
    }
}
