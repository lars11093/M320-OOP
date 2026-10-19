# D3 – Wetterberatung (Lars): Komplette Erklärung

## 1. Worum geht es?

Die Konsolenapplikation fragt einen Ort ab (Zürich, Bern oder Basel), holt **live** die aktuellen Wetterdaten von der [Open-Meteo-API](https://open-meteo.com/en/docs) und zeigt an:

- Temperatur (°C)
- Luftfeuchtigkeit (%)
- Windgeschwindigkeit (km/h)
- einen **Kleidungshinweis**, berechnet aus Temperatur und Wettercode

Mit `Ende` wird das Programm beendet. Die API braucht keinen API-Schlüssel, aber eine Internetverbindung.

## 2. Das Kompetenzziel D3: Delegation

**Delegation** heisst: Ein Objekt erledigt eine Aufgabe nicht selbst, sondern gibt sie an ein anderes Objekt weiter, das dafür zuständig ist.

In dieser App hat **jede Klasse genau eine Verantwortung**:

| Verantwortung | Klasse |
|---|---|
| Programm starten | `WetterApp` |
| Ablauf koordinieren | `WetterAnwendung` |
| Ein- und Ausgabe auf der Konsole | `WetterKonsole` |
| Eingaben prüfen | `WetterEingaben` |
| Fachlogik (Kleidungshinweis) | `WetterService` |
| HTTP-Aufruf und JSON lesen | `OpenMeteoApi` |
| Daten halten | `WetterOrt`, `WetterDaten` |
| Fehler bei ungültiger Eingabe | `WetterEingabeException` |

## 3. Ablauf einer Abfrage

```
WetterApp.main()
   └─► WetterAnwendung.starten()
          │
          ├─ 1. konsole.ortAbfragen()           → "Zürich"
          ├─ 2. eingaben.ortPruefen("Zürich")   → WetterOrt("Zürich", 47.3769, 8.5417)
          ├─ 3. service.aktuelleDaten(ort)
          │        └─► api.wetterAbrufen(ort)   → HTTP GET → JSON → WetterDaten
          ├─ 4. service.kleidungshinweis(daten) → "Zieh eine warme Jacke an."
          └─ 5. konsole.zeigeWetter(daten, hinweis)
```

Die Werte (Ort, Wetterdaten, Hinweis) werden als **Objekte von Klasse zu Klasse weitergegeben**. Keine Klasse greift auf die Felder einer anderen zu.

## 4. Klassendiagramm (vereinfacht)

```
┌──────────────┐        ┌──────────────────┐
│  WetterApp   │──────► │ WetterAnwendung  │
└──────────────┘        └──────────────────┘
                          │      │       │
              ┌───────────┘      │       └────────────┐
              ▼                  ▼                    ▼
     ┌───────────────┐  ┌────────────────┐   ┌───────────────┐
     │ WetterKonsole │  │ WetterEingaben │   │ WetterService │
     └───────────────┘  └────────────────┘   └───────────────┘
                               │ erzeugt              │ hat
                               ▼                      ▼
                         ┌───────────┐         ┌──────────────┐
                         │ WetterOrt │         │ OpenMeteoApi │
                         └───────────┘         └──────────────┘
                                                      │ erzeugt
                                                      ▼
                                                ┌─────────────┐
                                                │ WetterDaten │
                                                └─────────────┘
```

## 5. Die Klassen im Detail

### `WetterApp`
Enthält nur die `main`-Methode. Sie macht nichts anderes, als die Anwendung zu starten:
```java
new WetterAnwendung().starten();
```

### `WetterAnwendung`: der Koordinator
- Erstellt die benötigten Objekte: `WetterKonsole`, `WetterEingaben`, `WetterService` (mit `OpenMeteoApi`).
- Läuft in einer Schleife, bis der Benutzer `Ende` eingibt.
- Ruft die anderen Objekte in der richtigen Reihenfolge auf, macht aber **selbst keine Fachlogik**.
- Fängt alle Fehler zentral ab:
  - `WetterEingabeException`: ungültiger Ort
  - `IOException`: Netzwerk- oder API-Problem
  - `InterruptedException`: Abfrage wurde unterbrochen

### `WetterKonsole`: Ein- und Ausgabe
- **Die einzige Klasse**, die `Scanner` und `System.out` benutzt.
- Liest mit `new Scanner(System.in, StandardCharsets.UTF_8)`, damit Umlaute wie in „Zürich“ korrekt ankommen.
- Methoden: `zeigeBegruessung()`, `ortAbfragen()`, `zeigeWetter(...)`, `zeigeFehler(...)`, `schliessen()`.
- Formatiert Zahlen auf eine Nachkommastelle (`%.1f`).

### `WetterEingaben`: Validierung
- `ortPruefen(String eingabe)` prüft die rohe Eingabe:
  - leer oder `null` → `WetterEingabeException`
  - normalisiert: Leerzeichen weg, Kleinbuchstaben, `ü` → `u`
  - `zuerich` / `zurich` / `bern` / `basel` → passender `WetterOrt` mit Koordinaten
  - alles andere → `WetterEingabeException`
- Verwendet einen modernen `switch`-Ausdruck mit `->`.

### `WetterEingabeException`
Eigene, geprüfte Exception (`extends Exception`). So kann `WetterAnwendung` ungültige Eingaben klar von Netzwerkfehlern unterscheiden.

### `WetterService`: Fachlogik
- Bekommt die `OpenMeteoApi` über den **Konstruktor** (Dependency Injection):
  ```java
  new WetterService(new OpenMeteoApi());
  ```
- `aktuelleDaten(ort)` delegiert direkt an die API.
- `kleidungshinweis(daten)` enthält die eigentliche Geschäftsregel:

| Bedingung | Hinweis |
|---|---|
| Wettercode 51–99 (Niesel, Regen, Schnee, Gewitter) | „Nimm einen Regenschutz mit.“ |
| Temperatur < 8 °C | „Zieh eine warme Jacke an.“ |
| Temperatur ≥ 25 °C | „Denk an leichte Kleidung und genügend Wasser.“ |
| sonst | „Eine leichte Jacke könnte angenehm sein.“ |

### `OpenMeteoApi`: der API-Client
- Benutzt den `HttpClient` aus Java (`java.net.http`), mit Timeouts von 10 s (Verbindung) und 15 s (Anfrage).
- Baut die URL aus Breiten- und Längengrad:
  ```
  https://api.open-meteo.com/v1/forecast?latitude=47.3769&longitude=8.5417
      &current=temperature_2m,relative_humidity_2m,wind_speed_10m,weather_code&timezone=auto
  ```
- Prüft den HTTP-Statuscode. Ist er nicht `200`, wirft er eine `IOException`.
- Liest die vier Werte mit einem **regulären Ausdruck** aus dem JSON (Hilfsmethode `dezimalwert`), ohne externe Bibliothek.
- Gibt ein fertiges `WetterDaten`-Objekt zurück.

### `WetterOrt` und `WetterDaten`: Datenobjekte
- Alle Felder sind `private final`, gesetzt im Konstruktor, und nur über Getter lesbar.
- Sie sind damit **unveränderlich** (immutable) und enthalten keine Logik.
- `WetterOrt`: Name, Breitengrad, Längengrad
- `WetterDaten`: Ort, Temperatur, Luftfeuchtigkeit, Windgeschwindigkeit, Wettercode

## 6. Warum ist das gute Delegation?

1. **Austauschbarkeit:** Für eine andere Wetter-API ändert man nur `OpenMeteoApi`. Für eine GUI statt der Konsole ändert man nur `WetterKonsole`. Der Rest bleibt gleich.
2. **Single Responsibility:** Jede Klasse hat einen einzigen Grund, sich zu ändern.
3. **Testbarkeit:** `WetterEingaben` und `kleidungshinweis()` lassen sich ohne Internet und ohne Konsole testen.
4. **Saubere Fehlerbehandlung:** Fehler werden dort erkannt, wo sie entstehen (Validierung bzw. API), und an einer Stelle behandelt (`WetterAnwendung`). Das Programm stürzt nie ab.
5. **Lose Kopplung:** `WetterService` erzeugt seine API nicht selbst, sondern bekommt sie übergeben.

## 7. Starten

In PowerShell im Ordner `D3\Lars\src`:

```powershell
chcp 65001
$utf8 = [System.Text.UTF8Encoding]::new($false)
[Console]::InputEncoding = $utf8
[Console]::OutputEncoding = $utf8
$OutputEncoding = $utf8
javac -encoding UTF-8 (Get-ChildItem -Filter '*.java' | ForEach-Object { $_.Name })
java WetterApp
```

## 8. Beispielablauf

```
=== Wetterberatung für Zürich, Bern und Basel ===
Orte: Zürich, Bern, Basel. Mit 'Ende' beenden.
Ort: Zürich
Wetter in Zürich:
Temperatur: 11,4 °C
Luftfeuchtigkeit: 78 %
Wind: 6,2 km/h
Kleidungshinweis: Eine leichte Jacke könnte angenehm sein.
Ort: Luzern
Fehler: Unbekannter Ort. Erlaubt sind Zürich, Bern und Basel.
Ort: Ende
```

*(Die Werte sind ein Beispiel. Die echten Werte kommen live von der API.)*

## 9. Testfälle zum Vorführen

| Eingabe | Erwartung |
|---|---|
| `Zürich` / `Zuerich` / `zurich` | Wetter für Zürich |
| `BERN` | Wetter für Bern (Grossschreibung egal) |
| `  Basel  ` | Wetter für Basel (Leerzeichen egal) |
| *(leer)* | Fehler: „Bitte einen gültigen Ort eingeben …“ |
| `Luzern` | Fehler: „Unbekannter Ort …“ |
| WLAN aus, dann `Bern` | Fehler: „Wetterdaten konnten nicht geladen werden …“ |
| `Ende` | Programm wird beendet |
