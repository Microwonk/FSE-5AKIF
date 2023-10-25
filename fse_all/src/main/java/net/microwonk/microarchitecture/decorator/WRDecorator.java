package net.microwonk.microarchitecture.decorator;

import net.microwonk.microarchitecture.IUmrechnen;
import net.microwonk.microarchitecture.WR;

// wie ein wrapper, kann mit bestimmten komplexitäten helfen, aber für diesen Zweck etwas overkill
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
        return umrechner.getFaktor();
    }
}
