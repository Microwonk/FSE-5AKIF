package net.microwonk.aufg_interfaces_und_abstrakte_klassen;

public class Main {
    public static void main(String[] args) {
        Druckbar d = new Namensschilderdruck();
        Mitarbeiter p = new Mitarbeiter("Nicolas Frey", 1209381209, "name@example.com", Mitarbeiter.Position.Mitarbeiter);
        Visitenkartendruck vd = new Visitenkartendruck(p, d);

        vd.drucken();
        vd.setDrucker(Visitenkartendruck.RAHMEN_DRUCK);
        System.out.println();
        vd.drucken();
        vd.setDrucker(Visitenkartendruck.STANDARD_DRUCK);
        System.out.println();
        vd.drucken();

    }
}
