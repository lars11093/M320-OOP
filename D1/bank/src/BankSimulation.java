import java.util.List;
import java.util.Scanner;

/**
 * Konsolenprogramm für die Bank-Simulation (Kompetenznachweis D1).
 */
public class BankSimulation {

    public static void main(String[] args) {
        Bank bank = new Bank();
        Scanner scanner = new Scanner(System.in);

        // Zwei Testkonten, damit man gleich etwas ausprobieren kann
        Konto a = bank.kontoEroeffnen("Anna Muster", 500);
        Konto b = bank.kontoEroeffnen("Beat Meier", 200);
        System.out.println("Testkonten erstellt: " + a.getKontonummer() + " und " + b.getKontonummer());

        boolean laeuft = true;
        while (laeuft) {
            System.out.println();
            System.out.println("=== Bank-Simulation ===");
            System.out.println("1) Konto eroeffnen");
            System.out.println("2) Einzahlen");
            System.out.println("3) Abheben");
            System.out.println("4) Transfer zwischen zwei Konten");
            System.out.println("5) Alle Konten anzeigen");
            System.out.println("0) Beenden");
            System.out.print("Auswahl: ");
            String eingabe = scanner.nextLine().trim();

            try {
                switch (eingabe) {
                    case "1" -> {
                        System.out.print("Name Inhaber: ");
                        String inhaber = scanner.nextLine().trim();
                        System.out.print("Startguthaben: ");
                        double start = Double.parseDouble(scanner.nextLine().trim());
                        Konto neu = bank.kontoEroeffnen(inhaber, start);
                        System.out.println("Konto eroeffnet: " + neu);
                    }
                    case "2" -> {
                        Konto k = kontoAuswaehlen(bank, scanner);
                        if (k == null) {
                            System.out.println("Konto nicht gefunden.");
                            break;
                        }
                        System.out.print("Betrag: ");
                        double betrag = Double.parseDouble(scanner.nextLine().trim());
                        k.einzahlen(betrag);
                        System.out.println("Neuer Kontostand: " + k.getKontostand());
                    }
                    case "3" -> {
                        Konto k = kontoAuswaehlen(bank, scanner);
                        if (k == null) {
                            System.out.println("Konto nicht gefunden.");
                            break;
                        }
                        System.out.print("Betrag: ");
                        double betrag = Double.parseDouble(scanner.nextLine().trim());
                        k.abheben(betrag);
                        System.out.println("Neuer Kontostand: " + k.getKontostand());
                    }
                    case "4" -> {
                        System.out.print("Kontonummer von: ");
                        Konto von = bank.findeKonto(scanner.nextLine().trim());
                        System.out.print("Kontonummer nach: ");
                        Konto nach = bank.findeKonto(scanner.nextLine().trim());
                        if (von == null || nach == null) {
                            System.out.println("Mindestens ein Konto wurde nicht gefunden (Kontonummer pruefen, z.B. 1000).");
                            break;
                        }
                        System.out.print("Betrag: ");
                        double betrag = Double.parseDouble(scanner.nextLine().trim());
                        bank.transferieren(von, nach, betrag);
                        System.out.println("Transfer abgeschlossen.");
                    }
                    case "5" -> printListe(bank.getAlleKonten());
                    case "0" -> laeuft = false;
                    default -> System.out.println("Ungueltige Eingabe.");
                }
            } catch (Exception e) {
                System.out.println("Fehler: " + e.getMessage());
            }
        }
        scanner.close();
    }

    private static Konto kontoAuswaehlen(Bank bank, Scanner scanner) {
        System.out.print("Kontonummer: ");
        return bank.findeKonto(scanner.nextLine().trim());
    }

    private static void printListe(List<Konto> liste) {
        for (Konto k : liste) {
            System.out.println(" - " + k);
        }
    }
}
