package dk.easv.cookiefy.dal;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionManager {
    private String URL = "jdbc:mysql://localhost:3306/cookiefy";
    private String USER = "root";
    private String PASS = "";
    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASS);
    }
}
