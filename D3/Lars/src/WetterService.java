public class WetterService {

    private final OpenMeteoApi api;

    public WetterService(OpenMeteoApi api) {
        this.api = api;
    }

    public WetterDaten aktuelleDaten(WetterOrt ort) throws java.io.IOException, InterruptedException {
        return api.wetterAbrufen(ort);
    }

    public String kleidungshinweis(WetterDaten daten) {
        if (daten.getWettercode() >= 51 && daten.getWettercode() <= 99) {
            return "Nimm einen Regenschutz mit.";
        }
        if (daten.getTemperatur() < 8) {
            return "Zieh eine warme Jacke an.";
        }
        if (daten.getTemperatur() >= 25) {
            return "Denk an leichte Kleidung und genügend Wasser.";
        }
        return "Eine leichte Jacke könnte angenehm sein.";
    }
}
