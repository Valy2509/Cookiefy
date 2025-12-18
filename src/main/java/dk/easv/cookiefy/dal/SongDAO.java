package dk.easv.cookiefy.dal;

import dk.easv.cookiefy.be.Song;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * DAO for Songs
 */

public class SongDAO {

    private ConnectionManager connectionManager = new ConnectionManager();

    public Song saveSong(String title, String artist, double duration, String path, int userId){
        try(Connection con = connectionManager.getConnection()){
            PreparedStatement pr = con.prepareStatement("INSERT INTO Songs(Title, Artist, Duration_Seconds, File_Path, User_ID) VALUES (?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
            pr.setString(1,  title);
            pr.setString(2, artist);
            pr.setDouble(3, duration);
            pr.setString(4, path);
            pr.setInt(5, userId);
            pr.executeUpdate();
            ResultSet rs = pr.getGeneratedKeys();
            if (rs.next()) {
                int id = rs.getInt(1);
                return new Song(id, title, artist, path, duration, userId);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    public void updateSong(Song song){
        try(Connection con = connectionManager.getConnection()){
            PreparedStatement ps = con.prepareStatement("UPDATE Songs SET Title = ?, Artist = ?, File_Path = ?, Duration_Seconds = ? WHERE Song_ID = ?");
            ps.setString(1, song.getTrackName());
            ps.setString(2, song.getArtistName());
            ps.setString(3, song.getPath());
            ps.setInt(4, (int) song.getDuration());
            ps.setInt(5, song.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public List<Song> getAllSongs(int userId){
        List<Song> songs = new ArrayList<>();
        try(Connection con = connectionManager.getConnection()){
            PreparedStatement pr = con.prepareStatement("SELECT * FROM Songs WHERE User_ID = ?");
            pr.setInt(1, userId);
            ResultSet rs = pr.executeQuery();
            while (rs.next()) {
                int id = rs.getInt(1);
                String title = rs.getString(2);
                String artist = rs.getString(3);
                double duration = rs.getDouble(4);
                String path = rs.getString(5);
                songs.add(new Song(id, title, artist, path, duration, userId));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return songs;
    }

    public void deleteSong(Song target) {
        try(Connection con = connectionManager.getConnection()){
            PreparedStatement pr = con.prepareStatement("DELETE FROM Songs WHERE Song_ID = ?");
            pr.setInt(1, target.getId());
            pr.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
