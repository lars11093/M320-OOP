import java.util.Locale;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class WetterKonsole {
    private final Scanner scanner = new Scanner(System.in, StandardCharsets.UTF_8);

    public void zeigeBegruessung() {
        System.out.println("=== Wetterberatung für Zürich, Bern und Basel ===");
        System.out.println("Orte: Zürich, Bern, Basel. Mit 'Ende' beenden.");
    }

    public String ortAbfragen() {
        System.out.print("Ort: ");
        return scanner.nextLine();
    }

    public void zeigeWetter(WetterDaten daten, String hinweis) {
        System.out.println("Wetter in " + daten.getOrt().getName() + ":");
        System.out.println("Temperatur: " + format(daten.getTemperatur()) + " °C");
        System.out.println("Luftfeuchtigkeit: " + daten.getLuftfeuchtigkeit() + " %");
        System.out.println("Wind: " + format(daten.getWindgeschwindigkeit()) + " km/h");
        System.out.println("Kleidungshinweis: " + hinweis);
    }

    public void zeigeFehler(String meldung) {
        System.out.println("Fehler: " + meldung);
    }

    public void schliessen() {
        scanner.close();
    }

    private String format(double wert) {
        return String.format(Locale.GERMANY, "%.1f", wert);
    }
}
