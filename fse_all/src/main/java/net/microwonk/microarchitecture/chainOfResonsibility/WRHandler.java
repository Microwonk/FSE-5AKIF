package net.microwonk.microarchitecture.chainOfResonsibility;

import net.microwonk.microarchitecture.IUmrechnen;

public class WRHandler {
    private WRHandler next;
    private IUmrechnen handler;

    public void addHandler(IUmrechnen toAdd) {
        if (handler == null) {
            handler = toAdd;
        } else {
            if (next == null) {
                next = new WRHandler();
            }
            next.addHandler(toAdd);
        }
    }

    public boolean removeHandler(IUmrechnen toRemove) {
        if (handler == toRemove) {
            handler = null;
            return true;
        } else if (next != null) {
            boolean removed = next.removeHandler(toRemove);
            if (removed && next.handler == null) {
                next = next.next;
            }
            return removed;
        }
        return false;
    }

    public double umrechnen(String variante, double betrag) {
        if (handler != null) {
            betrag = handler.umrechnen(variante, betrag);
        }
        if (next != null) {
            return next.umrechnen(variante, betrag);
        } else {
            return betrag;
        }
    }
}
