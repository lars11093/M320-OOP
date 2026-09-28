import java.math.BigDecimal;

public class UmrechnungErgebnis {
    private final BigDecimal betrag;
    private final String von;
    private final BigDecimal resultat;
    private final String nach;
    private final BigDecimal kurs;
    private final String datum;

    public UmrechnungErgebnis(BigDecimal betrag, String von, BigDecimal resultat,
                              String nach, BigDecimal kurs, String datum) {
        this.betrag = betrag;
        this.von = von;
        this.resultat = resultat;
        this.nach = nach;
        this.kurs = kurs;
        this.datum = datum;
    }

    public BigDecimal getBetrag() {
        return betrag;
    }

    public String getVon() {
        return von;
    }

    public BigDecimal getResultat() {
        return resultat;
    }

    public String getNach() {
        return nach;
    }

    public BigDecimal getKurs() {
        return kurs;
    }

    public String getDatum() {
        return datum;
    }
}
