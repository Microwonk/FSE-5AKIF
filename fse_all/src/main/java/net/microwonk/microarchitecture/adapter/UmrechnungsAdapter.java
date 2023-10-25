package net.microwonk.microarchitecture.adapter;

import net.microwonk.microarchitecture.ISammelumrechnung;
import net.microwonk.microarchitecture.IUmrechnen;

import java.util.Arrays;

// Das Adapter-Muster ist hier sehr Hilfreich, weil es nun mehrere Beträge zulässt und diese mithilfe des Umrechners in eine Summe von doubles zurückgibt
public class UmrechnungsAdapter implements ISammelumrechnung {
    private final IUmrechnen umrechner;

    public UmrechnungsAdapter(IUmrechnen umrechner) {
        this.umrechner = umrechner;
    }

    @Override
    public double sammelumrechnen(double[] betraege, String variante) {
        return Arrays.stream(betraege).map(betrag -> umrechner.umrechnen(variante, betrag)).sum();
    }
}
