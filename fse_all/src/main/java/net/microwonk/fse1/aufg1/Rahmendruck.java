package net.microwonk.fse1.aufg1;

public final class Rahmendruck implements  Druckbar {
    @Override
    public void drucken(Person person) {
        switch (person) {
            // Overkill, ist nur wenn man die Formattierung 100% korrekt haben will
            case Mitarbeiter m -> {
                String f = m.getUID() + " " + m.getName(); // first
                String s = m.getEMail() + " " + m.getPosition().toString(); // second
                int len = Math.max(f.length(), s.length()); // maximale Länge von beiden Zeilen

                // padding
                String padF = " ".repeat(Math.max(0, len - f.length()));
                String padS = " ".repeat(Math.max(0, len - s.length()));
                len+=2; // Add 2 for padding

                // print
                System.out.println(
                        lengthChar(len, '_', "|") + '\n' +
                                "| " + f + padF + " |\n" +
                                "| " + s + padS + " |\n" +
                                lengthChar(len, '-', "|")
                );

                // andernfalls kommt diese Implementierung in Frage:
                // sieht zwar nicht so schön aus, aber ist um einiges einfacher! ;)
                /*
                System.out.println(
                        "_________________________________________" + '\n' +
                                "| " + m.getUID() + " " + m.getName() + " |\n" +
                                "| " + m.getEMail() + " " + m.getPosition().toString() + " |\n" +
                                "----------------------------------------------"
                );
                 */
            }
        }
    }

    // gibt einen String mit der richtigen Anzahl an --- oder ___ zurück für die Border
    private String lengthChar(int len, char toRepeat, String padding) {
        return String.valueOf(padding) + String.valueOf(toRepeat).repeat(Math.max(0, len)) +
                padding;
    }
}
