/**
 * Repräsentiert ein Fahrzeug, das in der Garage zur Reparatur registriert werden kann.
 *
 * Kapselung: Alle Attribute sind private. Der Zustand (repariert / Kosten) kann
 * von aussen nur über die Methode reparieren() verändert werden, nicht durch
 * direktes Setzen der Attribute (kein setReparaturKosten()!).
 */
public class Fahrzeug {

    private final String kennzeichen;
    private final String marke;
    private final String modell;

    private boolean repariert;
    private double reparaturKosten;

    public Fahrzeug(String kennzeichen, String marke, String modell) {
        this.kennzeichen = kennzeichen;
        this.marke = marke;
        this.modell = modell;
        this.repariert = false;
        this.reparaturKosten = 0.0;
    }

    /**
     * Verändert den Zustand (Status) dieses Objekts: markiert das Fahrzeug als
     * repariert und addiert die Kosten. Dies ist der einzige Weg, den Zustand
     * von aussen zu beeinflussen.
     */
    public void reparieren(double kosten) {
        if (kosten < 0) {
            throw new IllegalArgumentException("Reparaturkosten duerfen nicht negativ sein.");
        }
        this.reparaturKosten += kosten;
        this.repariert = true;
    }

    public String getKennzeichen() {
        return kennzeichen;
    }

    public String getMarke() {
        return marke;
    }

    public String getModell() {
        return modell;
    }

    public boolean istRepariert() {
        return repariert;
    }

    public double getReparaturKosten() {
        return reparaturKosten;
    }

    @Override
    public String toString() {
        return marke + " " + modell + " (" + kennzeichen + ")"
                + (repariert ? " - repariert, CHF " + reparaturKosten : " - nicht repariert");
    }
}
