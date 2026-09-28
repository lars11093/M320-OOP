import java.io.IOException;

public class WetterAnwendung {
    public void starten() {
        WetterKonsole konsole = new WetterKonsole();
        WetterEingaben eingaben = new WetterEingaben();
        WetterService service = new WetterService(new OpenMeteoApi());

        konsole.zeigeBegruessung();
        while (true) {
            String ortEingabe = konsole.ortAbfragen();
            if (ortEingabe.trim().equalsIgnoreCase("ende")) {
                break;
            }

            try {
                WetterOrt ort = eingaben.ortPruefen(ortEingabe);
                WetterDaten daten = service.aktuelleDaten(ort);
                konsole.zeigeWetter(daten, service.kleidungshinweis(daten));
            } catch (WetterEingabeException e) {
                konsole.zeigeFehler(e.getMessage());
            } catch (IOException e) {
                konsole.zeigeFehler("Wetterdaten konnten nicht geladen werden. Prüfe die Internetverbindung: " + e.getMessage());
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                konsole.zeigeFehler("Die Wetterabfrage wurde unterbrochen.");
            }
        }
        konsole.schliessen();
    }
}
