# Kompetenznachweis M3 — Klassendiagramm: Interfaces vs. starre Hierarchie

Gewählt: **Beschreibung B — Hamburger-Bestellung**, gelöst mit dem **Decorator-Pattern**.

## Lernziele-Checkliste

| # | Lernziel | Erfüllt durch |
|---|---|---|
| 1 | Klassendiagramm aus Beschreibung ableiten, starre Hierarchie von flexiblem Interface-Design unterscheiden | ✅ `klassendiagramm-hamburger.puml` enthält BEIDE Varianten nebeneinander — Decorator (oben) vs. rot markierte Vererbungshierarchie (unten) |
| 2 | Wichtigste Symbole für Interfaces im UML-Klassendiagramm kennen | ✅ Abschnitt "UML-Symbole" unten |
| 3 | Aufzeigen, wieso Interfaces die Erweiterung von Code flexibler machen | ✅ Abschnitt "Fragen beim Gespräch", Punkt 1 + 3 |

## Warum Beschreibung B (Decorator) statt A (Zahlungsarten)?
Beschreibung A (Kreditkarte/Postfinance/Debit) wäre auch ein sauberes Interface-Beispiel
(`Zahlungsmethode`-Interface, drei Implementierungen), zeigt aber nur EINE Ebene.
Beschreibung B zeigt zusätzlich, wie sich Decorators **verschachteln** lassen (Zutat auf
Zutat auf Zutat) und erlaubt den direkten Vergleich mit einer starren Vererbungshierarchie,
die bei derselben Anforderung an ihre Grenzen stösst (Kombinationsexplosion) — das trifft
Lernziel 1 direkter.

## Architektur

```
Bestellbar (Interface)
 ├─ Patty                          implementiert Bestellbar direkt
 └─ ZutatDecorator (abstract)      implementiert Bestellbar UND hat ein Bestellbar
     ├─ ExtraZwiebeln
     ├─ ExtraSauce
     ├─ ExtraBacon
     └─ MenuDecorator              (fügt Getränk + FrenchFries hinzu)
```

**Wie eine Bestellung entsteht (Beispiel):**
```
new MenuDecorator(
    new ExtraBacon(
        new ExtraZwiebeln(
            new Patty())))
```
Jede Schicht "umhüllt" die vorherige und addiert ihren eigenen Preis/Beschreibungstext
dazu. Der Kunde bestellt am Ende einfach ein `Bestellbar` — ob dahinter ein reiner Patty
oder ein zehnfach dekorierter Burger steckt, ist für den aufrufenden Code egal.

## UML-Symbole für Interfaces (Lernziel 2)

| Symbol | Bedeutung |
|---|---|
| `interface Bestellbar { ... }` mit `<<interface>>`-Stereotyp | kennzeichnet ein Interface (keine Attribute, nur Methodensignaturen) |
| Gestrichelter Pfeil mit **hohler** Pfeilspitze (`<|..`) | **Realisierung** — eine Klasse implementiert ein Interface (IST-Beziehung, aber "verspricht nur die Schnittstelle einzuhalten") |
| Durchgezogener Pfeil mit **hohler** Pfeilspitze (`<|--`) | **Vererbung** — eine Klasse erbt von einer anderen Klasse (IST-Beziehung mit geerbter Implementierung) |
| Linie mit **hohlem Diamant** (`o--`) | **Aggregation** — HAT-Beziehung, hier: der Decorator hält eine Referenz auf ein anderes `Bestellbar`-Objekt |

## Fragen beim Gespräch

### Wieso sind Interfaces häufig besser als eine Vererbung?
Vererbung koppelt zwei Klassen sehr eng: die Subklasse übernimmt die komplette
Implementierung der Superklasse, auch Dinge, die sie gar nicht braucht oder die
für sie unpassend sind. Ausserdem erlaubt Java pro Klasse nur **eine** Superklasse
(Einfachvererbung) — ein `ExtraBacon`, das gleichzeitig "ein Zutat-Decorator" UND
"ein Grillprodukt" sein müsste, ginge mit Vererbung nicht.

Ein Interface legt dagegen nur eine **Schnittstelle** (Verträge: welche Methoden
muss es geben) fest, ohne Implementierung vorzuschreiben. Eine Klasse kann
**mehrere** Interfaces implementieren, bleibt aber frei, wie sie die Methoden
umsetzt. Das macht das Design lose gekoppelt: neue Klassen (wie unsere vier
Decorators) lassen sich hinzufügen, ohne bestehenden Code zu verändern oder zu
verstehen — sie müssen nur den Vertrag von `Bestellbar` erfüllen.

### Was bedeutet der Begriff "Deadly Diamond of Death"?
Ein Problem der **Mehrfachvererbung**: wenn eine Klasse `D` von zwei Klassen `B`
und `C` erbt, die beide selbst von derselben Klasse `A` erben, entsteht eine
Diamant-Form (`A` oben, `B`/`C` in der Mitte, `D` unten). Überschreiben `B` und `C`
dieselbe von `A` geerbte Methode unterschiedlich, weiss `D` nicht mehr, welche
Version es erben soll — mehrdeutig, "tödlich" für die Klarheit des Codes.

In unserer roten "Alternative"-Box im Diagramm taucht genau das auf:
`PattyMitZwiebelnUndBacon` müsste von `PattyMitZwiebeln` UND von `PattyMitBacon`
erben, um beide Extras zu bekommen — klassischer Diamant. Java erlaubt das für
Klassen bewusst gar nicht (nur für Interfaces, dort aber ohne Implementierungs-
konflikt) — genau deshalb ist die starre Vererbungshierarchie hier ungeeignet.

### Wie stellen Sie sicher, dass der Code flexibel erweitert werden kann?
Indem alles, was **variieren** soll, nur über eine gemeinsame Schnittstelle
(`Bestellbar`) angesprochen wird, nie über konkrete Klassen. Eine neue Zutat
(z. B. `ExtraKäse`) hinzuzufügen heisst: **eine neue Klasse** schreiben, die
`ZutatDecorator` erweitert — kein bestehender Code (`Patty`, andere Decorators,
die Bestellannahme) muss angefasst oder auch nur gelesen werden. Das ist das
Open/Closed-Prinzip: offen für Erweiterung, geschlossen für Änderung. Bei der
starren Vererbungshierarchie dagegen bräuchte jede neue Zutat-Kombination eine
komplett neue, oft mehrfach vererbende Klasse — der bestehende Code wüchse
exponentiell mit.

## Hinweis zu KI-Einsatz
Dieses Klassendiagramm wurde mit Unterstützung von Claude (KI) erstellt und
durchgearbeitet, um es in der Besprechung erklären zu können.
