/**
 * Ein Knoten im Freundschafts-Graphen.
 */
public class Person {

    private final String name;
    private final boolean hatAuto;

    public Person(String name, boolean hatAuto) {
        this.name = name;
        this.hatAuto = hatAuto;
    }

    public String getName() {
        return name;
    }

    public boolean hatAuto() {
        return hatAuto;
    }

    @Override
    public String toString() {
        return name + (hatAuto ? " (hat Auto)" : "");
    }
}
