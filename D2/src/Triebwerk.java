/**
 * Ein Triebwerk hat ausserhalb eines Flugzeugs keine sinnvolle Existenz -
 * genau das macht es zum Komposition-Baustein (siehe Flugzeug.java).
 */
public class Triebwerk {

    private final String modell;
    private final double schubKN;
    private boolean laeuft;

    public Triebwerk(String modell, double schubKN) {
        this.modell = modell;
        this.schubKN = schubKN;
        this.laeuft = false;
    }

    public void starten() {
        laeuft = true;
    }

    public void stoppen() {
        laeuft = false;
    }

    public boolean laeuft() {
        return laeuft;
    }

    @Override
    public String toString() {
        return modell + " (" + schubKN + " kN)" + (laeuft ? " [läuft]" : " [aus]");
    }
}
