package net.microwonk.aufg_jdbc.jdbc_raw;

import lombok.Cleanup;
import lombok.val;

import java.sql.*;

public class Main {

    public static void jdbcTests() {
        try (Connection con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/php13", "root", "123")) {

            val stmt = con.createStatement();

            var sql = "SELECT * FROM user";
            ResultSet resSet = stmt.executeQuery(sql);

            while (resSet.next()) {
                System.out.println(
                        resSet.getInt("id") + ", " +
                                resSet.getDate("birthdate") + ", " +
                                resSet.getString("email") + ", " +
                                resSet.getString("firstname") + ", " +
                                resSet.getString("lastname") + ", " +
                                resSet.getString("phone") + ", " +
                                resSet.getString("street") + '\n'
                );
            }

            String sql2 = "UPDATE user SET lastname=? WHERE id=?";

            @Cleanup
            PreparedStatement prepstmt = con.prepareStatement(sql2);

            prepstmt.setString(1, "Test");
            prepstmt.setInt(2, 50);

            prepstmt.execute();

            String sql3 = "INSERT INTO user(birthdate, email, firstname, lastname, phone, street) VALUES(?, ?, ?, ?, ?, ?)";

            @Cleanup
            PreparedStatement pstmt = con.prepareStatement(sql3);

            pstmt.setDate(1, new Date(2002, 3, 1));
            pstmt.setString(2, "iwas@mail.com");
            pstmt.setString(3, "Nicolas");
            pstmt.setString(4, "Frey");
            pstmt.setString(5, "1028379128378");
            pstmt.setString(6, "Unterhäuser 80");

            pstmt.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
