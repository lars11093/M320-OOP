import java.util.List;
import java.util.Scanner;

/**
 * Konsolenprogramm für die Garagen-Simulation (Kompetenznachweis D1).
 */
public class GaragenSimulation {

    public static void main(String[] args) {
        Garage garage = new Garage();
        Scanner scanner = new Scanner(System.in);

        // Ein paar Fahrzeuge vorregistrieren, damit man gleich etwas testen kann
        garage.fahrzeugRegistrieren(new Fahrzeug("ZH123456", "VW", "Golf"));
        garage.fahrzeugRegistrieren(new Fahrzeug("BE987654", "Toyota", "Yaris"));

        boolean laeuft = true;
        while (laeuft) {
            System.out.println();
            System.out.println("=== Garagen-Simulation ===");
            System.out.println("1) Fahrzeug registrieren");
            System.out.println("2) Fahrzeug reparieren");
            System.out.println("3) Alle Fahrzeuge anzeigen");
            System.out.println("4) Reparierte Fahrzeuge anzeigen");
            System.out.println("5) Gesamtkosten anzeigen");
            System.out.println("0) Beenden");
            System.out.print("Auswahl: ");
            String eingabe = scanner.nextLine().trim();

            switch (eingabe) {
                case "1" -> {
                    System.out.print("Kennzeichen: ");
                    String kennzeichen = scanner.nextLine().trim();
                    System.out.print("Marke: ");
                    String marke = scanner.nextLine().trim();
                    System.out.print("Modell: ");
                    String modell = scanner.nextLine().trim();
                    garage.fahrzeugRegistrieren(new Fahrzeug(kennzeichen, marke, modell));
                    System.out.println("Fahrzeug registriert.");
                }
                case "2" -> {
                    System.out.print("Kennzeichen: ");
                    String kennzeichen = scanner.nextLine().trim();
                    System.out.print("Reparaturkosten: ");
                    double kosten = Double.parseDouble(scanner.nextLine().trim());
                    boolean erfolgreich = garage.fahrzeugReparieren(kennzeichen, kosten);
                    System.out.println(erfolgreich ? "Fahrzeug repariert." : "Fahrzeug nicht gefunden.");
                }
                case "3" -> printListe(garage.getAlleFahrzeuge());
                case "4" -> printListe(garage.getReparierteFahrzeuge());
                case "5" -> System.out.println("Gesamtkosten: CHF " + garage.getGesamtkosten());
                case "0" -> laeuft = false;
                default -> System.out.println("Ungueltige Eingabe.");
            }
        }
        scanner.close();
    }

    private static void printListe(List<Fahrzeug> liste) {
        if (liste.isEmpty()) {
            System.out.println("(keine Fahrzeuge)");
            return;
        }
        for (Fahrzeug f : liste) {
            System.out.println(" - " + f);
        }
    }
}
