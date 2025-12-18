package dk.easv.cookiefy.dal;

import dk.easv.cookiefy.be.Song;
import dk.easv.cookiefy.be.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class HistoryDAO {
    private ConnectionManager connectionManager =  new ConnectionManager();

    public void addToHistory(Song song, User user){
        try(Connection con = connectionManager.getConnection()){
            try(PreparedStatement ps = con.prepareStatement("DELETE FROM User_History WHERE User_Id = ? AND Song_Id = ?")){
                ps.setInt(1, user.getUserId());
                ps.setInt(2, song.getId());
                ps.executeUpdate();
            }

            try(PreparedStatement ps = con.prepareStatement("INSERT INTO User_History(User_Id,Song_Id) VALUES(?,?)")){
                ps.setInt(1, user.getUserId());
                ps.setInt(2, song.getId());
                ps.executeUpdate();
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Song> getRecentSongs(User user){
        List<Song> recentSongs = new ArrayList<>();
        try(Connection con = connectionManager.getConnection()){
            PreparedStatement ps = con.prepareStatement("SELECT TOP 10 * FROM User_History JOIN Songs ON User_History.Song_ID = Songs.Song_ID WHERE User_History.User_ID = ? ORDER BY User_History.Played_At DESC");
            ps.setInt(1, user.getUserId());
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                recentSongs.add(new Song(rs.getInt("Song_ID"), rs.getString("Title"), rs.getString("Artist"), rs.getString("File_Path"), rs.getDouble("Duration_Seconds"), rs.getInt("User_ID")));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return recentSongs;
    }
}
