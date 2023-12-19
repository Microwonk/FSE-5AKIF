package net.microwonk.aufg_jdbc.dao_land.dataaccess;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MySqlDatabaseConnection {

    private static Connection con = null;

    private MySqlDatabaseConnection(){

    }

    public static Connection getConnection(String url, String user, String password) throws ClassNotFoundException, SQLException {
        if (con == null) {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection(url, user, password);
        }
        return con;
    }
}
