# D3 – Delegation und saubere Trennung

Dieses Verzeichnis enthält zwei getrennte Beispielapplikationen zum Kompetenznachweis D3:

- [Wetterberatung für Lars](Lars/README.md) – fragt Wetterdaten bei Open-Meteo ab.
- [Währungsrechner](Loris/README.md) – für Loris; bezieht Wechselkurse von Frankfurter.

Beide Programme trennen Konsoleneingabe, Validierung, Fachlogik und API-Aufruf. Die jeweilige `main`-Methode startet nur die Anwendung.
