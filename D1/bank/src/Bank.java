import java.util.ArrayList;
import java.util.List;

/**
 * Die Bank verwaltet mehrere Konto-Objekte und koordiniert Transfers.
 *
 * Kommunikation zwischen Objekten: transferieren() ruft abheben() auf dem
 * einen Konto und einzahlen() auf dem anderen Konto auf und übergibt jeweils
 * den Betrag als Wert.
 */
public class Bank {

    private final List<Konto> konten = new ArrayList<>();
    private int naechsteKontonummer = 1000;

    public Konto kontoEroeffnen(String inhaber, double startguthaben) {
        Konto konto = new Konto(String.valueOf(naechsteKontonummer++), inhaber, startguthaben);
        konten.add(konto);
        return konto;
    }

    public void transferieren(Konto von, Konto nach, double betrag) {
        von.abheben(betrag);    // Wert wird an das Objekt "von" übergeben
        nach.einzahlen(betrag); // Wert wird an das Objekt "nach" übergeben
    }

    public Konto findeKonto(String kontonummer) {
        for (Konto k : konten) {
            if (k.getKontonummer().equals(kontonummer)) {
                return k;
            }
        }
        return null;
    }

    public List<Konto> getAlleKonten() {
        return new ArrayList<>(konten);
    }
}
