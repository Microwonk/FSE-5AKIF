package net.microwonk.aufg_jdbc.dao_land.ui;

import lombok.Cleanup;

import java.util.Scanner;

public class CLI {

    public static void start() {
        @Cleanup
        Scanner scanner = new Scanner(System.in);
        while(true) {
            showMenu();
            switch(scanner.nextLine()){
                case "1" -> System.out.println("Kurseingabe");
                case "2" -> System.out.println("Alle Kurse anzeigen");
                case "3" -> {
                    System.out.println("Auf Wiedersehen!");
                    System.exit(0);
                }
                default -> System.out.println("Falsche Eingabe");
            }
        }
    }

    private static void showMenu() {
        System.out.println("\n______________________ KURSMANAGEMENT ______________________");
        System.out.println("(1) Kurs eingeben \t (2) Alle Kurse anzeigen \t (3) ENDE");
    }
}
