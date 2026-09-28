import java.util.Locale;

public class WetterEingaben {
    public WetterOrt ortPruefen(String eingabe) throws WetterEingabeException {
        if (eingabe == null || eingabe.trim().isEmpty()) {
            throw new WetterEingabeException("Bitte einen gültigen Ort eingeben: Zürich, Bern oder Basel.");
        }

        String ort = eingabe.trim().toLowerCase(Locale.ROOT).replace("ü", "u");
        return switch (ort) {
            case "zuerich", "zurich" -> new WetterOrt("Zürich", 47.3769, 8.5417);
            case "bern" -> new WetterOrt("Bern", 46.9480, 7.4474);
            case "basel" -> new WetterOrt("Basel", 47.5596, 7.5886);
            default -> throw new WetterEingabeException("Unbekannter Ort. Erlaubt sind Zürich, Bern und Basel.");
        };
    }
}
