package net.microwonk.aufg;

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
