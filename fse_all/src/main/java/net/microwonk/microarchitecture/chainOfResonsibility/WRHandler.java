package net.microwonk.microarchitecture.chainOfResonsibility;

import net.microwonk.microarchitecture.IUmrechnen;

// chain of responsibility kann hier von Nutzen sein, da man hier das Open-Closed Prinzip nicht verletzt. Um einen neuen Währungsumrechner zu erstellen, einfach eine neue Klasse dafür erstellen
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

// YAGNI: In diesem Code gibt es keine offensichtlichen Anzeichen für unnötige Funktionalität. Das Chain-of-Responsibility-Muster ermöglicht das Hinzufügen neuer Währungsumrechner, ohne bestehenden Code zu ändern.

// DRY: Das Chain-of-Responsibility-Muster fördert die Wiederverwendbarkeit von Code. Jeder Umrechner ist eine separate Klasse, die die gleiche Schnittstelle (IUmrechnen) implementiert. Dies reduziert die Wiederholung von Code und ermöglicht die einfache Hinzufügung neuer Umrechnerklassen.

// KISS: Der Code ist relativ einfach und folgt dem KISS-Prinzip. Er ist leicht verständlich und erfüllt seinen Zweck, indem er eine Kette von Verantwortlichen für die Währungsumrechnung erstellt. Jeder Umrechner ist für eine spezifische Währung zuständig, was die Struktur einfach hält.
