# Kompetenznachweis D1

Zwei einfache Konsolen-Implementationen, die die D1-Lernziele zeigen: **Garagen-Simulation** und **Bank-Simulation**.

## Ausführen

```
cd D1/garage/src
javac *.java
java GaragenSimulation
```

```
cd D1/bank/src
javac *.java
java BankSimulation
```

## Lernziele → wo im Code

| Lernziel | Garagen-Simulation | Bank-Simulation |
|---|---|---|
| Eigene Klassen entwerfen, Objekte instanziieren | `Fahrzeug`, `Garage` — instanziiert in `GaragenSimulation.main()` | `Konto`, `Bank` — instanziiert in `BankSimulation.main()` |
| Objekte kommunizieren über Methodenaufrufe | `Garage.fahrzeugReparieren()` ruft `fahrzeug.reparieren(kosten)` auf | `Bank.transferieren()` ruft `von.abheben(betrag)` und `nach.einzahlen(betrag)` auf |
| Werte werden an andere Objekte übergeben | Parameter `kosten` | Parameter `betrag` |
| Attribute vor Aussenzugriff geschützt (Kapselung) | `Fahrzeug`: alle Felder `private`, kein `setReparaturKosten()` | `Konto`: `kontostand` `private`, kein `setKontostand()` |
| Zustand eines Objekts ändert sich über Attribute | `reparieren()` setzt `repariert = true` und erhöht `reparaturKosten` | `einzahlen()`/`abheben()` verändern `kontostand` |

## Vorbereitung für die Besprechung

**Zeigen Sie die Datenkapselung. Wie wird der Zugriff auf Attribute gelöst?**
Alle Attribute sind `private`. Lesender Zugriff nur über Getter (`getKontostand()`, `getReparaturKosten()`, ...). Verändernder Zugriff nur über kontrollierte Methoden (`einzahlen()`, `abheben()`, `reparieren()`) — es gibt bewusst **keine** direkten Setter für den Zustand, damit z. B. kein negativer Kontostand oder eine falsche Reparaturkosten-Zahl von aussen gesetzt werden kann.

**Zeigen Sie auf, wie Objekte miteinander kommunizieren? Werden dabei Werte mitgegeben?**
`Garage` und `Bank` kennen ihre jeweiligen Objekte (`Fahrzeug`/`Konto`) über eine Liste (Attribut) und rufen deren Methoden per Punkt-Operator auf. Dabei werden Werte als Parameter übergeben, z. B. `fahrzeug.reparieren(kosten)` oder `von.abheben(betrag)` / `nach.einzahlen(betrag)` beim Transfer — das ist ein Beispiel für **Delegation**: `Bank.transferieren()` erledigt die eigentliche Arbeit nicht selbst, sondern delegiert sie an die beiden `Konto`-Objekte.

**Zeigen Sie, wie sich der Zustand eines Objekts verändert.**
`Fahrzeug.reparieren(kosten)` ändert `repariert` von `false` auf `true` und erhöht `reparaturKosten`. `Konto.einzahlen()`/`abheben()` ändern `kontostand`. In beiden Fällen passiert das ausschliesslich innerhalb der Klasse selbst — von aussen wird nur die Methode mit einem Wert aufgerufen, die Klasse entscheidet, wie sich ihr eigener Zustand ändert.

**Was ist der Unterschied zwischen einem primitiven und komplexen Datentyp? Zeigen Sie Beispiele in Ihrem Code.**
- Primitiv (Wert direkt gespeichert): `double kosten`, `boolean repariert`, `int naechsteKontonummer`
- Komplex / Referenztyp (Variable speichert eine Referenz auf ein Objekt): `Fahrzeug`, `Konto`, `String kennzeichen`, `List<Fahrzeug> fahrzeuge`

## Hinweis zu KI-Einsatz

Diese Implementationen wurden mit Unterstützung von Claude (KI) erstellt und anschliessend selbst durchgearbeitet, um sie in der Besprechung erklären zu können — wie im Auftrag vorgesehen ("Arbeiten Sie mit KI an möglichen Implementationen").
