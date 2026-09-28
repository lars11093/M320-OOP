import java.math.BigDecimal;

public class UmrechnungsAnfrage {
    private final BigDecimal betrag;
    private final String von;
    private final String nach;

    public UmrechnungsAnfrage(BigDecimal betrag, String von, String nach) {
        this.betrag = betrag;
        this.von = von;
        this.nach = nach;
    }

    public BigDecimal getBetrag() {
        return betrag;
    }

    public String getVon() {
        return von;
    }

    public String getNach() {
        return nach;
    }
}
