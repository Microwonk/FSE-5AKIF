package net.microwonk.aufg_jdbc.dao_land.ui;

import net.microwonk.aufg_jdbc.dao_land.dataaccess.MyCourseRepository;
import net.microwonk.aufg_jdbc.dao_land.dataaccess.MySqlDatabaseException;
import net.microwonk.aufg_jdbc.dao_land.domain.Course;
import net.microwonk.aufg_jdbc.dao_land.domain.Course.CourseType;
import net.microwonk.aufg_jdbc.dao_land.domain.InvalidValueException;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import java.sql.Date;

public class CLICourse {
    private final Scanner scan;
    private final MyCourseRepository repo;

    public CLICourse(MyCourseRepository repo) {
        this.scan = new Scanner(System.in);
        this.repo = repo;
    }

    public void start() {
        String input = "";
        while (!input.equals("x")) {
            showMenu();
            input = scan.nextLine();
            switch (input) {
                case "1" -> addCourse();
                case "2" -> showAllCourses();
                case "3" -> showCourseDetails();
                case "4" -> updateCourseDetails();
                case "5" -> deleteCourse();
                case "6" -> courseSearch();
                case "7" -> runningCourses();
                default -> System.out.println("Bitte geben Sie etwas gültiges ein...");
            }
        }
    }

    private void runningCourses() {
        List<Course> courseList;

        try {
            courseList = repo.findAllRunningCourses();
            for (Course c : courseList) {
                System.out.println(c);
            }
        } catch (MySqlDatabaseException e) {
            System.out.println(e.getMessage());
        } catch (Exception e) {
            System.out.println("Allgemeiner Fehler: " + e);
        }
    }

    private void courseSearch() {
        System.out.println("Nach Welchen Kurch möchten Sie suchen?");
        String input = scan.nextLine();
        List<Course> courseList;
        try {
            courseList = repo.findAllCoursesByNameOrDescription(input);
            for (Course c : courseList) {
                System.out.println(c);
            }
        } catch (MySqlDatabaseException e) {
            System.out.println(e.getMessage());
        }
    }

    private void deleteCourse() {
        System.out.println("Welcher Kurs soll geloescht werden?");
        Long courseToDelete = Long.parseLong((scan.nextLine()));
        boolean isDone = false;
        try {
            isDone = repo.deleteById(courseToDelete);

        } catch (MySqlDatabaseException e) {
            System.out.println("Datenbankfehler beim loeschen: " + e.getMessage());
        }

        System.out.println(isDone ? "erledigt" : "fehler");
    }

    private void updateCourseDetails() {
        System.out.println("Für welche Kurs DI wollen sie ein Update machen?");
        long courseId = Long.parseLong(scan.nextLine());

        try {

            Optional<Course> courseOptional = repo.getById((courseId));
            if (courseOptional.isEmpty()) {
                System.out.println("Kurs ist nicht in der DB");
            } else {
                Course course = courseOptional.get();
                System.out.println("Ämderungen für folgenden Kurs angeben");
                System.out.println(course);

                System.out.println("Bitte neue Daten angeben; Wenn Wert nicht geändert werden will ENTER drücken");
                System.out.println("Name: ");
                String name = scan.nextLine();
                System.out.println("Description: ");
                String desc = scan.nextLine();
                System.out.println("hours: ");
                String hours = scan.nextLine();
                System.out.println("startDate (YYYY-MM-DD): ");
                String start = scan.nextLine();
                System.out.println("endDate (YYYY-MM-DD): ");
                String end = scan.nextLine();
                System.out.println("CourseType ZA/BF/FF/OE: ");
                String ct = scan.nextLine();

                Optional<Course> optionalCourseUpdated = repo.update(new Course(course.getId(),
                        name.isEmpty() ? course.getName() : name,
                        desc.isEmpty() ? course.getDescription() : desc,
                        Integer.parseInt(hours),
                        start.isEmpty() ? course.getBeginDate() : Date.valueOf(start),
                        end.isEmpty() ? course.getEndDate() : Date.valueOf(end),
                        ct.isEmpty() ? course.getCourseType() : CourseType.valueOf(ct)
                ));
                optionalCourseUpdated.ifPresentOrElse(c -> System.out.println("Kurs aktualisiert"),
                        () -> System.out.println("Fehler bei der Aktualisierung"));
            }
        } catch (IllegalArgumentException iae) {
            System.out.println("Eigabefehler: " + iae.getMessage());
        } catch (InvalidValueException ive) {
            System.out.println("Wert ungültig: " + ive.getMessage());
        } catch (MySqlDatabaseException mde) {
            System.out.println("DB_Fehler: " + mde.getMessage());
        } catch (Exception e) {
            System.out.println("Unbekannter Fehler: " + e.getMessage());
        }
    }

