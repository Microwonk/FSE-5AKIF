package net.microwonk.aufg_interfaces_und_abstrakte_klassen;

public final class Standarddruck implements Druckbar{
    @Override
    public void drucken(Person person) {
        // pattern matching : erst ab java 17 feature glaube ich
        switch (person) {
            case Mitarbeiter m -> {
                System.out.println(
                        m.getUID() + "  " + m.getName() + " " +
                            m.getEMail() + "    " + m.getPosition().toString()
                );
            }
        }
    }
}
