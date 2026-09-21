import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

/**
 * Der Graph: Personen sind Knoten, Freundschaften sind Kanten. Wird als
 * HashMap&lt;Person, List&lt;Person&gt;&gt; abgebildet (Adjazenzliste) - genau
 * wie im Auftrag beschrieben. Freundschaft ist hier bewusst gegenseitig
 * (ungerichteter Graph): wenn A mit B befreundet ist, ist auch B mit A
 * befreundet.
 */
public class Netzwerk {

    private final Map<Person, List<Person>> freundschaften = new HashMap<>();

    public void personHinzufuegen(Person person) {
        freundschaften.putIfAbsent(person, new ArrayList<>());
    }

    public void freundschaftHinzufuegen(Person a, Person b) {
        personHinzufuegen(a);
        personHinzufuegen(b);
        freundschaften.get(a).add(b);
        freundschaften.get(b).add(a);
    }

    public List<Person> getDirekteFreunde(Person person) {
        return freundschaften.getOrDefault(person, new ArrayList<>());
    }

    /**
     * TASK A: Suchfunktion - findet eine Person anhand ihres Namens.
     */
    public Person findePerson(String name) {
        for (Person p : freundschaften.keySet()) {
            if (p.getName().equalsIgnoreCase(name)) {
                return p;
            }
        }
        return null;
    }

    /**
     * TASK A: der komplette Freundeskreis - inkl. Freunde der Freunde,
     * beliebig tief. Klassischer Graphen-Durchlauf (BFS) mit einer Queue:
     * wir starten bei der Person, besuchen alle direkten Freunde, und von
     * dort wieder deren Freunde, bis nichts Neues mehr dazukommt. Ein
     * "besucht"-Set verhindert Endlosschleifen bei Kreisen im Netzwerk.
     */
    public Set<Person> getFreundeskreis(Person start) {
        Set<Person> besucht = new HashSet<>();
        Queue<Person> queue = new LinkedList<>();
        queue.add(start);
        besucht.add(start);

        while (!queue.isEmpty()) {
            Person aktuell = queue.poll();
            for (Person freund : getDirekteFreunde(aktuell)) {
                if (!besucht.contains(freund)) {
                    besucht.add(freund);
                    queue.add(freund);
                }
            }
        }

        besucht.remove(start); // man ist nicht sein eigener Freund
        return besucht;
    }

    /**
     * TASK B: Sucht im Freundeskreis (inkl. Freunde von Freunden) nach der
     * ERSTEN Person mit Auto - exakt nach dem im Auftrag beschriebenen
     * Algorithmus:
     * 1) Queue mit den direkten Freunden der Startperson füllen.
     * 2) Erste Person aus der Queue nehmen, prüfen ob sie ein Auto hat.
     * 3) Falls nicht: ihre Freunde in die Queue stellen, weitersuchen.
     * 4) Falls ja: gefunden, Suche stoppen.
     * 5) Falls die Queue leer wird: niemand im Freundeskreis hat ein Auto.
     *
     * @param start die Person, deren Freundeskreis durchsucht wird
     * @return die erste gefundene Person mit Auto, oder {@code null}, wenn
     *         niemand im gesamten Freundeskreis eines hat
     */
    public Person findePersonMitAuto(Person start) {
        Queue<Person> queue = new LinkedList<>(getDirekteFreunde(start));
        Set<Person> besucht = new HashSet<>(queue);
        besucht.add(start);

        while (!queue.isEmpty()) {
            Person aktuell = queue.poll();
            if (aktuell.hatAuto()) {
                return aktuell;
            }
            for (Person freund : getDirekteFreunde(aktuell)) {
                if (!besucht.contains(freund)) {
                    besucht.add(freund);
                    queue.add(freund);
                }
            }
        }

        return null; // Queue leer -> keine Person mit Auto gefunden
    }
}
