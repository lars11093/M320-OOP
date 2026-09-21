# Kompetenznachweis Q3 — Graphen & BFS-Suchalgorithmus

Ein Freundschaftsnetzwerk als Graph modelliert (`HashMap<Person, List<Person>>`),
mit beiden geforderten Aufgaben.

## Checkliste

| Aufgabe | Anforderung | Erfüllt durch |
|---|---|---|
| a) | Methode, die den kompletten Freundeskreis abbildet (inkl. Freunde der Freunde) | ✅ `Netzwerk.getFreundeskreis(Person)` — BFS über den ganzen Graphen |
| a) | Such-Funktion: Freundeskreis einer bestimmten Person auflisten | ✅ `Netzwerk.findePerson(String)` + Menüpunkt 1 |
| b) | BFS mit Queue: jemanden mit Auto im Freundeskreis finden | ✅ `Netzwerk.findePersonMitAuto(Person)` — exakt der im Auftrag beschriebene Algorithmus |

## Architektur

```
Person            — Knoten (Name, hatAuto)
Netzwerk          — Graph als HashMap<Person, List<Person>> (Adjazenzliste)
 ├─ getFreundeskreis(start)      Task a: BFS, alle erreichbaren Personen
 ├─ findePerson(name)            Task a: Suchfunktion
 └─ findePersonMitAuto(start)    Task b: BFS mit Queue, stoppt beim ersten Treffer
```

Freundschaft ist als **ungerichteter Graph** modelliert: `freundschaftHinzufuegen(a, b)`
trägt die Kante in beide Richtungen ein (A kennt B, B kennt A) — das entspricht einer
echten Freundschaft besser als eine Einbahnstrasse.

## Ausführen

```
cd Q3/src
javac -encoding UTF-8 *.java
java NetzwerkSimulation
```

## Testnetzwerk

```
Anna - Beat - David (hat Auto) - Elena - Fabio (hat Auto)
       Clara ┘

Giulia - Hannes   (isolierte Zweiergruppe, niemand hat ein Auto)
```

**Getestet:**
- Freundeskreis von Anna → 5 Personen (Beat, Clara, David, Elena, Fabio) — korrekt inkl.
  Freunde von Freunden, obwohl Anna nur mit Beat und Clara direkt befreundet ist.
- Auto-Suche von Anna → findet **David** (2 Schritte entfernt).
- Auto-Suche von Giulia → **"Niemand im Freundeskreis hat ein Auto"** — beweist den
  Fall "Queue wird leer" aus der Aufgabenstellung.

## Task a: Wie der Freundeskreis berechnet wird

```java
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
    besucht.remove(start);
    return besucht;
}
```
Klassischer **Breadth-First-Search**: von der Startperson aus werden zuerst alle direkten
Freunde besucht, dann deren Freunde, dann wieder deren Freunde — bis der ganze
zusammenhängende Teil des Graphen abgelaufen ist. Das `besucht`-Set verhindert, dass wir
jemanden doppelt besuchen (wichtig bei Freundschaftskreisen, die sich überschneiden).

## Task b: Wie die Auto-Suche funktioniert (1:1 nach Aufgabenstellung)

```java
public Person findePersonMitAuto(Person start) {
    Queue<Person> queue = new LinkedList<>(getDirekteFreunde(start));
    Set<Person> besucht = new HashSet<>(queue);
    besucht.add(start);

    while (!queue.isEmpty()) {
        Person aktuell = queue.poll();
        if (aktuell.hatAuto()) {
            return aktuell;               // gefunden, Suche stoppt
        }
        for (Person freund : getDirekteFreunde(aktuell)) {
            if (!besucht.contains(freund)) {
                besucht.add(freund);
                queue.add(freund);          // nicht gefunden -> Freunde in die Queue
            }
        }
    }
    return null;                            // Queue leer -> niemand gefunden
}
```

1. **Queue füllen** mit den direkten Freunden der Startperson.
2. **Erste Person aus der Queue nehmen**, prüfen: hat sie ein Auto?
3. **Nein** → ihre Freunde in die Queue stellen, weitermachen.
4. **Ja** → gefunden, Suche stoppt sofort (kein `false`-Zweig, direktes `return`).
5. **Queue leer** → die `while`-Schleife endet von selbst, `return null` — niemand im
   ganzen (zusammenhängenden) Freundeskreis hat ein Auto.

## Warum eine Queue (FIFO) und kein Stack?

Eine `Queue` verarbeitet Personen in der Reihenfolge, in der sie entdeckt wurden (First In,
First Out) — das garantiert, dass wir **zuerst die nächsten** (direkten) Freunde
durchsuchen, bevor wir zu Freunden von Freunden weitergehen. So findet man immer die
**nächstgelegene** Person mit Auto zuerst, nicht irgendeine zufällig weiter entfernte. Mit
einem `Stack` (LIFO) würde man zuerst tief in einen einzelnen Freundschaftspfad
hineinlaufen, statt die Suche gleichmässig auszubreiten.

## Hinweis zu KI-Einsatz
Diese Implementation wurde mit Unterstützung von Claude (KI) erstellt und anschliessend
selbst durchgearbeitet, um sie in der Besprechung erklären zu können.
