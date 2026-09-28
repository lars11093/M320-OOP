# Währungsrechner für Loris

## Beschreibung

Die Konsolenapplikation rechnet einen Betrag zwischen CHF, EUR, USD und GBP um. Den aktuellen Wechselkurs fragt sie bei der Frankfurter-API ab. Die API stellt Wechselkurse bereit, benötigt für diese Abfrage keinen API-Schlüssel und liefert Kurs sowie Datum zurück.

## Aufbau und Delegation

- `WährungsApp` startet nur `WährungsAnwendung`.
- `WährungsKonsole` liest rohe Eingaben und zeigt Resultate.
- `WährungsEingaben` prüft Menüwahl, Betrag und Währung. Ungültige Eingaben lösen `WährungsEingabeException` aus.
- `WechselkursService` berechnet das Resultat mit dem erhaltenen Betrag und Kurs.
- `FrankfurterApi` sendet die HTTP-Anfrage und liest Kurs und Datum aus der JSON-Antwort.

`WährungsAnwendung` koordiniert die Objekte: Konsole → Validierung → Wechselkursservice → API-Client → Ausgabe. Betrag und Währungscodes werden als Werte übergeben; das Ergebnisobjekt enthält Resultat, Kurs und Kursdatum.

## Starten

In PowerShell im Ordner `D3\Loris\src`:

```powershell
chcp 65001
javac -encoding UTF-8 (Get-ChildItem -Filter '*.java' | ForEach-Object { $_.Name })
java WährungsApp
```

Für die Live-Abfrage ist eine Internetverbindung erforderlich. Die Kurse können sich täglich ändern.

API-Dokumentation: [Frankfurter API v2](https://frankfurter.dev/)
