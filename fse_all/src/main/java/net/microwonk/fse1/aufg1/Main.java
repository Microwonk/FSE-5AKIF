package net.microwonk.fse1.aufg1;

public class Main {
    public static void main(String[] args) {
        Druckbar d = new Namensschilderdruck();
        Person p = new Mitarbeiter("Nicolas Frkasdhlasjdey", 1209381209, "name@example.com", Mitarbeiter.Position.Mitarbeiter);
        Visitenkartendruck vd = new Visitenkartendruck((Mitarbeiter) p, d);
        vd.drucken();
        vd.setDrucker(Visitenkartendruck.RAHMEN_DRUCK);
        vd.drucken();
    }
}
