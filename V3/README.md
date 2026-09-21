# Kompetenznachweis V3 — Interfaces: Portfolio von Aktien

Gewählt: **Idee 1 — Portfolio von Aktien**.

## Lernziele-Checkliste

| # | Lernziel | Erfüllt durch |
|---|---|---|
| 1 | Interfaces aus Beschreibung identifizieren und im Code umsetzen | ✅ Interface `StockExchange` (`StockExchange.java`), implementiert von `ZuercherBoerse` und `LondonBoerse` |
| 2 | Aufzeigen, wie Interfaces den Code flexibler machen | ✅ `Portfolio.berechneWert(StockExchange boerse)` — Börse wird als Parameter übergeben, Portfolio kennt keine konkrete Börse |
| 3 | Aufzeigen, wie Interfaces den Code polymorph machen | ✅ Dieselbe Methode `berechneWert()` liefert je nach übergebener Börse (Zürich vs. London) unterschiedliche Resultate, ohne `instanceof` |

## Architektur

```
StockExchange (Interface)
 ├─ ZuercherBoerse   — Nestlé, ABB, Swiss Re, Novartis
 └─ LondonBoerse     — HSBC, BP, Vodafone, Barclays

Portfolio
 └─ hat eine Liste von Aktie-Objekten (Ticker + Anzahl, kein Kurs)
 └─ berechneWert(StockExchange boerse) — fragt Kurse bei der übergebenen Börse ab
```

**Wichtig:** `Portfolio` importiert oder kennt `ZuercherBoerse`/`LondonBoerse` **nirgends**.
Es gibt in `Portfolio.java` nur eine einzige Referenz auf den Typ `StockExchange`.

## Ausführen

```
cd V3/src
javac -encoding UTF-8 *.java
java PortfolioSimulation
```

**Test-Beweis (bereits durchgeführt):** Dasselbe Portfolio (Nestlé, ABB, HSBC, Vodafone)
ergibt an der Zürcher Börse CHF 1859.00 (nur Nestlé + ABB sind dort kotiert), an der
Londoner Börse CHF 414.50 (nur HSBC + Vodafone sind dort kotiert) — **derselbe
Methodenaufruf**, **unterschiedliches Resultat**, je nachdem welches `StockExchange`-Objekt
übergeben wird.

## Warum Interfaces hier Sinn machen (Lernziel 2 + 3)

**Flexibilität (Lernziel 2):**
`Portfolio` müsste ohne Interface für jede Börse eigenen Code haben
(`if (boerse == "Zürich") { ... } else if (boerse == "London") { ... }`), oder schlimmer:
eine eigene Methode pro Börse. Mit dem Interface reicht **eine** Methode
(`berechneWert(StockExchange boerse)`) für **beliebig viele** Börsen. Eine neue Börse
(z. B. New York) hinzuzufügen heisst: eine neue Klasse `NewYorkBoerse implements
StockExchange` schreiben — `Portfolio.java` bleibt komplett unverändert.

**Polymorphie (Lernziel 3):**
Zur Laufzeit entscheidet der **tatsächliche Objekttyp** hinter der `StockExchange`-Referenz
(`ZuercherBoerse` oder `LondonBoerse`), welcher `getPreis()`/`istKotiert()`-Code ausgeführt
wird — `Portfolio` selbst weiss das zur Compile-Zeit nicht und muss es auch nicht wissen
(dynamisches Binden). Das ist im Test-Beweis oben sichtbar: derselbe Aufruf
`portfolio.berechneWert(boerse)`, zwei völlig verschiedene Ergebnisse.

## Hinweis zu KI-Einsatz
Diese Implementation wurde mit Unterstützung von Claude (KI) erstellt und anschliessend
selbst durchgearbeitet, um sie in der Besprechung erklären zu können.
