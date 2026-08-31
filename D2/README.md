# Kompetenznachweis D2 — HAT-Beziehungen & Delegation

Idee: **Flüge und Zeitplan** (aus der Auftragsliste).

## Lernziele-Checkliste

| # | Lernziel | Erfüllt durch |
|---|---|---|
| 1 | Verschiedene HAT-Beziehungen kennen (lose Kopplung bis starke Abhängigkeit) | ✅ Alle drei Stufen im Code: Assoziation (`Zeitplan`↔`Flug`, `Drucker`), Aggregation (`Flug`↔`Flugzeug`/`Passagier`), Komposition (`Flugzeug`↔`Triebwerk`) |
| 2 | Aggregation und Komposition im Code unterscheiden | ✅ `Flug.java` (Aggregation, Objekte von aussen übergeben) vs. `Flugzeug.java` (Komposition, Objekte selbst im Konstruktor erzeugt) |
| 3 | Aufzeigen, wie ein Aufruf delegiert wird | ✅ `Flugzeug.starten()`, `Zeitplan.sucheFluegeAb()`, `Flug.druckeBoardingpass()` — je ein Delegationsbeispiel |

## Ausführen

```
cd D2/src
javac *.java
java FlugSimulation
```

*(Bei verstümmelten Umlauten in der Konsole: `java -Dfile.encoding=UTF-8 FlugSimulation` — reines Terminal-Codepage-Problem, kein Code-Bug.)*

## Fragen zur Besprechung

### Zeigen Sie auf, welche HAT-Beziehungen Sie im Code verwenden.

| Beziehung | Klassen | Wo im Code | Stärke |
|---|---|---|---|
| **Komposition** | `Flugzeug` *-- `Triebwerk` | `Flugzeug.java` Konstruktor: `triebwerke.add(new Triebwerk(...))` | Stark — Triebwerk existiert nur als Teil genau dieses Flugzeugs |
| **Aggregation** | `Flug` o-- `Flugzeug` | `Flug.java` Konstruktor-Parameter `flugzeug` | Mittel — Flugzeug existiert unabhängig, fliegt mehrere Flüge |
| **Aggregation** | `Flug` o-- `Passagier` | `Flug.passagierHinzufuegen()` | Mittel — Passagier existiert unabhängig, reist auf mehreren Flügen |
| **Assoziation** | `Zeitplan` — `Flug` | `Zeitplan.flugHinzufuegen()`, Liste `fluege` | Lose — Zeitplan verwaltet nur Referenzen, "besitzt" die Flüge nicht |
| **Assoziation** | `Flug` — `Drucker` | `Flug.druckeBoardingpass(Drucker drucker, ...)` | Am losesten — Drucker nur als Parameter, nie als Attribut gespeichert |

### Welche HAT-Beziehungen werden wann verwendet? Erklären Sie mögliche Szenarios.

**Komposition — wenn ein Teil ohne das Ganze keinen Sinn ergibt.**
Ein Triebwerk, das zu keinem Flugzeug gehört, ist im Modell bedeutungslos. Deshalb erzeugt `Flugzeug` seine Triebwerke selbst im Konstruktor — niemand ausserhalb kann sie referenzieren oder einem anderen Flugzeug zuweisen. Szenario: Wird das `Flugzeug`-Objekt "verschrottet" (Garbage Collection), verschwinden auch seine `Triebwerk`-Objekte mit — es gibt keinen Weg, sie vorher "herauszulösen".

**Aggregation — wenn Teile eine eigene Lebensdauer und eigene Identität ausserhalb des Ganzen haben.**
Ein Flugzeug existiert weiter, auch wenn ein bestimmter Flug storniert wird — es fliegt einfach eine andere Route. Ein Passagier existiert weiter, auch wenn er von einem Flug entfernt wird. Szenario: In der Demo fliegt Flugzeug `HB-ABC` sowohl `LX100` als auch `LX300`, und Passagierin Anna ist sowohl auf `LX100` als auch auf `LX200` — beide "Teile" werden zwischen mehreren "Ganzen" geteilt, was bei Komposition unmöglich wäre.

**Assoziation — wenn zwei Objekte sich nur kurz "kennen" oder benutzen, ohne echten Besitz.**
`Zeitplan` braucht Flüge nur, um sie aufzulisten/zu durchsuchen — er verwaltet ihre Daten nicht, verändert sie nicht, "gehört" ihnen nicht. Noch loser: `Drucker` taucht nur als Parameter einer einzigen Methode auf, wird nirgends gespeichert. Szenario: Ich könnte denselben `Drucker` für zehn verschiedene Boardingpässe verschiedenster Flüge verwenden, ohne dass irgendeine Klasse eine dauerhafte Beziehung zu ihm braucht.

**Faustregel für die Unterscheidung:**
> Frage dich: "Kann das Teil-Objekt unabhängig vom Container erzeugt/übergeben werden, und überlebt es, wenn der Container verschwindet?" Ja → Aggregation (oder Assoziation, falls gar keine Sammel-/Container-Beziehung besteht). Nein, das Teil wird nur intern erzeugt und stirbt mit dem Container → Komposition.

## Delegation — drei Beispiele

1. `Flugzeug.starten()` ruft `triebwerk.starten()` auf jedem seiner Triebwerke auf — das Flugzeug delegiert das eigentliche Starten an seine Teile.
2. `Zeitplan.sucheFluegeAb(datum)` ruft `flug.startetAm(datum)` auf, statt selbst auf das private `datum`-Attribut von `Flug` zuzugreifen — die Prüflogik bleibt dort, wo die Daten sind.
3. `Flug.druckeBoardingpass(drucker, passagier)` baut den Text zusammen, delegiert das eigentliche Drucken aber an `drucker.druckeText(text)` — `Flug` weiss nicht, wie gedruckt wird, nur dass es angefragt werden kann.

## Hinweis zu KI-Einsatz
Diese Implementation wurde mit Unterstützung von Claude (KI) erstellt und anschliessend selbst durchgearbeitet, um sie in der Besprechung erklären zu können.
