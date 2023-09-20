package net.microwonk.aufg;

public class Visitenkartendruck {

    // statische Konstanten, die für die Methode @setDrucker(int drucker) benutzt wird (short-hand)
    public static final int STANDARD_DRUCK = 0;
    public static final int RAHMEN_DRUCK = 1;
    public static final int NAMENSSCHILDER_DRUCK = 2;

    private Mitarbeiter mitarbeiter;
    private Druckbar drucker;

    /**
     *
     * @param mitarbeiter
     * @param drucker
     * @throws IllegalStateException falls das Feld Mitarbeiter als NULL mitgegeben wird
     */
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

    /**
     *
     * @param mitarbeiter
     * @throws IllegalStateException falls das Feld Mitarbeiter als NULL mitgegeben wird
     */
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
        // darf nicht außerhalb von den Konstanten mitgegeben werden
        if (drucker != 0 && drucker != 1 && drucker != 2) {
            return;
        }
        setDrucker((drucker == 0) ? new Standarddruck() : (drucker == 1) ? new Rahmendruck() : new Namensschilderdruck());
    }
}
