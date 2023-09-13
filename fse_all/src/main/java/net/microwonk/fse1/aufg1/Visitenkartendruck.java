package net.microwonk.fse1.aufg1;

public class Visitenkartendruck {

    public static final int STANDARD_DRUCK = 0;
    public static final int RAHMEN_DRUCK = 1;
    public static final int NAMENSSCHILDER_DRUCK = 2;


    private Mitarbeiter mitarbeiter;
    private Druckbar drucker;

    public Visitenkartendruck(Mitarbeiter mitarbeiter, Druckbar drucker) {
        if (mitarbeiter == null) {
            throw new IllegalStateException("Field 'mitarbeiter' must not be empty");
        }
        this.mitarbeiter = mitarbeiter;
        this.drucker = drucker;
    }

    public void drucken() {
        this.drucker.drucken(this.mitarbeiter);
    }

    public void setMitarbeiter(Mitarbeiter mitarbeiter) {
        if (mitarbeiter == null) {
            throw new IllegalStateException("Field 'mitarbeiter' must not be empty");
        }
        this.mitarbeiter = mitarbeiter;
    }

    public void setDrucker(Druckbar drucker) {
        this.drucker = drucker;
    }

    public void setDrucker(int drucker) {
        if (drucker != 0 && drucker != 1 && drucker != 2) {
            return;
        }
        setDrucker(fromInt(drucker));
    }

    private Druckbar fromInt(int n) {
        return (n == 0) ? new Standarddruck() : (n == 1) ? new Rahmendruck() : new Namensschilderdruck();
    }
}
