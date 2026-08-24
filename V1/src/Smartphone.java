/**
 * Smartphone erbt von Produkt und ergänzt displaygroesse und dualSim.
 */
public class Smartphone extends Produkt {

    private final double displaygroesse;
    private final boolean dualSim;

    public Smartphone(String produktname, String hersteller, double preis,
                       String artikelnummer, double displaygroesse, boolean dualSim) {
        super(produktname, hersteller, preis, artikelnummer);
        this.displaygroesse = displaygroesse;
        this.dualSim = dualSim;
    }

    public double getDisplaygroesse() {
        return displaygroesse;
    }

    public boolean isDualSim() {
        return dualSim;
    }

    @Override
    public String toString() {
        return super.toString() + " | Smartphone: " + displaygroesse + "\" Display, Dual-SIM: " + dualSim;
    }
}
