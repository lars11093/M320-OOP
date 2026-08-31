/**
 * Ein Passagier existiert unabhängig von jedem einzelnen Flug - er wird
 * separat erzeugt und kann auf mehreren Flügen mitreisen. Genau das macht
 * die Beziehung zu Flug zu einer AGGREGATION statt einer Komposition
 * (siehe Flug.java).
 */
public class Passagier {

    private final String name;
    private final String passnummer;
    private final double gepaeckKg;

    public Passagier(String name, String passnummer, double gepaeckKg) {
        this.name = name;
        this.passnummer = passnummer;
        this.gepaeckKg = gepaeckKg;
    }

    public String getName() {
        return name;
    }

    public String getPassnummer() {
        return passnummer;
    }

    public double getGepaeckKg() {
        return gepaeckKg;
    }

    @Override
    public String toString() {
        return name + " (Pass " + passnummer + ", " + gepaeckKg + "kg Gepäck)";
    }
}
