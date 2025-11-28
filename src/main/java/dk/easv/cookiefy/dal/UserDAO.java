package dk.easv.cookiefy.dal;

import dk.easv.cookiefy.be.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDAO {
    private ConnectionManager connectionManager = new ConnectionManager();

    public User getUserByEmail(String email) throws SQLException{
        Connection con = connectionManager.getConnection();
        User user = null;
        PreparedStatement preparedStatement = con.prepareStatement("SELECT * FROM users WHERE email = ?");
        preparedStatement.setString(1, email);
        ResultSet rs = preparedStatement.executeQuery();
        if (rs.next()) {
            user = new User(rs.getString("name"), rs.getString("email"), rs.getString("password_hash"));
        }
        return user;
    }

    public void registerUser(String email, String pass){
        try {
            Connection con = connectionManager.getConnection();
            PreparedStatement preparedStatement = con.prepareStatement("INSERT INTO users (name, email, password_hash, created_at) VALUES ('Adam', ?,?, '2025-11-15')");
            preparedStatement.setString(1, email);
            preparedStatement.setString(2, pass);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
