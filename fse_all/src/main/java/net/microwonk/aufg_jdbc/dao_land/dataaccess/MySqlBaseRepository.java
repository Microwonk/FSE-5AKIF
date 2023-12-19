package net.microwonk.aufg_jdbc.dao_land.dataaccess;

import lombok.Getter;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;

@Getter
public abstract class MySqlBaseRepository {

    private final Connection con;
    private static final String URL = "jdbc:mysql://localhost:3306/kurse"; // nur auf meinem heim PC
    private static final String USER = "root";
    private static final String PASSWORD = "123";

    public MySqlBaseRepository() {
        try {
            this.con = MySqlDatabaseConnection.getConnection(URL, USER, PASSWORD);
        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public int countAllEntriesInDbWithId(Long id, String entity) {
        try {
            String countSql = "SELECT COUNT(*) FROM " + entity + " WHERE id = ?";
            PreparedStatement preparedStatementCount = con.prepareStatement(countSql);
            preparedStatementCount.setLong(1, id);
            ResultSet resSetCount = preparedStatementCount.executeQuery();
            resSetCount.next();
            return resSetCount.getInt(1);
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public boolean deleteById(Long id, String entity) {
        Objects.requireNonNull(id);
        String sql = "DELETE FROM " + entity + " WHERE id = ?";
        if (this.countAllEntriesInDbWithId(id, entity) == 1) {
            try (PreparedStatement preparedStatement = this.getCon().prepareStatement(sql)) {
                preparedStatement.setLong(1, id);
                preparedStatement.executeUpdate();
                return true;
            } catch (SQLException e) {
                throw new RuntimeException(e.getMessage());
            }
        }
        return false;
    }
}
