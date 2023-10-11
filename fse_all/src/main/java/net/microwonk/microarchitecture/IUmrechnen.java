package net.microwonk.microarchitecture;

public interface IUmrechnen {
    double umrechnen(String variante, double betrag);
    double getFaktor();
    boolean zustaendig(String variante);
}