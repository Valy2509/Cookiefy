package dk.easv.cookiefy.dal;

import dk.easv.cookiefy.be.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDAO {
    private ConnectionManager connectionManager = new ConnectionManager();

    public User Login(String email){
        User user = null;
        try(Connection con = connectionManager.getConnection()){
            PreparedStatement preparedStatement = con.prepareStatement("SELECT * FROM users WHERE email = ?");
            preparedStatement.setString(1, email);
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                user = new User(rs.getInt("User_ID"), rs.getString("Username"), rs.getString("Email"), rs.getString("Password"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return user;
    }

    public void Register(String username, String email, String pass){
        try(Connection con = connectionManager.getConnection()){
            PreparedStatement preparedStatement = con.prepareStatement("INSERT INTO Users (Username, Email, Password) VALUES (?, ?, ?)");
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, email);
            preparedStatement.setString(3, pass);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
