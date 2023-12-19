package net.microwonk.aufg_jdbc.dao;

import java.util.Date;

public class UserDaoTest {

    public static void main(String[] args) {
        UserDao userDao = new UserDao();

        // Create a new user
        User newUser = new User(0, new Date(), "John", "Doe", "john.doe@example.com", "123456789", "123 Main St");
        userDao.insert(newUser);
        System.out.println("Created user: " + newUser);

        // Retrieve and display the created user
        User retrievedUser = userDao.get(newUser.getId()).orElse(User.EMPTY);
        System.out.println("Retrieved user: " + retrievedUser);

        // Update the user's information
        String[] updateParams = {"UpdatedFirstName", "UpdatedLastName", "updated.email@example.com", "987654321", "456 Updated St"};
        userDao.update(newUser, updateParams);
        System.out.println("Updated user: " + newUser);

        // Retrieve and display the updated user
        retrievedUser = userDao.get(newUser.getId()).orElse(User.EMPTY);
        System.out.println("Retrieved updated user: " + retrievedUser);

        // Delete the user
        userDao.delete(newUser.getId());
        System.out.println("User deleted.");

        // Attempt to retrieve the deleted user
        retrievedUser = userDao.get(newUser.getId()).orElse(User.EMPTY);
        System.out.println("Attempted to retrieve deleted user: " + retrievedUser);
    }
}

