package net.microwonk.fse1.aufg1;

// ein sealed interface muss eine Reihe von Klassen oder Interfaces permitten
// , damit Pattern matching ohne default case und andere Sachen möglich sind.
// (ist auch besser, falls das Interface außerhalb der Dedizierten Liste an Klassen nicht verwendet werden muss
public sealed interface Druckbar permits Namensschilderdruck, Rahmendruck, Standarddruck {
    void drucken(Person person);
}
