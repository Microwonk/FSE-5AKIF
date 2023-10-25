package net.microwonk.microarchitecture.decorator;

import net.microwonk.microarchitecture.IUmrechnen;

public class FixGebuhrenDecorator extends WRDecorator {

    public FixGebuhrenDecorator(IUmrechnen umrechner) {
        super(umrechner);
    }

    // weil die Funktion des Decorators hier so simpel ist, könnte man es relativ einfach prozedurell programmieren.
    @Override
    public double umrechnen(String variante, double betrag) {
        return super.umrechnen(variante, betrag) + 5; // 5 € fix dazu
    }
}
