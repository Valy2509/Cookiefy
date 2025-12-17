package dk.easv.cookiefy.dal;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionManager {
    private String URL = "jdbc:sqlserver://10.176.111.34:1433;databaseName=cookiefy;encrypt=true;trustServerCertificate=true";
    private String USER = "CS2025b_e_7";
    private String PASS = "CS2025bE7#23";
    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASS);
    }
}