package net.microwonk.aufg_jdbc.dao_land.dataaccess;

import net.microwonk.aufg_jdbc.dao_land.domain.Student;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class MySqlStudentRepository extends MySqlBaseRepository implements MyStudentRepository {

    private static final String ENTITY_NAME = "students";

    @Override
    public Optional<Student> insert(Student entity) {

        String sql = "INSERT INTO " + ENTITY_NAME + " (firstname, lastname, street, streetnumber, postalcode, birthdate) VALUES (?,?,?,?,?,?)";

        try (PreparedStatement preparedStatement = super.getCon().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, entity.getFirstname());
            preparedStatement.setString(2, entity.getLastname());
            preparedStatement.setString(3, entity.getFirstname());
            preparedStatement.setInt(4, entity.getStreetNumber());
            preparedStatement.setInt(5, entity.getPostalCode());
            preparedStatement.setDate(6, entity.getGebDatum());

            int insertedRowCount = preparedStatement.executeUpdate();
            ResultSet keys = preparedStatement.getGeneratedKeys();

            if (insertedRowCount == 0) {
                return Optional.empty();
            }

            if (keys.next()) {
                return this.getById(keys.getLong(1));
            } else {
                return Optional.empty();
            }

        } catch (SQLException e) {
            throw new MySqlDatabaseException(e.getMessage());
        }
    }

    @Override
    public Optional<Student> getById(Long id) {
        Objects.requireNonNull(id);
        if (super.countAllEntriesInDbWithId(id, ENTITY_NAME) == 0) {
            return Optional.empty();
        }

        String sql = "SELECT * FROM " + ENTITY_NAME + " WHERE id = ?";

        try (PreparedStatement preparedStatement = super.getCon().prepareStatement(sql)) {
            preparedStatement.setLong(1, id);
            ResultSet resSet = preparedStatement.executeQuery();
            resSet.next();
            Student student = new Student(resSet.getLong("id"),
                    resSet.getString("firstname"),
                    resSet.getString("lastname"),
                    resSet.getString("street"),
                    resSet.getInt("streetnumber"),
                    resSet.getInt("postalcode"),
                    resSet.getDate("birthdate")
            );
            return Optional.of(student);
        } catch (SQLException e) {
            throw new MySqlDatabaseException(e.getMessage());
        }

    }

    @Override
    public List<Student> getAll() {

        String sql = "SELECT * FROM " + ENTITY_NAME;

        try (PreparedStatement preparedStatement = super.getCon().prepareStatement(sql)) {
            ResultSet res = preparedStatement.executeQuery();
            return getStudents(res);

        } catch (SQLException e) {
            throw new MySqlDatabaseException(e.getMessage());
        }

    }

    private List<Student> getStudents(ResultSet res) throws SQLException {
        List<Student> studentList = new ArrayList<>();
        while (res.next()) {
            studentList.add(new Student(
                    res.getLong("id"),
                    res.getString("firstname"),
                    res.getString("lastname"),
                    res.getString("street"),
                    res.getInt("streetnumber"),
                    res.getInt("postalcode"),
                    res.getDate("birthdate")
            ));
        }
        return studentList;
    }

    public List<Student> getAllStudentsByLastNameOrFirstName(String searchText) {

        String sql = "SELECT * FROM " + ENTITY_NAME + " WHERE LOWER(firstname) LIKE LOWER(?) OR LOWER (lastname) LIKE LOWER(?)";

        try (PreparedStatement preparedStatement = super.getCon().prepareStatement(sql)) {
            preparedStatement.setString(1, "%" + searchText + "%");
            preparedStatement.setString(2, "%" + searchText + "%");
            ResultSet res = preparedStatement.executeQuery();

            ArrayList<Student> studentList = new ArrayList<>();
            while (res.next()) {
                studentList.add(new Student(
                        res.getLong("id"),
                        res.getString("firstname"),
                        res.getString("lastname"),
                        res.getString("street"),
                        res.getInt("streetnumber"),
                        res.getInt("postalcode"),
                        res.getDate("birthdate")
                ));
            }
            return studentList;

        } catch (SQLException e) {
            throw new MySqlDatabaseException(e.getMessage());
        }
    }


    @Override
    public Optional<Student> update(Student entity) {
        Objects.requireNonNull(entity);
        String sql = "UPDATE " + ENTITY_NAME + " SET firstname = ?, lastname = ?, street = ?, streetnumber = ?, postalcode = ?, birthdate = ? WHERE " + this.ENTITY_NAME + " . id = ?";

        if (super.countAllEntriesInDbWithId(entity.getId(), ENTITY_NAME) == 0) {
            return Optional.empty();
        }
        try (PreparedStatement preparedStatement = super.getCon().prepareStatement(sql)) {
            preparedStatement.setString(1, entity.getFirstname());
            preparedStatement.setString(2, entity.getLastname());
            preparedStatement.setString(3, entity.getStreet());
            preparedStatement.setInt(4, entity.getStreetNumber());
            preparedStatement.setInt(5, entity.getPostalCode());
            preparedStatement.setDate(6, entity.getGebDatum());
            preparedStatement.setLong(7, entity.getId());

            if (preparedStatement.executeUpdate() == 0) {
                return Optional.empty();
            } else {
                return this.getById(entity.getId());
            }

        } catch (SQLException sqlEx) {
            throw new MySqlDatabaseException((sqlEx.getMessage()));
        }

    }

    public boolean deleteById(Long id) {
        return super.deleteById(id, ENTITY_NAME);
    }

    @Override
    public List<Student> getAllStudentsByLastName(String ln) {
        return null;
    }

    @Override
    public List<Student> getAllStudentsByYOB(Date d) {
        String sql = "SELECT * FROM " + ENTITY_NAME + " WHERE birthdate like ? ORDER BY birthdate DESC";

        try (PreparedStatement preparedStatement = super.getCon().prepareStatement(sql)) {
            preparedStatement.setDate(1, d);
            ResultSet res = preparedStatement.executeQuery();

            return getStudents(res);


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public List<Student> getAllStudentsByPostalCode(int pc) {
        return null;
    }
}
