/**
 * ASSOZIATION (loseste Kopplung): Drucker wird nirgends als Attribut einer
 * anderen Klasse gespeichert - er wird nur kurzzeitig als Methodenparameter
 * verwendet (siehe Flug.druckeBoardingpass()). Keine dauerhafte Beziehung,
 * kein Besitz - nur "kennt kurz, benutzt kurz".
 */
public class Drucker {

    private final String modell;

    public Drucker(String modell) {
        this.modell = modell;
    }

    public void druckeText(String text) {
        System.out.println("[" + modell + "] druckt:");
        System.out.println(text);
    }
}
