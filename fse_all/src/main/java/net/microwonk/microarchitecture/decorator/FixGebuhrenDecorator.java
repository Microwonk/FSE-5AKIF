package net.microwonk.microarchitecture.decorator;

import net.microwonk.microarchitecture.IUmrechnen;

public class FixGebuhrenDecorator extends WRDecorator {

    public FixGebuhrenDecorator(IUmrechnen umrechner) {
        super(umrechner);
    }

    @Override
    public double umrechnen(String variante, double betrag) {
        return super.umrechnen(variante, betrag) + 5; // 5 € fix dazu
    }
}
