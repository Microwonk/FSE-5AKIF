package net.microwonk.microarchitecture.decorator;

import net.microwonk.microarchitecture.IUmrechnen;
import net.microwonk.microarchitecture.WR;

public class WRDecorator extends WR {
    protected IUmrechnen umrechner;

    public WRDecorator(IUmrechnen umrechner) {
        this.umrechner = umrechner;
    }

    @Override
    public double umrechnen(String variante, double betrag) {
        return umrechner.umrechnen(variante, betrag);
    }

    @Override
    public double getFaktor() {
        return 0;
    }
}
