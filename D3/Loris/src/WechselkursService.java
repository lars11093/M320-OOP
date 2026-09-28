import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;

public class WechselkursService {
    private final FrankfurterApi api;

    public WechselkursService(FrankfurterApi api) {
        this.api = api;
    }

    public UmrechnungErgebnis umrechnen(UmrechnungsAnfrage anfrage)
            throws IOException, InterruptedException {
        BigDecimal kurs;
        String datum;
        if (anfrage.getVon().equals(anfrage.getNach())) {
            kurs = BigDecimal.ONE;
            datum = "gleiche Währung";
        } else {
            FrankfurterApi.KursAntwort antwort = api.aktuellenKursAbrufen(
                    anfrage.getVon(), anfrage.getNach());
            kurs = antwort.getKurs();
            datum = antwort.getDatum();
        }

        BigDecimal resultat = anfrage.getBetrag().multiply(kurs).setScale(2, RoundingMode.HALF_UP);
        return new UmrechnungErgebnis(anfrage.getBetrag(), anfrage.getVon(), resultat,
                anfrage.getNach(), kurs, datum);
    }
}
