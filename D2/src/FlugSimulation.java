import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

/**
 * Demo, die alle drei HAT-Beziehungen und Delegation sichtbar macht.
 */
public class FlugSimulation {

    public static void main(String[] args) {
        // KOMPOSITION: jedes Flugzeug erzeugt seine eigenen Triebwerke selbst.
        Flugzeug a320 = new Flugzeug("HB-ABC", "Airbus A320", 3, 2);
        Flugzeug b737 = new Flugzeug("HB-XYZ", "Boeing 737", 3, 2);

        // AGGREGATION: Flugzeuge werden Flügen von aussen zugewiesen - dasselbe
        // Flugzeug (a320) fliegt hier zwei verschiedene Flüge, es "gehört"
        // keinem der beiden exklusiv.
        Flug lx100 = new Flug("LX100", "Zürich", "London", LocalDate.of(2026, 9, 1), a320);
        Flug lx200 = new Flug("LX200", "Zürich", "New York", LocalDate.of(2026, 9, 1), b737);
        Flug lx300 = new Flug("LX300", "Zürich", "Tokio", LocalDate.of(2026, 9, 5), a320);

        // AGGREGATION: Passagiere existieren unabhängig - Anna ist auf zwei
        // verschiedenen Flügen, ihre Existenz hängt an keinem der beiden.
        Passagier anna = new Passagier("Anna Meier", "P1234567", 18.5);
        Passagier ben = new Passagier("Ben Keller", "P7654321", 23.0);
        lx100.passagierHinzufuegen(anna);
        lx100.passagierHinzufuegen(ben);
        lx200.passagierHinzufuegen(anna);

        // ASSOZIATION: Zeitplan verwaltet nur Referenzen auf existierende Flüge.
        Zeitplan zeitplan = new Zeitplan();
        zeitplan.flugHinzufuegen(lx100);
        zeitplan.flugHinzufuegen(lx200);
        zeitplan.flugHinzufuegen(lx300);

        System.out.println("=== Komposition: Flugzeug.starten() delegiert an seine Triebwerke ===");
        a320.starten();
        for (Triebwerk t : a320.getTriebwerke()) {
            System.out.println("  " + t);
        }

        System.out.println();
        System.out.println("=== Aggregation: Passagierliste von LX100 ===");
        for (Passagier p : lx100.getPassagierliste()) {
            System.out.println("  " + p);
        }

        System.out.println();
        System.out.println("=== Aggregation: dasselbe Flugzeug (HB-ABC) fliegt zwei Flüge ===");
        System.out.println("  " + lx100);
        System.out.println("  " + lx300);

        System.out.println();
        System.out.println("=== Assoziation + Delegation: Zeitplan sucht über Flug.startetAm() ===");
        List<Flug> treffer = zeitplan.sucheFluegeAb(LocalDate.of(2026, 9, 1));
        for (Flug f : treffer) {
            System.out.println("  " + f);
        }

        System.out.println();
        System.out.println("=== Assoziation: Drucker nur als Parameter, Druck wird delegiert ===");
        Drucker drucker = new Drucker("HP LaserJet");
        lx100.druckeBoardingpass(drucker, anna);

        // Interaktives Menü
        Scanner scanner = new Scanner(System.in);
        boolean laeuft = true;
        while (laeuft) {
            System.out.println();
            System.out.println("=== Zeitplan-Menü ===");
            System.out.println("1) Alle Flüge anzeigen");
            System.out.println("2) Flüge nach Datum suchen (JJJJ-MM-TT)");
            System.out.println("3) Beenden");
            System.out.print("Auswahl: ");

            String eingabe = scanner.nextLine().trim();
            switch (eingabe) {
                case "1" -> {
                    for (Flug f : zeitplan.getAlleFluege()) {
                        System.out.println(f);
                    }
                }
                case "2" -> {
                    System.out.print("Datum: ");
                    String datumStr = scanner.nextLine().trim();
                    try {
                        LocalDate datum = LocalDate.parse(datumStr);
                        List<Flug> gefunden = zeitplan.sucheFluegeAb(datum);
                        if (gefunden.isEmpty()) {
                            System.out.println("Keine Flüge gefunden.");
                        } else {
                            for (Flug f : gefunden) {
                                System.out.println(f);
                            }
                        }
                    } catch (Exception e) {
                        System.out.println("Ungueltiges Datumsformat (erwartet JJJJ-MM-TT).");
                    }
                }
                case "3" -> laeuft = false;
                default -> System.out.println("Ungueltige Auswahl.");
            }
        }

        scanner.close();
    }
}
