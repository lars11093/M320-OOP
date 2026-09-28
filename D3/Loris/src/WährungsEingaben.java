import java.math.BigDecimal;
import java.util.Locale;
import java.util.Set;

public class WährungsEingaben {
    private static final Set<String> WAEHRUNGEN = Set.of("CHF", "EUR", "USD", "GBP");

    public boolean istUmrechnungGewuenscht(String eingabe) throws WährungsEingabeException {
        String auswahl = eingabe == null ? "" : eingabe.trim();
        if (auswahl.equals("1")) {
            return true;
        }
        if (auswahl.equals("2")) {
            return false;
        }
        throw new WährungsEingabeException("Bitte 1 oder 2 eingeben.");
    }

    public UmrechnungsAnfrage anfrageErstellen(String betragEingabe, String vonEingabe,
                                                String nachEingabe) throws WährungsEingabeException {
        BigDecimal betrag;
        try {
            betrag = new BigDecimal(betragEingabe.trim().replace(',', '.'));
        } catch (NumberFormatException | NullPointerException e) {
            throw new WährungsEingabeException("Der Betrag muss eine Zahl sein, zum Beispiel 25.50.");
        }
        if (betrag.signum() <= 0) {
            throw new WährungsEingabeException("Der Betrag muss grösser als 0 sein.");
        }

        String von = waehrungPruefen(vonEingabe);
        String nach = waehrungPruefen(nachEingabe);
        return new UmrechnungsAnfrage(betrag, von, nach);
    }

    private String waehrungPruefen(String eingabe) throws WährungsEingabeException {
        String waehrung = eingabe == null ? "" : eingabe.trim().toUpperCase(Locale.ROOT);
        if (!WAEHRUNGEN.contains(waehrung)) {
            throw new WährungsEingabeException("Erlaubt sind CHF, EUR, USD und GBP.");
        }
        return waehrung;
    }
}
