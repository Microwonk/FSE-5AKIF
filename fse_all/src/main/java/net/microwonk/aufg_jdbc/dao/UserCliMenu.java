package net.microwonk.aufg_jdbc.dao;

import lombok.Cleanup;

import java.util.List;
import java.util.Scanner;

public class UserCliMenu {

    private static final UserDao userDao = new UserDao();
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String... args) {
        while (true) {
            System.out.print(
                    """
                            User DAO CLI Menu:
                            1. User mit ID holen
                            2. Alle Users holen
                            3. User kreieren
                            4. User Updaten
                            5. User Löschen
                            0. Beenden
                            Eingabe:\s"""
            );

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1 -> getUserById();
                case 2 -> getAllUsers();
                case 3 -> createUser();
                case 4 -> updateUser();
                case 5 -> deleteUser();
                case 0 -> {
                    System.out.println("Tschüss!");
                    scanner.close();
                    System.exit(0);
                }
                default -> System.out.println("Invalide Eingabe, bitte nochmals versuchen oder mit 0 beenden");
            }
            try { Thread.sleep(1000); } catch (Exception ignored) { }
        }
    }

    private static void getUserById() {
        System.out.print("User ID eingeben: ");
        int userId = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        userDao.get(userId).ifPresentOrElse(
                user -> System.out.println("User gefunden: " + user),
                () -> System.out.println("User nicht gefunden mit ID: " + userId)
        );
    }

    private static void getAllUsers() {
        List<User> users = userDao.getAll();
        System.out.println("Alle User: ");
        users.forEach(System.out::println);
    }

    private static void createUser() {
        System.out.println("User Details eingeben:");
        System.out.print("Geburtstag (yyyy-MM-dd): ");
        String birthdateStr = scanner.nextLine();
        System.out.print("Email: ");
        String email = scanner.nextLine();
        System.out.print("First Name: ");
        String firstName = scanner.nextLine();
        System.out.print("Last Name: ");
        String lastName = scanner.nextLine();
        System.out.print("Phone: ");
        String phone = scanner.nextLine();
        System.out.print("Street: ");
        String street = scanner.nextLine();

        User newUser = new User(0, java.sql.Date.valueOf(birthdateStr), firstName, lastName, email, phone, street);
        userDao.insert(newUser);
        System.out.println("User kreiert: " + newUser);
    }

    private static void updateUser() {
        System.out.print("User ID zum updaten eingeben: ");
        int userId = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        userDao.get(userId).ifPresentOrElse(
                user -> {
                    System.out.println("Neue Details eingeben für User " + userId + ":");
                    System.out.print("First Name: ");
                    String firstName = scanner.nextLine();
                    System.out.print("Last Name: ");
                    String lastName = scanner.nextLine();
                    System.out.print("Email: ");
                    String email = scanner.nextLine();
                    System.out.print("Phone: ");
                    String phone = scanner.nextLine();
                    System.out.print("Street: ");
                    String street = scanner.nextLine();

                    userDao.update(user, new String[]{firstName, lastName, email, phone, street});
                    System.out.println("User updated: " + user);
                },
                () -> System.out.println("User mit ID: " + userId + " nicht gefunden.")
        );
    }

    private static void deleteUser() {
        System.out.print("Enter User ID, die gelöscht werden soll: ");
        int userId = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        userDao.delete(userId);
        System.out.println("User mit ID: " + userId + " gelöscht");
    }
}

