public class WetterDaten {

    private final WetterOrt ort;
    private final double temperatur;
    private final int luftfeuchtigkeit;
    private final double windgeschwindigkeit;
    private final int wettercode;

    public WetterDaten(WetterOrt ort, double temperatur, int luftfeuchtigkeit,
                       double windgeschwindigkeit, int wettercode) {
        this.ort = ort;
        this.temperatur = temperatur;
        this.luftfeuchtigkeit = luftfeuchtigkeit;
        this.windgeschwindigkeit = windgeschwindigkeit;
        this.wettercode = wettercode;
    }

    public WetterOrt getOrt() {
        return ort;
    }

    public double getTemperatur() {
        return temperatur;
    }

    public int getLuftfeuchtigkeit() {
        return luftfeuchtigkeit;
    }

    public double getWindgeschwindigkeit() {
        return windgeschwindigkeit;
    }

    public int getWettercode() {
        return wettercode;
    }
}
