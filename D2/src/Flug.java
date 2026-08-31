import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * AGGREGATION (mittlere Kopplung, unabhängige Lebensdauer):
 * - Flugzeug wird von aussen übergeben (Konstruktor-Parameter). Es existiert
 *   unabhängig von diesem einen Flug und kann für mehrere Flüge eingesetzt
 *   werden (siehe FlugSimulation: dasselbe Flugzeug fliegt zwei Flüge).
 * - Passagiere werden von aussen erzeugt und über passagierHinzufuegen()
 *   angehängt/entfernt. Sie existieren unabhängig von diesem Flug (können
 *   auf mehreren Flügen mitreisen).
 * Im Klassendiagramm: hohles (nicht gefülltes) Diamant-Symbol.
 */
public class Flug {

    private final String flugnummer;
    private final String startort;
    private final String zielort;
    private final LocalDate datum;
    private final Flugzeug flugzeug;
    private final List<Passagier> passagiere = new ArrayList<>();

    public Flug(String flugnummer, String startort, String zielort, LocalDate datum, Flugzeug flugzeug) {
        this.flugnummer = flugnummer;
        this.startort = startort;
        this.zielort = zielort;
        this.datum = datum;
        this.flugzeug = flugzeug;
    }

    public void passagierHinzufuegen(Passagier passagier) {
        if (passagiere.size() >= flugzeug.getSitzplaetze()) {
            throw new IllegalStateException("Flug " + flugnummer + " ist ausgebucht.");
        }
        passagiere.add(passagier);
    }

    public void passagierEntfernen(Passagier passagier) {
        passagiere.remove(passagier);
    }

    public List<Passagier> getPassagierliste() {
        return new ArrayList<>(passagiere);
    }

    public boolean startetAm(LocalDate datum) {
        return this.datum.equals(datum);
    }

    /**
     * DELEGATION: Flug startet das Flugzeug nicht selbst - es ruft nur
     * flugzeug.starten() auf und überlässt dem Flugzeug, wie das im Detail
     * passiert (welches wiederum an seine Triebwerke weiterdelegiert).
     */
    public void boardingAbschliessen() {
        flugzeug.starten();
    }

    /**
     * DELEGATION + ASSOZIATION: Der Drucker kommt nur als Parameter herein
     * (kein Attribut von Flug) und der eigentliche Druckvorgang wird an ihn
     * delegiert - Flug weiss nicht, WIE gedruckt wird.
     */
    public void druckeBoardingpass(Drucker drucker, Passagier passagier) {
        String text = "Boardingpass\nFlug: " + flugnummer + "\nPassagier: " + passagier.getName();
        drucker.druckeText(text);
    }

    public String getFlugnummer() {
        return flugnummer;
    }

    @Override
    public String toString() {
        return flugnummer + ": " + startort + " -> " + zielort + " am " + datum
                + " | Flugzeug " + flugzeug.getKennzeichen() + " | " + passagiere.size() + " Passagiere";
    }
}
