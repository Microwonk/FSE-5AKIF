package net.microwonk.aufg_interfaces_und_abstrakte_klassen;

public final class Mitarbeiter extends Person {

    private String EMail;
    private Position position;

    // TODO verbesserung!! set in Konstruktor

    public Mitarbeiter(String name, long UID, String EMail, Position position) {
        super(name, UID);
        if (!EMail.matches("^[a-zA-Z0-9]+(?:\\.[a-zA-Z0-9]+)*@[a-zA-Z0-9]+(?:\\.[a-zA-Z0-9]+)*$"))
            throw new IllegalStateException("Email given is not to format"); // not valid email to be set
        this.EMail = EMail;
        this.position = position;
    }

    public String getEMail() {
        return EMail;
    }

    public void setEMail(String EMail) {
        if (!EMail.matches("^[a-zA-Z0-9]+(?:\\.[a-zA-Z0-9]+)*@[a-zA-Z0-9]+(?:\\.[a-zA-Z0-9]+)*$")) return; // not valid email to be set
        this.EMail = EMail;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    // eine Enumeration für die Einteilung eines Mitarbeiters
    public enum Position {
        Abteilungsleiter {
            @Override
            String sternchen() {
                return "***";
            }
        },
        CEO {
            @Override
            String sternchen() {
                return "*****";
            }
        },
        Mitarbeiter {
            @Override
            String sternchen() {
                return "*";
            }
        };
        // abstrakte Methode in einem Enum muss bei jedem "Case" implementiert werden -> gibt die sternchen zurück die in @Namensschilderdruck.drucken(Person person) in Verwendung kommt
        abstract String sternchen();
    }
}
