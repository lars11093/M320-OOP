import java.util.ArrayList;
import java.util.Scanner;

/**
 * Konsolen-Demo für den Online-Shop.
 *
 * Baut mindestens zwei Objekte jeder Subklasse (Aufgabe 8) auf, zeigt die
 * geerbte Methode verkaufen() (Aufgabe 5/10) und lässt Produkte über die
 * Shop-Klasse suchen/verkaufen/entfernen.
 */
public class ShopSimulation {

    public static void main(String[] args) {
        Shop shop = new Shop();
        Scanner scanner = new Scanner(System.in);

        // Je mindestens zwei Objekte pro Subklasse (Aufgabe 8)
        shop.produktHinzufuegen(new Laptop("ThinkPad X1", "Lenovo", 1899.00, "L-1000", 32, 1024));
        shop.produktHinzufuegen(new Laptop("MacBook Air", "Apple", 1299.00, "L-1001", 16, 512));

        shop.produktHinzufuegen(new Smartphone("Galaxy S25", "Samsung", 999.00, "S-2000", 6.2, true));
        shop.produktHinzufuegen(new Smartphone("iPhone 17", "Apple", 1099.00, "S-2001", 6.1, false));

        shop.produktHinzufuegen(new Monitor("UltraSharp U27", "Dell", 429.00, "M-3000", 27.0, "4K"));
        shop.produktHinzufuegen(new Monitor("Odyssey G7", "Samsung", 549.00, "M-3001", 32.0, "QHD"));

        // Neue Subklasse (Aufgabe 11) - ohne dass Shop oder Produkt angepasst werden mussten
        shop.produktHinzufuegen(new Drucker("LaserJet Pro", "HP", 259.00, "D-4000", false, true, 22));
        shop.produktHinzufuegen(new Drucker("EcoTank ET-4850", "Epson", 329.00, "D-4001", true, true, 15));

        boolean laeuft = true;
        while (laeuft) {
            System.out.println();
            System.out.println("=== Online-Shop ===");
            System.out.println("1) Alle Produkte anzeigen");
            System.out.println("2) Produkt suchen (Artikelnummer)");
            System.out.println("3) Produkt verkaufen (Artikelnummer)");
            System.out.println("4) Produkt entfernen (Artikelnummer)");
            System.out.println("5) Beenden");
            System.out.print("Auswahl: ");

            String eingabe = scanner.nextLine().trim();

            switch (eingabe) {
                case "1" -> {
                    ArrayList<Produkt> alle = shop.getAlleProdukte();
                    for (Produkt p : alle) {
                        System.out.println(p);
                    }
                }
                case "2" -> {
                    System.out.print("Artikelnummer: ");
                    String artikelnummer = scanner.nextLine().trim();
                    Produkt gefunden = shop.sucheProdukt(artikelnummer);
                    System.out.println(gefunden != null ? gefunden : "Nicht gefunden.");
                }
                case "3" -> {
                    System.out.print("Artikelnummer: ");
                    String artikelnummer = scanner.nextLine().trim();
                    try {
                        shop.produktVerkaufen(artikelnummer);
                        System.out.println("Verkauft.");
                    } catch (RuntimeException e) {
                        System.out.println("Fehler: " + e.getMessage());
                    }
                }
                case "4" -> {
                    System.out.print("Artikelnummer: ");
                    String artikelnummer = scanner.nextLine().trim();
                    shop.produktEntfernen(artikelnummer);
                    System.out.println("Entfernt (falls vorhanden).");
                }
                case "5" -> laeuft = false;
                default -> System.out.println("Ungueltige Auswahl.");
            }
        }

        scanner.close();
    }
}
