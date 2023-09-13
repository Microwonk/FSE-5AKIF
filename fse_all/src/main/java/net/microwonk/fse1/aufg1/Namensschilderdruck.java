package net.microwonk.fse1.aufg1;

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
