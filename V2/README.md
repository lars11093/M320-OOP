# V2 Serie G — Versandarten modellieren (Polymorphismus)

## Checkliste

| # | Anforderung | Erfüllt durch |
|---|---|---|
| 1 | Abstrakte Superklasse `Versandart` mit gemeinsamen Attributen | ✅ `Versandart.java` — `code`, `bezeichnung`, `grundPreis`, `maxGewichtKg`, `aktiv` |
| 2 | 3 abstrakte Methoden, je Subklasse überschrieben | ✅ `berechneVersandkosten()`, `berechneLieferTage()`, `akzeptiert()` in `StandardVersand`, `ExpressVersand`, `KurierVersand` |
| 3 | Subklassen mit eigenen Zusatzattributen | ✅ `preisProKg` (Standard), `expressZuschlag`/`garantierteTage` (Express), `preisProKm`/`maxDistanzKm` (Kurier) |
| 4 | `VersandVerwaltung` mit allen 6 Pflichtmethoden | ✅ `add`, `removeByCode`, `findByCode`, `printAlle`, `findeMoeglicheVersandarten`, `findeGuenstigsteVariante` |
| 5 | Polymorphie ohne `instanceof` | ✅ `VersandVerwaltung` ruft ausschliesslich über `Versandart`-Referenzen auf — kein einziges `instanceof` im Code |
| 6 | Konsolenmenü mit allen 9 Punkten | ✅ `VersandSimulation.java` |
| 7 | Erweiterung: 4. Versandart (Abholstation), bestehende Logik unverändert | ✅ `Abholstation.java` — `VersandVerwaltung` und `VersandSimulation`-Menülogik mussten dafür nicht angepasst werden |

## Ausführen

```
cd V2/src
javac -encoding UTF-8 *.java
java VersandSimulation
```

## Architektur

```
Versandart (abstract)
 ├─ StandardVersand   — günstig, langsam
 ├─ ExpressVersand    — teuer, schnell, Spezialregel: max. halbes Gewicht
 ├─ KurierVersand     — distanzabhängiger Preis, max. Distanz
 └─ Abholstation       — Erweiterung: kostenlos, sofort, keine Distanzgrenze
```

`VersandVerwaltung` kennt nur den Typ `Versandart`. Wenn `findeMoeglicheVersandarten()` oder `findeGuenstigsteVariante()` `v.akzeptiert(...)` bzw. `v.berechneVersandkosten(...)` aufrufen, entscheidet zur Laufzeit die **tatsächliche Objektklasse**, welcher Code ausgeführt wird (dynamisches Binden) — genau das ist Polymorphie. Die Verwaltung muss nicht wissen und fragt nie ab, ob sie gerade einen `StandardVersand` oder eine `Abholstation` vor sich hat.

## Warum die Architektur neue Typen "for free" unterstützt
`Abholstation` wurde als vierte Subklasse hinzugefügt, ohne `VersandVerwaltung.java` oder die Menülogik in `VersandSimulation` (Punkte 4-6) auch nur eine Zeile anzufassen. Einzige Änderung: `Abholstation` implementiert die drei abstrakten Methoden, und `main()` fügt eine Instanz zum Startbestand hinzu. Das beweist die Kernidee des Auftrags — Skalierbarkeit durch Polymorphie statt durch `instanceof`-Kaskaden.

## Hinweis zu KI-Einsatz
Diese Implementation wurde mit Unterstützung von Claude (KI) erstellt und anschliessend selbst durchgearbeitet, um sie in der Besprechung erklären zu können.
