# Kompetenznachweis M2 — Sequenzdiagramm

## Lernziele-Checkliste

| # | Lernziel | Erfüllt durch |
|---|---|---|
| 1 | Interaktion zwischen Objekten grafisch darstellen | ✅ `sequenzdiagramm-ueberweisung.puml` — Use Case "Überweisen", 5 Objekte, 7 Methodenaufrufe |
| 2 | Wichtigste UML-Sequenzdiagramm-Symbole kennen | ✅ Alle 6 Symbole unten in "Fragen zur Besprechung" erklärt + im Diagramm verwendet: Message, Swimlane, Activation, Return, Self-Message, `alt`/`else` |
| 3 | Unterschied dynamisch ↔ statisch kennen | ✅ Abschnitt "Dynamisch vs. statisch" unten |
| 4 | Sequenzdiagramm mit Tool (PlantUML) umsetzen | ✅ `.puml`-Datei liegt vor + Rendering-Anleitung unten |

## Use Case
**Geld überweisen** aus der Bank-Simulation (`D1/bank/src`): `BankSimulation` lässt den
Benutzer zwei Kontonummern und einen Betrag eingeben und ruft `Bank.transferieren(von, nach, betrag)`
auf, welches wiederum `Konto.abheben()` und `Konto.einzahlen()` auf den beiden `Konto`-Objekten aufruft.

Zugrundeliegender Code:
- `D1/bank/src/BankSimulation.java` — Menü, Benutzer-Interaktion
- `D1/bank/src/Bank.java` — `findeKonto()`, `transferieren()`
- `D1/bank/src/Konto.java` — `abheben()`, `einzahlen()`

## Diagramm
Siehe `sequenzdiagramm-ueberweisung.puml`. Rendern z.B. mit:
- https://www.plantuml.com/plantuml (Quellcode einfügen)
- VS Code Extension "PlantUML"
- lokal mit `plantuml.jar`

## Fragen zur Besprechung

**Wie werden Aufrufe von einem Objekt zu einem anderen dargestellt?**
Mit einem durchgezogenen Pfeil mit gefülltem Pfeilkopf (`->`) von der Lifeline des Senders zur
Lifeline des Empfängers, beschriftet mit Methodenname und Parametern. Beispiel im Diagramm:
`Bank -> Von : abheben(betrag)`.

**Was sind Swimlanes?**
Die vertikale "Spur" jedes Objekts (in PlantUML: Lifeline). Jedes teilnehmende Objekt
(`Benutzer`, `Sim`, `Bank`, `Von`, `Nach`) bekommt seine eigene senkrechte Linie; die Zeit läuft
von oben nach unten. Nachrichten zwischen den Objekten laufen als horizontale Pfeile zwischen
den Swimlanes.

**Kann ich sehen, wie lange ein Objekt "lebt"?**
Ja — über die **Aktivierungsbalken** (schmales Rechteck auf der Lifeline, `activate`/`deactivate`
in PlantUML). Der Balken zeigt, während welcher Zeitspanne ein Objekt aktiv Code ausführt.
Im Diagramm ist z.B. `Bank` während der ganzen `transferieren()`-Verarbeitung aktiviert, `Von` nur
kurz während `abheben()`.

**Wie wird der Return-Value dargestellt?**
Mit einem **gestrichelten** Pfeil (`-->`) zurück zum Aufrufer, meist mit dem Rückgabewert
beschriftet. Beispiel: `Von --> Bank : ok` bzw. im Fehlerfall `Von --> Bank : throws IllegalStateException`.

**Wie werden Aufrufe innerhalb desselben Objekts dargestellt?**
Als **Self-Message**: ein Pfeil, der von der Lifeline abgeht und zu ihr selbst zurückkehrt (kleine
Schleife), oft mit einer zweiten, leicht versetzten Aktivierung. Beispiel im Diagramm:
`Von -> Von : kontostand -= betrag` (die interne Zustandsänderung innerhalb von `Konto`).

**Wie kann ich eine alternative Sequenz zeigen?**
Mit einem **`alt`/`else`-Fragment** (Combined Fragment), das den Ablauf in Bedingungen aufteilt.
Im Diagramm: `alt genügend Guthaben` / `else ungenügend Guthaben` — zeigt den Erfolgsfall
(abheben + einzahlen) und den Fehlerfall (Exception, kein `einzahlen()`-Aufruf) als zwei
Alternativen innerhalb desselben Diagramms.

## Dynamisch vs. statisch
Ein Sequenzdiagramm ist eine **dynamische** Darstellung: es zeigt das Verhalten zur Laufzeit —
welche konkreten Nachrichten in welcher zeitlichen Reihenfolge zwischen Objekten ausgetauscht
werden, für einen bestimmten Ablauf (Use Case). Ein Klassendiagramm dagegen ist **statisch**:
es zeigt die Struktur (Klassen, Attribute, Methoden, Beziehungen), unabhängig davon, was zur
Laufzeit tatsächlich passiert.

## Hinweis zu KI-Einsatz
Dieses Sequenzdiagramm wurde mit Unterstützung von Claude (KI) auf Basis des selbst geschriebenen
Bank-Codes (D1) erstellt und durchgearbeitet, um es in der Besprechung erklären zu können.
