package net.microwonk.fse1.aufg1;

// eine "sealed" class muss einen permits beinhalten, sodass es kontrolliert werden kann, wie viele Fälle der Vererbung dieser Klasse es gibt
public abstract sealed class Person permits Mitarbeiter { // neue Einträge müssen dann hier geänndert werden (Mitarbeiter...)
    private String name;
    private long UID;

    public Person(String name, long UID) {
        this.name = name;
        this.UID = UID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getUID() {
        return UID;
    }

    public void setUID(long UID) {
        this.UID = UID;
    }
}
