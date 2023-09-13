package net.microwonk.fse1.aufg1;

public final class Rahmendruck implements  Druckbar {
    @Override
    public void drucken(Person person) {
        switch (person) {
            case Mitarbeiter m -> {
                String f = m.getUID() + " " + m.getName();
                String s = m.getEMail() + " " + m.getPosition().toString();
                int len = Integer.max(f.length(), s.length());
                f += " ".repeat(Math.max(0, Math.abs(f.length() - len)));
                s += " ".repeat(Math.max(0, Math.abs(s.length() - len)));
                len+=2; // padding consideration für den Rahmen
                System.out.println(
                        lengthChar(len, '_', "|") + '\n' +
                        "| " + f + " |\n" +
                                "| " + s + " |\n" +
                                lengthChar(len, '-', "|")
                );
            }
        }
    }

    private String lengthChar(int len, char toRepeat, String padding) {
        return String.valueOf(padding) + String.valueOf(toRepeat).repeat(Math.max(0, len)) +
                padding;
    }
}
