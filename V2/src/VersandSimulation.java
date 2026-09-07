import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Scanner;

/**
 * Konsolen-Demo für die Versandverwaltung (Kompetenznachweis V2).
 * Baut einen Startbestand aus allen vier Versandarten auf und bietet ein
 * interaktives Menü zum Suchen, Vergleichen und Verwalten von Versandarten.
 */
public class VersandSimulation {

    /** Reine Konsolenklasse mit statischem Einstiegspunkt - keine Instanzen nötig. */
    private VersandSimulation() {
    }

    /**
     * Einstiegspunkt: initialisiert die Versandverwaltung mit Startdaten
     * und startet die interaktive Konsolenschleife.
     *
     * @param args nicht verwendet
     */
    public static void main(String[] args) {
        System.setOut(new PrintStream(System.out, true, StandardCharsets.UTF_8));

        VersandVerwaltung verwaltung = new VersandVerwaltung();

        // Startbestand - eine Instanz jeder Subklasse, inkl. der Erweiterung Abholstation
        verwaltung.add(new StandardVersand("STD", "Standardversand", 5.90, 30.0, 0.50));
        verwaltung.add(new ExpressVersand("EXP", "Expressversand", 12.90, 30.0, 8.00, 1));
        verwaltung.add(new KurierVersand("KUR", "Kurierversand", 9.90, 20.0, 1.20, 50.0));
        verwaltung.add(new Abholstation("ABH", "Abholstation", 0.00, 25.0));

        Scanner scanner = new Scanner(System.in);
        boolean laeuft = true;
        while (laeuft) {
            System.out.println();
            System.out.println("=== Versandverwaltung ===");
            System.out.println("1) Versandart hinzufügen");
            System.out.println("2) Alle Versandarten anzeigen");
            System.out.println("3) Versandart suchen");
            System.out.println("4) Mögliche Versandarten für Gewicht/Distanz anzeigen");
            System.out.println("5) Kosten und Lieferzeit vergleichen");
            System.out.println("6) Günstigste Versandart bestimmen");
            System.out.println("7) Versandart aktivieren / deaktivieren");
            System.out.println("8) Versandart löschen");
            System.out.println("9) Beenden");
            System.out.print("Auswahl: ");

            String eingabe = scanner.nextLine().trim();
            switch (eingabe) {
                case "1" -> versandartHinzufuegen(scanner, verwaltung);
                case "2" -> verwaltung.printAlle();
                case "3" -> {
                    System.out.print("Code: ");
                    String code = scanner.nextLine().trim();
                    Versandart gefunden = verwaltung.findByCode(code);
                    System.out.println(gefunden != null ? gefunden.printOut() : "Nicht gefunden.");
                }
                case "4" -> {
                    double[] eingabeWerte = leseGewichtUndDistanz(scanner);
                    List<Versandart> moegliche = verwaltung.findeMoeglicheVersandarten(eingabeWerte[0], eingabeWerte[1]);
                    if (moegliche.isEmpty()) {
                        System.out.println("Keine passende Versandart gefunden.");
                    } else {
                        for (Versandart v : moegliche) {
                            System.out.println(v.printOut());
                        }
                    }
                }
                case "5" -> {
                    double[] eingabeWerte = leseGewichtUndDistanz(scanner);
                    List<Versandart> moegliche = verwaltung.findeMoeglicheVersandarten(eingabeWerte[0], eingabeWerte[1]);
                    if (moegliche.isEmpty()) {
                        System.out.println("Keine passende Versandart gefunden.");
                    }
                    for (Versandart v : moegliche) {
                        double kosten = v.berechneVersandkosten(eingabeWerte[0], eingabeWerte[1]);
                        int tage = v.berechneLieferTage(eingabeWerte[1]);
                        System.out.printf("  %-20s CHF %6.2f, %d Tag(e)%n", v.getBezeichnung(), kosten, tage);
                    }
                }
                case "6" -> {
                    double[] eingabeWerte = leseGewichtUndDistanz(scanner);
                    Versandart guenstigste = verwaltung.findeGuenstigsteVariante(eingabeWerte[0], eingabeWerte[1]);
                    if (guenstigste == null) {
                        System.out.println("Keine passende Versandart gefunden.");
                    } else {
                        double kosten = guenstigste.berechneVersandkosten(eingabeWerte[0], eingabeWerte[1]);
                        System.out.printf("Günstigste Variante: %s (CHF %.2f)%n", guenstigste.getBezeichnung(), kosten);
                    }
                }
                case "7" -> {
                    System.out.print("Code: ");
                    String code = scanner.nextLine().trim();
                    Versandart v = verwaltung.findByCode(code);
                    if (v == null) {
                        System.out.println("Nicht gefunden.");
                    } else if (v.istAktiv()) {
                        v.deaktivieren();
                        System.out.println("Deaktiviert.");
                    } else {
                        v.aktivieren();
                        System.out.println("Aktiviert.");
                    }
                }
                case "8" -> {
                    System.out.print("Code: ");
                    String code = scanner.nextLine().trim();
                    System.out.println(verwaltung.removeByCode(code) ? "Gelöscht." : "Nicht gefunden.");
                }
                case "9" -> laeuft = false;
                default -> System.out.println("Ungültige Auswahl.");
            }
        }

        scanner.close();
    }

    private static double[] leseGewichtUndDistanz(Scanner scanner) {
        System.out.print("Gewicht (kg): ");
        double gewicht = Double.parseDouble(scanner.nextLine().trim());
        System.out.print("Distanz (km): ");
        double distanz = Double.parseDouble(scanner.nextLine().trim());
        return new double[] { gewicht, distanz };
    }

    private static void versandartHinzufuegen(Scanner scanner, VersandVerwaltung verwaltung) {
        System.out.println("Typ: 1) Standard 2) Express 3) Kurier 4) Abholstation");
        String typ = scanner.nextLine().trim();
        System.out.print("Code: ");
        String code = scanner.nextLine().trim();
        System.out.print("Bezeichnung: ");
        String bezeichnung = scanner.nextLine().trim();
        System.out.print("Grundpreis: ");
        double grundPreis = Double.parseDouble(scanner.nextLine().trim());
        System.out.print("Max. Gewicht (kg): ");
        double maxGewicht = Double.parseDouble(scanner.nextLine().trim());

        switch (typ) {
            case "1" -> {
                System.out.print("Preis pro kg: ");
                double preisProKg = Double.parseDouble(scanner.nextLine().trim());
                verwaltung.add(new StandardVersand(code, bezeichnung, grundPreis, maxGewicht, preisProKg));
            }
            case "2" -> {
                System.out.print("Express-Zuschlag: ");
                double zuschlag = Double.parseDouble(scanner.nextLine().trim());
                System.out.print("Garantierte Tage: ");
                int tage = Integer.parseInt(scanner.nextLine().trim());
                verwaltung.add(new ExpressVersand(code, bezeichnung, grundPreis, maxGewicht, zuschlag, tage));
            }
            case "3" -> {
                System.out.print("Preis pro km: ");
                double preisProKm = Double.parseDouble(scanner.nextLine().trim());
                System.out.print("Max. Distanz (km): ");
                double maxDistanz = Double.parseDouble(scanner.nextLine().trim());
                verwaltung.add(new KurierVersand(code, bezeichnung, grundPreis, maxGewicht, preisProKm, maxDistanz));
            }
            case "4" -> verwaltung.add(new Abholstation(code, bezeichnung, grundPreis, maxGewicht));
            default -> System.out.println("Ungültiger Typ.");
        }
    }
}
