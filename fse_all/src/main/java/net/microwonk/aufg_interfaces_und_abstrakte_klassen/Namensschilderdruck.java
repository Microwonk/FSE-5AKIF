package net.microwonk.aufg_interfaces_und_abstrakte_klassen;

public final class Namensschilderdruck implements Druckbar {
    @Override
    public void drucken(Person person) {
        switch (person) {
            case Mitarbeiter m -> {
                System.out.println(
                        m.getUID() + "  " + m.getName() + '\n' +
                                m.getPosition().sternchen()
                );
            }
        }
    }
}
