package net.microwonk.fse1.aufg1;

public sealed interface Druckbar permits Namensschilderdruck, Rahmendruck, Standarddruck {
    void drucken(Person person);
}
