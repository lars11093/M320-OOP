public class WetterOrt {

    private final String name;
    private final double breitengrad;
    private final double laengengrad;

    public WetterOrt(String name, double breitengrad, double laengengrad) {
        this.name = name;
        this.breitengrad = breitengrad;
        this.laengengrad = laengengrad;
    }

    public String getName() {
        return name;
    }

    public double getBreitengrad() {
        return breitengrad;
    }

    public double getLaengengrad() {
        return laengengrad;
    }
}
