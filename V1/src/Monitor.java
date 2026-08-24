/**
 * Monitor erbt von Produkt und ergänzt bildschirmdiagonale und aufloesung.
 */
public class Monitor extends Produkt {

    private final double bildschirmdiagonale;
    private final String aufloesung;

    public Monitor(String produktname, String hersteller, double preis,
                    String artikelnummer, double bildschirmdiagonale, String aufloesung) {
        super(produktname, hersteller, preis, artikelnummer);
        this.bildschirmdiagonale = bildschirmdiagonale;
        this.aufloesung = aufloesung;
    }

    public double getBildschirmdiagonale() {
        return bildschirmdiagonale;
    }

    public String getAufloesung() {
        return aufloesung;
    }

    @Override
    public String toString() {
        return super.toString() + " | Monitor: " + bildschirmdiagonale + "\" " + aufloesung;
    }
}
