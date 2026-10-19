# D3 – Wetterberatung (Lars)

## Was macht die App?

Man gibt einen Ort ein (Zürich, Bern oder Basel). Die App holt das aktuelle Wetter von der Open-Meteo-API und zeigt Temperatur, Luftfeuchtigkeit, Wind und einen Kleidungstipp. Mit `Ende` wird sie beendet.

## Was ist Delegation?

Eine Klasse macht nicht alles selbst, sondern gibt Aufgaben an andere Klassen weiter. Jede Klasse hat **genau eine Aufgabe**.

## Die Klassen

| Klasse | Aufgabe |
|---|---|
| `WetterApp` | Startet nur die Anwendung (`main`) |
| `WetterAnwendung` | Steuert den Ablauf und fängt Fehler ab |
| `WetterKonsole` | Liest Eingaben und zeigt Ausgaben |
| `WetterEingaben` | Prüft, ob der Ort gültig ist |
| `WetterEingabeException` | Fehler bei ungültiger Eingabe |
| `WetterService` | Berechnet den Kleidungstipp |
| `OpenMeteoApi` | Holt die Daten aus dem Internet |
| `WetterOrt` | Speichert Name und Koordinaten |
| `WetterDaten` | Speichert die Wetterwerte |

## Ablauf

```
Eingabe → WetterKonsole → WetterEingaben → WetterService → OpenMeteoApi → Ausgabe
```

1. `WetterKonsole` fragt nach dem Ort.
2. `WetterEingaben` prüft ihn und macht daraus einen `WetterOrt`.
3. `WetterService` gibt den Auftrag an `OpenMeteoApi` weiter.
4. `OpenMeteoApi` holt die Daten und liefert `WetterDaten` zurück.
5. `WetterService` berechnet den Kleidungstipp.
6. `WetterKonsole` zeigt alles an.

## Kleidungstipp

| Bedingung | Tipp |
|---|---|
| Regen oder Schnee | Regenschutz mitnehmen |
| unter 8 °C | warme Jacke |
| ab 25 °C | leichte Kleidung und Wasser |
| sonst | leichte Jacke |

## Warum ist das gut?

- **Übersichtlich:** Jede Klasse hat nur eine Aufgabe.
- **Einfach änderbar:** Eine andere API? Nur `OpenMeteoApi` ändern. Eine andere Oberfläche? Nur `WetterKonsole` ändern.
- **Stabil:** Falsche Eingaben und Internetprobleme werden abgefangen, das Programm stürzt nicht ab.

## Starten

In PowerShell im Ordner `D3\Lars\src`:

```powershell
chcp 65001
$utf8 = [System.Text.UTF8Encoding]::new($false)
[Console]::InputEncoding = $utf8
[Console]::OutputEncoding = $utf8
javac -encoding UTF-8 *.java
java WetterApp
```

Die UTF-8-Zeilen sorgen dafür, dass Umlaute wie in „Zürich“ richtig angezeigt und eingelesen werden.
