# Wetterberatung für Lars

## Beschreibung

Die Konsolenapplikation zeigt die aktuelle Temperatur, Luftfeuchtigkeit, Windgeschwindigkeit und den Wettercode für Zürich, Bern oder Basel. Die Open-Meteo-Forecast-API liefert die Wetterwerte ohne API-Schlüssel. Die Applikation gibt anhand von Temperatur und Wettercode einen einfachen Kleidungshinweis.

## Aufbau und Delegation

- `WetterApp` startet nur `WetterAnwendung`.
- `WetterKonsole` liest rohe Eingaben und zeigt Ergebnisse.
- `WetterEingaben` prüft den Ortsnamen. Ungültige Eingaben lösen `WetterEingabeException` aus.
- `WetterService` holt die Wetterdaten beim API-Client und erstellt den Hinweis.
- `OpenMeteoApi` sendet den HTTP-Aufruf und liest die benötigten Werte aus der JSON-Antwort.

`WetterAnwendung` koordiniert diese Objekte: Konsole → Validierung → Wetterservice → API-Client → Ausgabe. Werte wie der gewählte Ort und die Wetterdaten werden zwischen den Objekten übergeben.

## Starten

In PowerShell im Ordner `D3\Lars\src`:

```powershell
chcp 65001
javac -encoding UTF-8 (Get-ChildItem -Filter '*.java' | ForEach-Object { $_.Name })
java WetterApp
```

Als Ort `Zürich`, `Zuerich`, `Bern` oder `Basel` eingeben. Mit `Ende` wird das Programm beendet. Für die Live-Abfrage ist eine Internetverbindung erforderlich.

API-Dokumentation: [Open-Meteo Forecast API](https://open-meteo.com/en/docs)
