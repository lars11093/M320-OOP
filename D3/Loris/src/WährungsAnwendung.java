import java.io.IOException;

public class WährungsAnwendung {
    public void starten() {
        WährungsKonsole konsole = new WährungsKonsole();
        WährungsEingaben eingaben = new WährungsEingaben();
        WechselkursService service = new WechselkursService(new FrankfurterApi());

        while (true) {
            konsole.zeigeMenue();
            try {
                if (!eingaben.istUmrechnungGewuenscht(konsole.menueEingabe())) {
                    break;
                }
                UmrechnungsAnfrage anfrage = eingaben.anfrageErstellen(
                        konsole.betragEinlesen(), konsole.quellwaehrungEinlesen(),
                        konsole.zielwaehrungEinlesen());
                UmrechnungErgebnis ergebnis = service.umrechnen(anfrage);
                konsole.zeigeErgebnis(ergebnis);
            } catch (WährungsEingabeException e) {
                konsole.zeigeFehler(e.getMessage());
            } catch (IOException e) {
                konsole.zeigeFehler("Wechselkurs konnte nicht geladen werden: " + e.getMessage());
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                konsole.zeigeFehler("Die Kursabfrage wurde unterbrochen.");
            }
        }
        konsole.schliessen();
    }
}