    private void addCourse() {
        String name, description;
        int hours;
        Date dateFrom, dateTo;
        CourseType courseType;

        try {
            System.out.println("Bitte alle Kursdaten angeben:");
            System.out.println("Name: ");
            name = scan.nextLine();
            if (name.isEmpty()) throw new IllegalArgumentException("Eingabe darf nicht leer sein!");
            System.out.println("Beschreibung: ");
            description = scan.nextLine();
            if (description.isEmpty()) throw new IllegalArgumentException("Eingabe darf nicht leer sein");
            System.out.println("Stundenanzahl");
            hours = Integer.parseInt(scan.nextLine());
            System.out.println("Startdatum (YYYY-MM-DD): ");
            dateFrom = Date.valueOf(scan.nextLine());
            System.out.println("Enddatum (YYYY-MM-DD): ");
            dateTo = Date.valueOf(scan.nextLine());
            System.out.println("Kurstyp: (ZA/BF/FF/OE): ");
            courseType = CourseType.valueOf(scan.nextLine());
            Optional<Course> optionalCourse = repo.insert(new Course(name, description, hours, dateFrom, dateTo, courseType));
            if (optionalCourse.isPresent()) {
                System.out.println("Kurs anlegen: " + optionalCourse.get());
            } else {
                System.out.println("Kurs konnte nicht angelegt werden");
            }

        } catch (IllegalArgumentException iae) {
            System.out.println("Eigabefehler: " + iae.getMessage());
        } catch (InvalidValueException ive) {
            System.out.println("Wert ungültig: " + ive.getMessage());
        } catch (MySqlDatabaseException mde) {
            System.out.println("DB_Fehler: " + mde.getMessage());
        } catch (Exception e) {
            System.out.println("Unbekannter Fehler: " + e.getMessage());
        }
    }

    private void showCourseDetails() {
        System.out.println("Für welchen Kurs möchten Sie die Kursdetails anzeigen?");
        Long courseId = Long.parseLong(scan.nextLine());
        try {
            Optional<Course> courseOptional = repo.getById(courseId);
            if (courseOptional.isPresent()) {
                System.out.println(courseOptional.get());
            } else {
                System.out.println("Kurs mit der ID: " + courseId + " wurde nicht gefunden");
            }
        } catch (MySqlDatabaseException e) {
            System.out.println("Database exeption: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Allgemeiner Fehler: " + e);
        }
    }

    private void showAllCourses() {
        List<Course> list;
        try {
            list = this.repo.getAll();
            if (!list.isEmpty()) {
                for (Course l : list) {
                    System.out.println(l);
                }
            } else {
                System.out.println("Kursliste leer");
            }
        } catch (MySqlDatabaseException e) {
            System.out.println("Datenbankzugriff Fehler: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("unbekannter Fehler: " + e.getMessage());
        }
    }

    private void showMenu() {
        System.out.println("----------Kursmanagement---------------");
        System.out.println("(1) Kurs eingeben \t (2) Alle Kurse anzeigen \t (3) Einen Kurs anzeigen mit id \t");
        System.out.println("(4) Kursdetails ändern \t (5) Kurs loeschen \t (6) Kurs suchen \t");
        System.out.println("(7) laufendeKurse \t (y) xxxxxxxxxxxx \t (y) xxxxxxxxxxx \t");
        System.out.println("(x) ENDE");
    }
}
