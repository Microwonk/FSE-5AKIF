package net.microwonk.microarchitecture;

import net.microwonk.microarchitecture.IUmrechnen;

public abstract class WR implements IUmrechnen {

    // template methoden in der abstrakten Klasse, damit man sie nicht gleich in den Unterklassen implementieren muss.

    @Override
    public double umrechnen(String variante, double betrag) {
        if (zustaendig(variante)) {
            return betrag * getFaktor();
        }
        return betrag;
    }

    @Override
    public boolean zustaendig(String variante) {
        return this.getClass().getSimpleName().equals(variante);
    }
}
