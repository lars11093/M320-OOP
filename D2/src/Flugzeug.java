import java.util.ArrayList;
import java.util.List;

public class Flugzeug {

    private final String kennzeichen;
    private final String typ;
    private final int sitzplaetze;
    private final List<Triebwerk> triebwerke = new ArrayList<>();

    public Flugzeug(String kennzeichen, String typ, int sitzplaetze, int anzahlTriebwerke) {
        this.kennzeichen = kennzeichen;
        this.typ = typ;
        this.sitzplaetze = sitzplaetze;
        for (int i = 1; i <= anzahlTriebwerke; i++) {
            triebwerke.add(new Triebwerk(typ + "-Triebwerk-" + i, 120.0));
        }
    }

    public void starten() {
        for (Triebwerk t : triebwerke) {
            t.starten();
        }
    }

    public String getKennzeichen() {
        return kennzeichen;
    }

    public String getTyp() {
        return typ;
    }

    public int getSitzplaetze() {
        return sitzplaetze;
    }

    public List<Triebwerk> getTriebwerke() {
        return new ArrayList<>(triebwerke);
    }

    @Override
    public String toString() {
        return typ + " (" + kennzeichen + "), " + sitzplaetze + " Sitze, " + triebwerke.size() + " Triebwerke";
    }
}
