import java.io.IOException;
import java.math.BigDecimal;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FrankfurterApi {
    private final HttpClient client = HttpClient.newBuilder()
            .connectTimeout(Duration.ofSeconds(10))
            .build();

    public KursAntwort aktuellenKursAbrufen(String von, String nach)
            throws IOException, InterruptedException {
        String adresse = "https://api.frankfurter.dev/v2/rate/"
                + von.toLowerCase() + "/" + nach.toLowerCase();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(adresse))
                .timeout(Duration.ofSeconds(15))
                .GET()
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        if (response.statusCode() != 200) {
            throw new IOException("Frankfurter antwortete mit HTTP " + response.statusCode() + ".");
        }

        String json = response.body();
        String kursText = textwert(json, "rate");
        String datum = textwert(json, "date");
        return new KursAntwort(new BigDecimal(kursText), datum);
    }

    private String textwert(String json, String schluessel) throws IOException {
        Pattern pattern = Pattern.compile("\\\"" + Pattern.quote(schluessel)
                + "\\\"\\s*:\\s*(?:\\\"([^\\\"]+)\\\"|([0-9]+(?:\\.[0-9]+)?))");
        Matcher matcher = pattern.matcher(json);
        if (!matcher.find()) {
            throw new IOException("In der API-Antwort fehlt der Wert " + schluessel + ".");
        }
        return matcher.group(1) != null ? matcher.group(1) : matcher.group(2);
    }

    public static class KursAntwort {
        private final BigDecimal kurs;
        private final String datum;

        public KursAntwort(BigDecimal kurs, String datum) {
            this.kurs = kurs;
            this.datum = datum;
        }

        public BigDecimal getKurs() {
            return kurs;
        }

        public String getDatum() {
            return datum;
        }
    }
}
