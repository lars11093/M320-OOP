import java.io.FileDescriptor;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

/**
 * Konsolen-Demo für das Aktien-Portfolio (Kompetenznachweis V3).
 * Die Testklasse übergibt dem Portfolio je nach Benutzerwahl eine andere
 * StockExchange-Implementierung - Portfolio selbst weiss nicht, welche.
 */
public class PortfolioSimulation {

    public static void main(String[] args) {
        // Konsole fest auf UTF-8 - siehe D1/D2/V1/V2 fuer den Hintergrund.
        System.setOut(new PrintStream(new FileOutputStream(FileDescriptor.out), true, StandardCharsets.UTF_8));

        // Zwei konkrete Boersen "verstecken" sich hinter demselben Interface.
        StockExchange zuerich = new ZuercherBoerse();
        StockExchange london = new LondonBoerse();

        Portfolio portfolio = new Portfolio();
        portfolio.aktieHinzufuegen(new Aktie("NESN", "Nestlé", 10));
        portfolio.aktieHinzufuegen(new Aktie("ABBN", "ABB", 20));
        portfolio.aktieHinzufuegen(new Aktie("HSBA", "HSBC", 50));
        portfolio.aktieHinzufuegen(new Aktie("VOD", "Vodafone", 100));

        Scanner scanner = new Scanner(System.in);
        boolean laeuft = true;
        while (laeuft) {
            System.out.println();
            System.out.println("=== Portfolio-Verwaltung ===");
            System.out.println("1) Portfolio-Wert anzeigen");
            System.out.println("2) Aktienkurs abfragen");
            System.out.println("3) Portfolio-Inhalt anzeigen");
            System.out.println("4) Beenden");
            System.out.print("Auswahl: ");

            String eingabe = scanner.nextLine().trim();
            switch (eingabe) {
                case "1" -> {
                    StockExchange boerse = boerseAuswaehlen(scanner, zuerich, london);
                    if (boerse != null) {
                        double wert = portfolio.berechneWert(boerse);
                        System.out.printf("Portfolio-Wert an der %s: %.2f%n", boerse.getName(), wert);
                    }
                }
                case "2" -> {
                    StockExchange boerse = boerseAuswaehlen(scanner, zuerich, london);
                    if (boerse != null) {
                        System.out.print("Ticker-Symbol: ");
                        String ticker = scanner.nextLine().trim().toUpperCase();
                        try {
                            double preis = boerse.getPreis(ticker);
                            System.out.printf("%s an der %s: %.2f%n", ticker, boerse.getName(), preis);
                        } catch (IllegalArgumentException e) {
                            System.out.println(e.getMessage());
                        }
                    }
                }
                case "3" -> {
                    for (Aktie a : portfolio.getAktien()) {
                        System.out.println(a);
                    }
                }
                case "4" -> laeuft = false;
                default -> System.out.println("Ungültige Auswahl.");
            }
        }

        scanner.close();
    }

    private static StockExchange boerseAuswaehlen(Scanner scanner, StockExchange zuerich, StockExchange london) {
        System.out.println("Börse wählen: 1) " + zuerich.getName() + "  2) " + london.getName());
        String auswahl = scanner.nextLine().trim();
        return switch (auswahl) {
            case "1" -> zuerich;
            case "2" -> london;
            default -> {
                System.out.println("Ungültige Auswahl.");
                yield null;
            }
        };
    }
}
