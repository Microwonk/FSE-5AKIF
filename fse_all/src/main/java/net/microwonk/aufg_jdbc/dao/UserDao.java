package net.microwonk.aufg_jdbc.dao;

import lombok.Cleanup;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class UserDao implements Dao<User, Integer> {
    private static final String JDBC_URL = "jdbc:mysql://127.0.0.1:3306/php13";
    private static final String USER = "root";
    private static final String PASSWORD = "123";

    public UserDao() {
    }

    @Override
    public Optional<User> get(Integer id) {
        try (Connection con = DriverManager.getConnection(JDBC_URL, USER, PASSWORD);
             PreparedStatement stmt = con.prepareStatement("SELECT * FROM user WHERE id=?")) {
            stmt.setInt(1, id);
            ResultSet resSet = stmt.executeQuery();
            if (resSet.next()) {
                return Optional.of(fromResultSet(resSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public List<User> getAll() {
        List<User> userList = new ArrayList<>();
        try (Connection con = DriverManager.getConnection(JDBC_URL, USER, PASSWORD);
             Statement stmt = con.createStatement()) {
            ResultSet resSet = stmt.executeQuery("SELECT * FROM user");
            while (resSet.next()) {
                userList.add(fromResultSet(resSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return userList;
    }

    @Override
    public void insert(User user) {
        try (Connection con = DriverManager.getConnection(JDBC_URL, USER, PASSWORD);
             PreparedStatement stmt = con.prepareStatement("INSERT INTO user(birthdate, email, firstname, lastname, phone, street) VALUES(?, ?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS)) {
            stmt.setDate(1, new java.sql.Date(user.getBirthDate().getTime()));
            stmt.setString(2, user.geteMail());
            stmt.setString(3, user.getFirstName());
            stmt.setString(4, user.getLastName());
            stmt.setString(5, user.getPhone());
            stmt.setString(6, user.getStreet());
            stmt.executeUpdate();

            ResultSet generatedKeys = stmt.getGeneratedKeys();
            if (generatedKeys.next()) {
                user.setId(generatedKeys.getInt(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(User user, String[] params) {
        user.setFirstName(Objects.requireNonNull(params[0], "First name cannot be null"));
        user.setLastName(Objects.requireNonNull(params[1], "Last name cannot be null"));
        user.seteMail(Objects.requireNonNull(params[2], "Email cannot be null"));
        user.setPhone(Objects.requireNonNull(params[3], "Phone cannot be null"));
        user.setStreet(Objects.requireNonNull(params[4], "Street cannot be null"));

        try (Connection con = DriverManager.getConnection(JDBC_URL, USER, PASSWORD);
             PreparedStatement stmt = con.prepareStatement("UPDATE user SET firstname=?, lastname=?, email=?, phone=?, street=? WHERE id=?")) {
            stmt.setString(1, user.getFirstName());
            stmt.setString(2, user.getLastName());
            stmt.setString(3, user.geteMail());
            stmt.setString(4, user.getPhone());
            stmt.setString(5, user.getStreet());
            stmt.setInt(6, user.getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(User user) {
        delete(user.getId());
    }

    public void delete(Integer id) {
        try (Connection con = DriverManager.getConnection(JDBC_URL, USER, PASSWORD);
             PreparedStatement stmt = con.prepareStatement("DELETE FROM user WHERE id=?")) {
             stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private User fromResultSet(ResultSet resSet) throws SQLException {
        return new User(
                resSet.getInt("id"),
                resSet.getDate("birthdate"),
                resSet.getString("firstname"),
                resSet.getString("lastname"),
                resSet.getString("email"),
                resSet.getString("phone"),
                resSet.getString("street")
        );
    }
}
