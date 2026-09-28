import java.util.Locale;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class WährungsKonsole {
    private final Scanner scanner = new Scanner(System.in, StandardCharsets.UTF_8);

    public void zeigeMenue() {
        System.out.println();
        System.out.println("=== Währungsrechner ===");
        System.out.println("1) Betrag umrechnen");
        System.out.println("2) Beenden");
    }

    public String menueEingabe() {
        System.out.print("Auswahl: ");
        return scanner.nextLine();
    }

    public String betragEinlesen() {
        System.out.print("Betrag (z. B. 25.50): ");
        return scanner.nextLine();
    }

    public String quellwaehrungEinlesen() {
        System.out.print("Von Währung (CHF/EUR/USD/GBP): ");
        return scanner.nextLine();
    }

    public String zielwaehrungEinlesen() {
        System.out.print("In Währung (CHF/EUR/USD/GBP): ");
        return scanner.nextLine();
    }

    public void zeigeErgebnis(UmrechnungErgebnis ergebnis) {
        System.out.println(String.format(Locale.GERMANY, "%.2f %s = %.2f %s",
                ergebnis.getBetrag(), ergebnis.getVon(), ergebnis.getResultat(), ergebnis.getNach()));
        System.out.println("Kurs: 1 " + ergebnis.getVon() + " = "
                + ergebnis.getKurs().stripTrailingZeros().toPlainString() + " " + ergebnis.getNach());
        System.out.println("Kursdatum: " + ergebnis.getDatum());
    }

    public void zeigeFehler(String meldung) {
        System.out.println("Fehler: " + meldung);
    }

    public void schliessen() {
        scanner.close();
    }
}
