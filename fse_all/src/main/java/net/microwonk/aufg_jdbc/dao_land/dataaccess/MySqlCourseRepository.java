package net.microwonk.aufg_jdbc.dao_land.dataaccess;

import net.microwonk.aufg_jdbc.dao_land.domain.Course;
import net.microwonk.aufg_jdbc.dao_land.domain.Course.CourseType;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class MySqlCourseRepository extends MySqlBaseRepository implements MyCourseRepository {

    private static final String ENTITY_NAME = "courses";

    public MySqlCourseRepository() {
        super();

    }

    @Override
    public Optional<Course> insert(Course entity) {
        Objects.requireNonNull(entity);

        String sql = "INSERT INTO " + ENTITY_NAME + " (`name`, `description`, `hours`, `begindate`, `enddate`, `coursetype`) VALUES (?,?,?,?,?,?) ";

        try (PreparedStatement preparedStatement = super.getCon().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            sets(entity, preparedStatement);

            int affectedRows = preparedStatement.executeUpdate();

            if (affectedRows == 0) {
                return Optional.empty();
            }

            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                return this.getById(generatedKeys.getLong(1));
            } else {
                return Optional.empty();
            }

        } catch (SQLException e) {
            throw new MySqlDatabaseException(e.getMessage());
        }
    }

    private void sets(Course entity, PreparedStatement preparedStatement) throws SQLException {
        preparedStatement.setString(1, entity.getName());
        preparedStatement.setString(2, entity.getDescription());
        preparedStatement.setInt(3, entity.getHours());
        preparedStatement.setDate(4, entity.getBeginDate());
        preparedStatement.setDate(5, entity.getEndDate());
        preparedStatement.setString(6, entity.getCourseType().toString());
    }

    @Override
    public List<Course> getAll() {

        String sql = "SELECT * FROM " + this.ENTITY_NAME;

        try (PreparedStatement preparedStatement = super.getCon().prepareStatement(sql)) {
            ResultSet resSet = preparedStatement.executeQuery();
            ArrayList<Course> courseList = new ArrayList<>();
            while (resSet.next()) {
                courseList.add(new Course(resSet.getLong("id"),
                        resSet.getString("name"),
                        resSet.getString("description"),
                        resSet.getInt("hours"),
                        resSet.getDate("begindate"),
                        resSet.getDate("endDate"),
                        CourseType.valueOf(resSet.getString("coursetype"))
                ));
            }
            return courseList;
        } catch (SQLException e) {
            throw new MySqlDatabaseException("Database Error occured!!");
        }

    }

    public Optional<Course> update(Course entity) {
        Objects.requireNonNull(entity);

        if (super.countAllEntriesInDbWithId(entity.getId(), ENTITY_NAME) == 0) {
            return Optional.empty();
        }

        String sql = "UPDATE " + this.ENTITY_NAME + " SET name = ?, `description` = ?, hours = ?, `begindate` = ?, enddate = ?, coursetype = ? WHERE `courses`.`id` = ?";

        try (PreparedStatement preparedStatement = super.getCon().prepareStatement(sql)) {
            ;
            sets(entity, preparedStatement);
            preparedStatement.setLong(7, entity.getId());

            int affectedRows = preparedStatement.executeUpdate();

            if (affectedRows == 0) {
                return Optional.empty();
            } else {
                return this.getById(entity.getId());
            }


        } catch (SQLException sqlEx) {
            throw new MySqlDatabaseException((sqlEx.getMessage()));
        }
    }

    @Override
    public Optional<Course> getById(Long id) {
        Objects.requireNonNull(id);

        if (super.countAllEntriesInDbWithId(id, this.ENTITY_NAME) == 0) {
            return Optional.empty();
        }

        String sql = "SELECT * FROM " + this.ENTITY_NAME + " WHERE id = ?";

        try (PreparedStatement preparedStatement = super.getCon().prepareStatement(sql)) {
            preparedStatement.setLong(1, id);
            ResultSet resSet = preparedStatement.executeQuery();
            resSet.next();
            Course course = new Course(resSet.getLong("id"),
                    resSet.getString("name"),
                    resSet.getString("description"),
                    resSet.getInt("hours"),
                    resSet.getDate("begindate"),
                    resSet.getDate("endDate"),
                    CourseType.valueOf(resSet.getString("coursetype"))
            );
            return Optional.of(course);
        } catch (SQLException e) {
            throw new MySqlDatabaseException(e.getMessage());
        }
    }

    public boolean deleteById(Long id) {
        return super.deleteById(id, this.ENTITY_NAME);
    }

    @Override
    public List<Course> findAllCoursesByName(String name) {
        return null;
    }

    @Override
    public List<Course> findAllCoursesByDescription(String description) {
        return null;
    }

    @Override
    public List<Course> findAllCoursesByNameOrDescription(String searchText) {

        String sql = "SELECT * FROM " + this.ENTITY_NAME + " WHERE LOWER(description) LIKE LOWER(?) OR LOWER (name) LIKE LOWER(?)";

        try (PreparedStatement preparedStatement = super.getCon().prepareStatement(sql)) {
            preparedStatement.setString(1, "%" + searchText + "%");
            preparedStatement.setString(2, "%" + searchText + "%");
            return getCourses(preparedStatement);

        } catch (SQLException e) {
            throw new MySqlDatabaseException(e.getMessage());
        }
    }

    @Override
    public List<Course> findAllCoursesByCourseType(CourseType courseType) {
        return null;
    }

    @Override
    public List<Course> findAllCoursesByStartDate(Date startDate) {
        return null;
    }

    @Override
    public List<Course> findAllRunningCourses() {

        String sql = "SELECT * FROM " + this.ENTITY_NAME + " WHERE NOW() <= enddate AND NOW() >= begindate";

        try (PreparedStatement preparedStatement = super.getCon().prepareStatement(sql)) {
            preparedStatement.executeQuery();
            return getCourses(preparedStatement);

        } catch (SQLException e) {
            throw new MySqlDatabaseException("Datebbankfehler" + e.getMessage());
        }

    }

    private List<Course> getCourses(PreparedStatement preparedStatement) throws SQLException {
        ResultSet res = preparedStatement.executeQuery();
        ArrayList<Course> courseList = new ArrayList<>();

        while (res.next()) {
            courseList.add(new Course(
                    res.getLong("id"),
                    res.getString("name"),
                    res.getString("description"),
                    res.getInt("hours"),
                    res.getDate("begindate"),
                    res.getDate("endDate"),
                    CourseType.valueOf(res.getString("courseType"))
            ));
        }
        return courseList;
    }
}
