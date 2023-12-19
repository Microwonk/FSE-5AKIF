package net.microwonk.aufg_jdbc.dao_land.ui;

import net.microwonk.aufg_jdbc.dao_land.dataaccess.MySqlCourseRepository;
import net.microwonk.aufg_jdbc.dao_land.dataaccess.MySqlStudentRepository;

import java.util.Scanner;

public class CLI {
    private final Scanner s;
    private String input;

    private final static CLI INSTANCE;

    static {
        INSTANCE = new CLI();
    }

    public static CLI get() {
        return INSTANCE;
    }

    private CLI(){
        s = new Scanner(System.in);
        input = "";
    }

    public void start() {
        while (!input.equals("x")) {
            System.out.println("(1) CourseMenu \t (2) StudentMenu \t (x) Abbrechen \t");
            input = s.nextLine();
            switch (input) {
                case "1" -> {
                    System.out.println("Kurs Menu");
                    new CLICourse(new MySqlCourseRepository()).start();
                }
                case "2" -> {
                    System.out.println("Student Menu");
                    new CLIStudent(new MySqlStudentRepository()).start();
                }
                case "x" -> {
                    System.out.println("Programm wird beendet");
                    System.exit(0);
                }
                default -> System.out.println("Bitte geben sie einen gültigen Wert ein");
            }
        }
    }
}
