package net.microwonk.microarchitecture.decorator;

import net.microwonk.microarchitecture.IUmrechnen;

public class ProzentGebuhrenDecorator extends WRDecorator {

    public ProzentGebuhrenDecorator(IUmrechnen umrechner) {
        super(umrechner);
    }

    // weil die Funktion des Decorators hier so simpel ist, könnte man es relativ einfach prozedurell programmieren.
    @Override
    public double umrechnen(String variante, double betrag) {
        return super.umrechnen(variante, betrag) * 1.005; // 0,5 % dazu
    }
}
