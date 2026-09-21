import java.io.FileDescriptor;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;
import java.util.Set;

/**
 * Konsolen-Demo für den Freundschafts-Graphen (Kompetenznachweis Q3).
 * Baut ein Beispielnetzwerk auf - inkl. einer isolierten Zweiergruppe
 * (Giulia/Hannes), damit auch der "niemand gefunden"-Fall von Task b
 * vorgeführt werden kann.
 */
public class NetzwerkSimulation {

    public static void main(String[] args) {
        System.setOut(new PrintStream(new FileOutputStream(FileDescriptor.out), true, StandardCharsets.UTF_8));

        Netzwerk netzwerk = new Netzwerk();

        Person anna = new Person("Anna", false);
        Person beat = new Person("Beat", false);
        Person clara = new Person("Clara", false);
        Person david = new Person("David", true);
        Person elena = new Person("Elena", false);
        Person fabio = new Person("Fabio", true);
        Person giulia = new Person("Giulia", false);
        Person hannes = new Person("Hannes", false);

        // Hauptgruppe: Anna - Beat - David (hat Auto) - Elena - Fabio (hat Auto)
        netzwerk.freundschaftHinzufuegen(anna, beat);
        netzwerk.freundschaftHinzufuegen(anna, clara);
        netzwerk.freundschaftHinzufuegen(beat, david);
        netzwerk.freundschaftHinzufuegen(clara, david);
        netzwerk.freundschaftHinzufuegen(david, elena);
        netzwerk.freundschaftHinzufuegen(elena, fabio);

        // Isolierte Zweiergruppe - niemand hat hier ein Auto
        netzwerk.freundschaftHinzufuegen(giulia, hannes);

        Scanner scanner = new Scanner(System.in);
        boolean laeuft = true;
        while (laeuft) {
            System.out.println();
            System.out.println("=== Freundschaftsnetzwerk ===");
            System.out.println("1) Freundeskreis einer Person anzeigen (inkl. Freunde von Freunden)");
            System.out.println("2) Jemanden mit Auto im Freundeskreis suchen");
            System.out.println("3) Beenden");
            System.out.print("Auswahl: ");

            String eingabe = scanner.nextLine().trim();
            switch (eingabe) {
                case "1" -> {
                    Person p = personSuchen(scanner, netzwerk);
                    if (p != null) {
                        Set<Person> kreis = netzwerk.getFreundeskreis(p);
                        System.out.println("Freundeskreis von " + p.getName() + " (" + kreis.size() + " Personen):");
                        for (Person freund : kreis) {
                            System.out.println("  " + freund);
                        }
                    }
                }
                case "2" -> {
                    Person p = personSuchen(scanner, netzwerk);
                    if (p != null) {
                        Person gefunden = netzwerk.findePersonMitAuto(p);
                        if (gefunden == null) {
                            System.out.println("Niemand im Freundeskreis von " + p.getName() + " hat ein Auto.");
                        } else {
                            System.out.println(gefunden.getName() + " hat ein Auto!");
                        }
                    }
                }
                case "3" -> laeuft = false;
                default -> System.out.println("Ungültige Auswahl.");
            }
        }

        scanner.close();
    }

    private static Person personSuchen(Scanner scanner, Netzwerk netzwerk) {
        System.out.print("Name der Person: ");
        String name = scanner.nextLine().trim();
        Person p = netzwerk.findePerson(name);
        if (p == null) {
            System.out.println("Person nicht gefunden.");
        }
        return p;
    }
}
