package net.microwonk.aufg_jdbc.dao_land.ui;

import net.microwonk.aufg_jdbc.dao_land.dataaccess.MyStudentRepository;
import net.microwonk.aufg_jdbc.dao_land.dataaccess.MySqlDatabaseException;
import net.microwonk.aufg_jdbc.dao_land.domain.InvalidValueException;
import net.microwonk.aufg_jdbc.dao_land.domain.Student;

import java.sql.Date;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class CLIStudent {

    Scanner scan;
    MyStudentRepository repo;

    public CLIStudent(MyStudentRepository repo) {
        this.scan = new Scanner(System.in);
        this.repo = repo;
    }

    public void start() {
        String input = "";
        while (!input.equals("x")) {

            showMenu();
            input = scan.nextLine();
            switch (input) {
                case "1" -> addStudent();
                case "2" -> showAllStudents();
                case "3" -> showOneStudentWithDetails();
                case "6" -> showStudentsByName();
                case "4" -> updateStudent();
                case "5" -> deleteStudent();
                case "7" -> showStudentsByYOB();
                default -> System.out.println("Bitte geben Sie etwas gültiges ein...");
            }
        }
    }

    private void showStudentsByYOB() {
        System.out.println("Geburtsdatum eingeben yyyy-mm-dd: ");
        String input = scan.nextLine();
        List<Student> studentList;
        try {
            Date d = Date.valueOf(input);
            studentList = repo.getAllStudentsByYOB(d);
            for (Student s : studentList) {
                System.out.println(s);
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


    private void showStudentsByName() {
        System.out.println("Nach welchen Studenten möchten Sie suchen?");
        String input = scan.nextLine();
        List<Student> studentList;
        try {
            studentList = repo.getAllStudentsByLastNameOrFirstName(input);
            for (Student s : studentList) {
                System.out.println(s);
            }
        } catch (MySqlDatabaseException e) {
            System.out.println(e.getMessage());
        }
    }

    private void deleteStudent() {
        System.out.println("Welcher Student soll geloescht werden?");
        Long studentToDelete = Long.parseLong((scan.nextLine()));
        boolean isDone = false;
        try {
            isDone = repo.deleteById(studentToDelete);
        } catch (MySqlDatabaseException e) {
            System.out.println("Datenbankfehler beim loeschen: " + e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println(isDone ? "erledigt" : "fehler");
    }


    private void updateStudent() {
        System.out.println("Update für welchen Student?");
        long studentId = Long.parseLong(scan.nextLine());

        try {

            Optional<Student> optionalStudent = repo.getById((studentId));
            if (optionalStudent.isEmpty()) {
                System.out.println("Diesen Studenten gibt es nicht");
            } else {
                Student student = optionalStudent.get();
                System.out.println("Änderungen für folgenden Studenten");
                System.out.println(student);

                System.out.println("Bitte neue Daten angeben; Wenn Wert nicht geändert werden will ENTER drücken");
                System.out.println("First Name: ");
                String firstname = scan.nextLine();
                System.out.println("Last Name: ");
                String lastname = scan.nextLine();
                System.out.println("Street: ");
                String street = scan.nextLine();
                System.out.println("Street Number: ");
                String streetNumber = scan.nextLine();
                System.out.println("Postal Code: ");
                String postalCode = scan.nextLine();
                System.out.println("birthDate (YYYY-MM-DD): ");
                String birthDate = scan.nextLine();

                Optional<Student> optionalStudentUpdate = repo.update(new Student(student.getId(),
                        firstname.isEmpty() ? student.getFirstname() : firstname,
                        lastname.isEmpty() ? student.getLastname() : lastname,
                        street.isEmpty() ? student.getStreet() : street,
                        streetNumber.isEmpty() ? student.getStreetNumber() : Integer.parseInt(streetNumber),
                        postalCode.isEmpty() ? student.getPostalCode() : Integer.parseInt(postalCode),
                        birthDate.isEmpty() ? student.getGebDatum() : Date.valueOf(birthDate)
                ));
                //optionalCourseUpdated.isPresent();
                optionalStudentUpdate.ifPresentOrElse(c -> System.out.println("Studnet aktualisiert"),
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

    private void addStudent() {
        String firstName, lastName;
        String street;
        Date birthDate;
        int streetNumber;
        int postalCode;

        try {
            System.out.println("Studenten hinzu: ");
            System.out.println("Bitte alles angeben:");
            System.out.println("FirstName: ");
            firstName = scan.nextLine();
            if (firstName.isEmpty()) throw new IllegalArgumentException("Eingabe darf nicht leer sein!");
            System.out.println("LastName: ");
            lastName = scan.nextLine();
            if (lastName.isEmpty()) throw new IllegalArgumentException("Eingabe darf nicht leer sein");
            System.out.println("Street:");
            street = scan.nextLine();
            System.out.println("StreetNumber:");
            streetNumber = Integer.parseInt(scan.nextLine());
            System.out.println("Postalcode");
            postalCode = Integer.parseInt(scan.nextLine());
            System.out.println("Date of Birth (YYYY-MM-DD): ");
            birthDate = Date.valueOf(scan.nextLine());


            Optional<Student> optionalStudent = repo.insert(new Student(firstName, lastName, street, streetNumber, postalCode, birthDate));
            if (optionalStudent.isPresent()) {
                System.out.println("Student anlegen: " + optionalStudent.get());
            } else {
                System.out.println("Student konnte nicht angelegt werden");
            }

        } catch (IllegalArgumentException iae) {
            System.out.println("Eingabefehler: " + iae.getMessage());
        } catch (InvalidValueException ive) {
            System.out.println("Wert ungültig: " + ive.getMessage());
        } catch (MySqlDatabaseException mde) {
            System.out.println("DB_Fehler: " + mde.getMessage());
        } catch (Exception e) {
            System.out.println("Unbekannter Fehler: " + e.getMessage());
        }
    }

    private void showOneStudentWithDetails() {
        System.out.println("Welchen Studenten wollen Sie anzeigen lassen?");
        Long studentId = Long.parseLong(scan.nextLine());
        try {
            Optional<Student> studentOptional = repo.getById(studentId);
            if (studentOptional.isPresent()) {
                System.out.println(studentOptional.get());
            } else {
                System.out.println("Student mit der ID: " + studentId + " wurde nicht gefunden");
            }
        } catch (MySqlDatabaseException e) {
            System.out.println("Database exeption: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Allgemeiner Fehler: " + e);
        }
    }

    private void showAllStudents() {
        List<Student> list;
        try {
            list = this.repo.getAll();
            if (!list.isEmpty()) {
                for (Student s : list) {
                    System.out.println(s);
                }
            } else {
                System.out.println("Studentenliste leer");
            }
        } catch (MySqlDatabaseException e) {
            System.out.println("Datenbankzugriff Fehler: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("unbekannter Fehler: " + e.getMessage());
        }
    }

    private void showMenu() {
        System.out.println("----------StudentManagement---------------");
        System.out.println("(1) Student eingeben \t (2) Alle Studenten anzeigen \t (3) Einen Student anzeigen mit id \t");
        System.out.println("(4) Studentendetails ändern \t (5) Student loeschen \t (6) Student suchen \t");
        System.out.println("(7) nach Geb.Jahr suchen \t (y) xxxxxxxxxxxx \t (y) xxxxxxxxxxx \t");
        System.out.println("(x) ENDE");
    }
}

