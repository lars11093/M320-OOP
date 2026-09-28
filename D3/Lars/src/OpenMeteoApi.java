import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class OpenMeteoApi {

    private final HttpClient client = HttpClient.newBuilder()
            .connectTimeout(Duration.ofSeconds(10))
            .build();

    public WetterDaten wetterAbrufen(WetterOrt ort) throws IOException, InterruptedException {
        String adresse = "https://api.open-meteo.com/v1/forecast?latitude="
                + ort.getBreitengrad() + "&longitude=" + ort.getLaengengrad()
                + "&current=temperature_2m,relative_humidity_2m,wind_speed_10m,weather_code&timezone=auto";
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(adresse))
                .timeout(Duration.ofSeconds(15))
                .GET()
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        if (response.statusCode() != 200) {
            throw new IOException("Open-Meteo antwortete mit HTTP " + response.statusCode() + ".");
        }

        String json = response.body();
        return new WetterDaten(
                ort,
                dezimalwert(json, "temperature_2m"),
                (int) dezimalwert(json, "relative_humidity_2m"),
                dezimalwert(json, "wind_speed_10m"),
                (int) dezimalwert(json, "weather_code"));
    }

    private double dezimalwert(String json, String schluessel) throws IOException {
        Pattern pattern = Pattern.compile("\\\"" + Pattern.quote(schluessel)
                + "\\\"\\s*:\\s*(-?\\d+(?:\\.\\d+)?)");
        Matcher matcher = pattern.matcher(json);
        if (!matcher.find()) {
            throw new IOException("In der API-Antwort fehlt der Wert " + schluessel + ".");
        }
        return Double.parseDouble(matcher.group(1));
    }
}
