package net.microwonk.fse1.aufg1;

public final class Mitarbeiter extends Person {

    private String EMail;
    private Position position;

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

    public enum Position {
        Abteilungsleiter,
        CEO,
        Mitarbeiter; // usw, mit fällt nicht mehr ein
    }
}
